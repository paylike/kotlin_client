package com.github.paylike.kotlin_client.domain.dto.payment.request.card

import com.github.paylike.kotlin_client.domain.dto.tokenize.response.TokenizedResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Describes a card that already has a tokenized number and CVC
 */
@Serializable
data class PaylikeCardDto(
    val number: TokenizedResponse,
    @SerialName("code") val cvc: TokenizedResponse,
    val expiry: ExpiryDto,
)
