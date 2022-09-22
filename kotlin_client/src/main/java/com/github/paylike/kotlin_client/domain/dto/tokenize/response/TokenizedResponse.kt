package com.github.paylike.kotlin_client.domain.dto.tokenize.response

import kotlinx.serialization.Serializable

/**
 * Token type for [PaylikeCard.number] and [PaylikeCard.cvc] fields and can be obtained from the
 * paylike vault through tokenize request.
 *
 * For API reference
 * @see <a
 * href="https://github.com/paylike/api-reference/blob/main/payments/index.md#parameters">link</a>
 */
@Serializable
data class TokenizedResponse(
    val token: String,
)
