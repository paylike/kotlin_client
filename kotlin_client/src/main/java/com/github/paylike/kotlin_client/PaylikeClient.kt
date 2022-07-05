package com.github.paylike.kotlin_client

import com.github.paylike.kotlin_client.dto.PaylikeClientResponse
import com.github.paylike.kotlin_client.dto.TokenizedResponse
import com.github.paylike.kotlin_client.request.PaylikeRequestBuilder
import com.github.paylike.kotlin_request.PaylikeRequester
import com.github.paylike.kotlin_request.RequestOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
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
     * SDK Client ID used while making requests
     *
     * Note: This is not the merchant's client ID
     */
    var clientId = String()


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
     * Logger function, prints the serialized object
     */
    var log : ((it: Any) -> Unit) = {
        println("$it")
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
     * Overrides the used requester
     */
    fun setRequester(requester: PaylikeRequester) : PaylikeClient {
        this.requester = requester
        return this
    }

    /**
     * Overrides the used logger
     */
    fun setLog(log: ((o: Any) -> Unit)): PaylikeClient {
        this.log = log
        return this
    }

    /**
     * Overrides the timeout settings
     */
    fun setTimeout(timeout: Duration): PaylikeClient {
        this.timeout = timeout
        return this
    }

    /**
     * Overrides hosts
     */
    fun setHosts(hosts: PaylikeHosts): PaylikeClient {
        this.hosts = hosts
        return this
    }

    /**
     * Tokenize is used to acquire tokens from the vault
     * with retry mechanism used.
     */
    fun tokenize(type: TokenizeTypes, value: String): PaylikeRequestBuilder<TokenizedResponse> {
        return PaylikeRequestBuilder {
            GlobalScope.launch(Dispatchers.Main.immediate) {
                _tokenize(type, value)
            }
        }
    }

    /**
     * Used for the tokenization of a token acquired during apple payment flow
     */
//  TODO tokenizeApple()

    /**
     * Used to acquire tokens from the vault.
     */
    private suspend fun _tokenize(type: TokenizeTypes, value: String): TokenizedResponse {
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
     * Used to acquire tokens for apple pay
     */
//  TODO _tokenizeApple()

    /**
     * Payment create calls to capture API
     * with retry mechanism used
     */


    /**
     * Payment create calls the payment API
     */
    suspend fun paymentCreate(
        payment: Map<String, Any>, // TODO nem lesz ez igy jo
        hints: List<String>,
        subPath: String = "/payments",
    ): PaylikeClientResponse {
        val url = hosts.api + subPath
        val dataJson = JsonObject(
            mapOf(
                "hints" to JsonArray(hints.map { JsonPrimitive(it) }),
            ).plus(payment.mapValues {
                val itt = it.value
                when (itt) {
                    is String -> JsonPrimitive(itt)
                    is Boolean -> JsonPrimitive(itt)
                    is Number -> JsonPrimitive(itt)
                    else -> JsonPrimitive("")
                } })
        )
        val opts = RequestOptions(
            clientId = this.clientId,
            data = dataJson,
            version = 1,
            timeout = this.timeout,
        )
    }
}
