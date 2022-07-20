package com.github.paylike.kotlin_client.domain.dto.tokenize.response

import kotlinx.serialization.Serializable

/**
 * Token type for [PaylikeCard.number] and [PaylikeCard.cvc] fields and
 * can be obtained from the paylike vault through tokenize request.
 */
@Serializable
data class TokenizedResponse(
    val token: String,
)
