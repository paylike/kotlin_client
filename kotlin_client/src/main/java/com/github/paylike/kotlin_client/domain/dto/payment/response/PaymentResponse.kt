package com.github.paylike.kotlin_client.domain.dto.payment.response

import com.github.paylike.kotlin_client.domain.dto.payment.response.challenges.ChallengeDto
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Response types to payment requests
 *
 * One of :
 * [challenges],
 * or
 * ( one of them but for the same purpose
 * [authorizationId]
 * or
 * [transactionId]
 * )
 * or
 * [hints]
 * or
 * [hints], // optional
 * [action],
 * [fields],
 * [timeout], // optional and in milliseconds
 * [method], // http method
 */
@Serializable
data class PaymentResponse(
    /**
     * Challenges type of response has this field
     */
    val challenges: List<ChallengeDto>? = null,

    /**
     * Final response has this field
     */
    val authorizationId: String? = null,
    val transactionId: String? = null,

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
    val timeout: Int? = null,
    val method: String? = null,
)
