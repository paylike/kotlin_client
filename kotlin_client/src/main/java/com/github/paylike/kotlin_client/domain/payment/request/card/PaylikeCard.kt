package com.github.paylike.kotlin_client.domain.payment.request.card

import com.github.paylike.kotlin_client.domain.tokenize.response.TokenizedResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Describes a card that already has a tokenized number and CVC
 */
@Serializable
data class PaylikeCard(
    val number: TokenizedResponse,
    @SerialName("code") val cvc: TokenizedResponse,
    val expiry: Expiry,
)
