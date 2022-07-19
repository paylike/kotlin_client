package com.github.paylike.kotlin_client.domain.payment.response

import com.github.paylike.kotlin_client.domain.payment.response.challenges.Challenge
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Response types to payment requests
 *
 * One of :
 * [challenges],
 * or
 * [authorizationId]
 * or
 * [hints]
 * or
 * [hints], // optional
 * [action],
 * [fields],
 * [timeout], // optional
 */
@Serializable
data class PaymentResponse(
    /**
     * Challenges type of response has this field
     */
    val challenges: List<Challenge>? = null,

    /**
     * Final response has this field
     */
    val authorizationId: String? = null,
    val transactionId: String? = null, // TODO is this existing?

    /**
     * Response to any challenge type has this field
     */
    var hints: List<String>? = null,

    /**
     * Iframe and background-iframe have these fields as well
     *
     * [hints] field can be optional in this kind
     */
    val action: String? = null,
    val fields: JsonObject? = null,
    val timeout: Int? = null, // optional and in milliseconds
//    val method: String? = null, // not even mentioned but exists
)
