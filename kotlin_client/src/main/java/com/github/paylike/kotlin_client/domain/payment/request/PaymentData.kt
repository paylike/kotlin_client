package com.github.paylike.kotlin_client.domain.payment.request

import com.github.paylike.kotlin_client.domain.payment.request.card.PaylikeCard
import com.github.paylike.kotlin_client.domain.payment.request.integration.PaymentIntegration
import com.github.paylike.kotlin_client.domain.payment.request.plan.PaymentPlan
import com.github.paylike.kotlin_client.domain.payment.request.test.PaymentTest
import com.github.paylike.kotlin_client.domain.payment.request.unplanned.PaymentUnplanned
import com.github.paylike.kotlin_money.PaymentAmount
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Holds information for the payment requests json body
 */
@OptIn(ExperimentalSerializationApi::class) // TODO do we need this annotation?
@Serializable
data class PaymentData(
    val test: PaymentTest? = null,
    val integration: PaymentIntegration,
    val card: PaylikeCard,
    @EncodeDefault var hints: List<String> = emptyList(),
    val text: String? = null,
    val custom: JsonObject? = null,
    val amount: PaymentAmount? = null,
    val plan: List<PaymentPlan>? = null,
    val unplanned: PaymentUnplanned? = null,
    )
