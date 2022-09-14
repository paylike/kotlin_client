package com.github.paylike.kotlin_client.domain.dto.payment.request.test.card

import com.github.paylike.kotlin_client.domain.dto.payment.request.money.PaymentAmount
import kotlinx.serialization.Serializable

/** [limit] is infinite by default for the API [balance] is infinite by default for the API */
@Serializable
data class TestCardDto(
    val scheme: CardSchemeOptions? = null,
    val code: CardCodeOptions? = null,
    val status: CardStatusOptions? = null,
    val limit: PaymentAmount? = null,
    val balance: PaymentAmount? = null,
)
