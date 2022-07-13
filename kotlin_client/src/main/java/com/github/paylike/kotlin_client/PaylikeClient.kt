package com.github.paylike.kotlin_client

import android.net.Uri
import com.github.paylike.kotlin_client.dto.*
import com.github.paylike.kotlin_request.PaylikeRequester
import com.github.paylike.kotlin_request.RequestOptions
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import java.util.function.Consumer
import kotlin.time.Duration
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

/**
 * Handles high level requests towards the paylike ecosystem
 */
class PaylikeClient {
    /**
     * Generates a new client ID
     */
    companion object {
         fun generateClientID(): String {
             val chars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890"
             val rnd = Random.Default
             var uniqueString = String()
             for (i in 0..5)
                 uniqueString += chars[rnd.nextInt(chars.length)]
             return "kotlin-1-$uniqueString"
         }
     }

    /**
     * Standard constructor
     */
    constructor() {
        clientId = generateClientID()
    }

    /**
     * For testing purposes
     */
    constructor(specificClientId: String) {
        this.clientId = specificClientId
    }

    /**
     * SDK Client ID used while making requests
     *
     * Note: This is not the merchant's client ID
     */
    var clientId = String()

    /**
     * Logger function, prints the serialized object
     */
    var log : Consumer<Any> = Consumer {
        println(it.toString())
    }

    /**
     * Underlying implementation to do requests from Paylike library kotlin_request
     */
    var requester: PaylikeRequester = PaylikeRequester()

    /**
     * Timeout to use while making requests
     */
    var timeout: Duration = 20.seconds

    /**
     * Host APIs
     */
    var hosts: PaylikeHosts = PaylikeHosts()

    /**
     * Used to acquire tokens from the vault.
     */
    suspend fun tokenize(type: TokenizeTypes, value: String): TokenizedResponse {
        val dataJson = JsonObject(
            mapOf(
                "type" to when (type) {
                    TokenizeTypes.PCN -> JsonPrimitive("pcn")
                    TokenizeTypes.PCSC -> JsonPrimitive("pcsc")
                },
                "value" to JsonPrimitive(value)
            )
        )
        val opts = RequestOptions(
            clientId = this.clientId,
            data = dataJson,
            version = 1,
            timeout = this.timeout,
        )
        val response = requester.request(hosts.vault, opts)
        val body = response.body
        return TokenizedResponse(Json.decodeFromString(body.toString()))
    }

    /**
     * Payment create calls the payment API
     */
    suspend fun paymentCreate(
        payment: Map<String, Any>, // TODO is it good like this?
        hints: List<String>,
        subPath: String? = "/payments",
    ): PaylikeClientResponse {
        val url = hosts.api + subPath
        val dataJson = JsonObject(
            mapOf(
                "hints" to JsonArray(hints.map { JsonPrimitive(it) }),
            ).plus(payment.mapValues {
                when (val that = it.value) {
                    is String -> JsonPrimitive(that)
                    is Boolean -> JsonPrimitive(that)
                    is Number -> JsonPrimitive(that)
                    else -> JsonPrimitive("")
                }
            })
        )
        val opts = RequestOptions(
            clientId = this.clientId,
            data = dataJson,
            version = 1,
            timeout = this.timeout,
        )
        val response = requester.request(url, opts)
        val jsonBody: JsonObject = Json.encodeToJsonElement(response.body.toString()) as JsonObject
        log.accept(mapOf(
            "t" to "response-body",
            "data" to jsonBody,
        ))
        if (jsonBody["challenges"] != null && (jsonBody["challenges"] as List<*>).isNotEmpty()) {
            val remainingChallenges: List<PaymentChallenge> = (jsonBody["challenges"] as JsonObject).toList().map { PaymentChallenge(Json.encodeToJsonElement(it).jsonObject) }
            val fetchChallenges = remainingChallenges.filter { it.type == "fetch" }
            if (fetchChallenges.isNotEmpty()) {
                return paymentCreate(payment, hints, fetchChallenges.first().path)
            }
            val tdsChallenge = remainingChallenges.filter { it.type == "background-iframe" && it.name == "tds-fingerprint" }
            if (tdsChallenge.isNotEmpty()) {
                return paymentCreate(payment, hints, tdsChallenge.first().path)
            }
            return paymentCreate(payment, hints, remainingChallenges.first().path)
        }
        if (jsonBody["action"] != null && jsonBody["fields"] != null) {
            var refreshedHints = hints
            if (jsonBody["hints"] != null) {
                val freshSet = mutableSetOf<Any>()
                freshSet.addAll(hints)
                freshSet.addAll(Json.decodeFromJsonElement<Collection<Any>>(jsonBody["hints"]!!).toList())
                refreshedHints = freshSet.toList().map { it.toString() }
            }
            val formFields: Map<String, String>? = if (jsonBody["fields"] != null) {
                Json.decodeFromJsonElement(jsonBody["fields"]!!)
            } else {
                null
            }
            val formResponse = requester.request(
                Uri.parse(jsonBody["Action"].toString()).toString(), // TODO not sure about this
                RequestOptions(
                    form = true,
                    formFields = formFields,
                )
            )
            return PaylikeClientResponse(isHTML = true, htmlBody = formResponse.body.toString(), paymentResponse = null, hints = refreshedHints)
        }
        if (jsonBody["hints"] != null && (jsonBody["hints"] as List<*>).isNotEmpty()) {
            val hintsResponse = HintsResponse(jsonBody)
            val freshSet = mutableSetOf<Any>()
            freshSet.addAll(hints)
            freshSet.addAll(hintsResponse.hints)
            val refreshedHints = freshSet.toList().map { it.toString() }
            return paymentCreate(payment, refreshedHints, null)
        }
        return PaylikeClientResponse(isHTML = false, htmlBody = null, paymentResponse = PaymentResponse(jsonBody))
    }
}
