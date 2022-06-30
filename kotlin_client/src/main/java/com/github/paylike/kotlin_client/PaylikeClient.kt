package com.github.paylike.kotlin_client

import com.github.paylike.kotlin_client.dto.TokenizedResponse
import com.github.paylike.kotlin_client.request.PaylikeRequestBuilder
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.Future
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
     * For testing pusposes
     */
    constructor(specificCliendId: String) {
        this.clientId = specificCliendId
    }

    /**
     * Logger function, prints the serialized object
     */
//     var log : ((o: Any) -> Unit) // TODO how to actually define this

    /**
     * Underlying implementation to do requests from Paylike library kotlin_equest
     */
//    var requester: PaylikeRequester = PaylikeRequester() // TODO I cant see the request lib...

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
//    fun setRequester(requester: PaylikeRequester) : PaylikeRequester { // TODO I cant see the request lib...
//        this.requester = requester
//        return this
//    }

    /**
     * Overrides the used logger
     */
    fun setLog(log: ((o: Any) -> Unit)): PaylikeClient { // TODO log funciton definition is blocking
//        this.log = log
        throw NotImplementedError()
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
//    fun tokenize(type: TokenizeTypes, value: String): PaylikeRequestBuilder<TokenizedResponse> {
//        return PaylikeRequestBuilder( () -> _tokenize(type, value) )
//    }

    /**
     * Used for the tokenization of a token acquired during apple payment flow
     */
//  TODO tokenizeApple()

    /**
     * Used to acquire tokens from the vault.
     */
//    suspend fun _tokenize(type: TokenizeTypes, value: String): Future<TokenizedResponse> {
//        var opts = RequestOptions.fromClientId(clientId).setData({
//
//        })
//    }

    /**
     * Used to acquire tokens for apple pay
     */
//  TODO _tokenizeApple()

    /**
     * Payment create calls the capture API
     * with retry mechanism used
     */
//  TODO paymentCreate()

    /**
     * Payment create calls the paymnet API
     */
//    TODO _paymentCreate()

}
