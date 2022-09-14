package com.github.paylike.kotlin_client.domain.dto.payment.request

import com.github.paylike.kotlin_client.domain.dto.payment.request.card.PaylikeCardDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.integration.PaymentIntegrationDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.money.PaymentAmount
import com.github.paylike.kotlin_client.domain.dto.payment.request.plan.PaymentPlanDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.test.PaymentTestDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.unplanned.PaymentUnplannedDto
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Holds information for the payment requests json body
 *
 * For API reference
 * @see <a
 * href="https://github.com/paylike/api-reference/blob/main/payments/index.md#payments">link</a>
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class PaymentData(
    val test: PaymentTestDto? = null,
    val integration: PaymentIntegrationDto,
    val card: PaylikeCardDto,
    @EncodeDefault var hints: List<String> = emptyList(),
    val text: String? = null,
    val custom: JsonObject? = null,
    val amount: PaymentAmount? = null,
    val plan: List<PaymentPlanDto>? = null,
    val unplanned: PaymentUnplannedDto? = null,
)
