package com.github.paylike.kotlin_client

import com.github.paylike.kotlin_client.domain.PaylikeHosts
import com.github.paylike.kotlin_client.domain.dto.payment.request.PaymentData
import com.github.paylike.kotlin_client.domain.dto.payment.response.PaylikeClientResponse
import com.github.paylike.kotlin_client.domain.dto.payment.response.PaymentResponse
import com.github.paylike.kotlin_client.domain.dto.payment.response.challenges.ChallengeTypes
import com.github.paylike.kotlin_client.domain.dto.tokenize.request.TokenizeData
import com.github.paylike.kotlin_client.domain.dto.tokenize.response.TokenizedResponse
import com.github.paylike.kotlin_request.PaylikeRequester
import com.github.paylike.kotlin_request.RequestOptions
import java.util.function.Consumer
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

/** Handles high level requests towards the paylike ecosystem */
class PaylikeClient {
    /** Generates a new client ID */
    companion object {
        fun generateClientID(): String {
            val chars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890"
            val rnd = Random.Default
            var uniqueString = String()
            for (i in 0..5) uniqueString += chars[rnd.nextInt(chars.length)]
            return "kotlin-1-$uniqueString"
        }
    }

    /** Standard constructor */
    constructor() {
        this.clientId = generateClientID()
    }

    /** For testing purposes */
    constructor(specificClientId: String) {
        this.clientId = specificClientId
    }

    /** Create Json formatter to be able to handle unexpected json fields */
    private val jsonFormatter = Json { ignoreUnknownKeys = true }

    /**
     * SDK Client ID used while making requests
     *
     * Note: This is not the merchant's client ID
     */
    private val clientId: String

    /** Logger function, prints the serialized object */
    var log: Consumer<Any> = Consumer { println(it.toString()) }

    /** Underlying implementation to do requests from Paylike library kotlin_request */
    private val requester: PaylikeRequester = PaylikeRequester()

    /** Timeout to use while making requests */
    private var timeout: Duration = 20.seconds

    /** Host APIs */
    var hosts: PaylikeHosts = PaylikeHosts()

    /** Used to acquire tokens from the vault. */
    suspend fun tokenize(tokenizeData: TokenizeData): TokenizedResponse {
        val response =
            requester.request(
                hosts.vault,
                RequestOptions(
                    method = "POST",
                    clientId = this.clientId,
                    data = Json.encodeToJsonElement(tokenizeData) as JsonObject,
                    version = 1,
                    timeout = this.timeout,
                )
            )
        return Json.decodeFromString(response.body.toString())
    }

    /** Payment create calls the payment API */
    suspend fun paymentCreate(
        paymentData: PaymentData,
        subPath: String? = "/payments",
    ): PaylikeClientResponse {
        /** Start request */
        val response =
            requester.request(
                (hosts.api + subPath),
                RequestOptions(
                    method = "POST",
                    clientId = this.clientId,
                    data = Json.encodeToJsonElement(paymentData) as JsonObject,
                    timeout = this.timeout,
                )
            )
        val paymentResponse =
            jsonFormatter.decodeFromString<PaymentResponse>(response.body.toString())

        /** ChallengeDto process */
        if (!paymentResponse.challenges.isNullOrEmpty()) {
            val remainingChallenges = paymentResponse.challenges
            val fetchChallenges = remainingChallenges.filter { it.type == ChallengeTypes.FETCH }
            if (fetchChallenges.isNotEmpty()) {
                return paymentCreate(paymentData, fetchChallenges.first().path)
            }
            val tdsChallenge =
                remainingChallenges.filter {
                    it.type == ChallengeTypes.BACKGROUNDIFRAME && it.name == "tds-fingerprint"
                }
            if (tdsChallenge.isNotEmpty()) {
                return paymentCreate(paymentData, tdsChallenge.first().path)
            }
            return paymentCreate(paymentData, remainingChallenges.first().path)
        }

        /** Iframe */
        if (!paymentResponse.action.isNullOrEmpty() && !paymentResponse.fields.isNullOrEmpty()) {
            if (!paymentResponse.hints.isNullOrEmpty()) {
                val refreshedHints = paymentData.hints
                paymentResponse.hints = refreshedHints.union(paymentResponse.hints!!).toList()
            }
            val formResponse =
                requester.request(
                    paymentResponse.action,
                    RequestOptions(
                        clientId = this.clientId,
                        method = "POST",
                        form = true,
                        formFields = Json.decodeFromJsonElement(paymentResponse.fields),
                        timeout = paymentResponse.timeout?.toDuration(DurationUnit.MILLISECONDS)
                                ?: this.timeout,
                    )
                )
            return PaylikeClientResponse(
                isHTML = true,
                htmlBody = formResponse.body.toString(),
                paymentResponse = paymentResponse,
            )
        }

        /**
         * Append new hint Appends newly got hint to the [paymentData] then recursively start a new
         * [paymentCreate] iteration.
         */
        if (!paymentResponse.hints.isNullOrEmpty()) {
            val refreshedHints = paymentData.hints
            paymentData.hints = refreshedHints.union(paymentResponse.hints!!).toList()
            return paymentCreate(paymentData)
        }

        /**
         * Final response with [PaylikeClientResponse.paymentResponse] and within
         * [PaymentResponse.transactionId]
         */
        return PaylikeClientResponse(
            isHTML = false,
            htmlBody = null,
            paymentResponse = paymentResponse,
        )
    }
}
