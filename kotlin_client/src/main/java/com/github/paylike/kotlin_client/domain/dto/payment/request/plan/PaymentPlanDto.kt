package com.github.paylike.kotlin_client.domain.dto.payment.request.plan

import com.github.paylike.kotlin_money.PaymentAmount
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/**
 * This field specifies future payments that is being agreed upon as of this payment order.
 *
 * Please note that the payment plan is not automatically executed. This field merely represents the
 * agreement made between customer and merchant as of this payment order.
 *
 * Each component represents an amount [amount] to be due at a future date either once [scheduled]
 * or repeated.
 *
 * Limitation: [scheduled] must be chronologically later than the previous component.
 */
@Serializable
data class PaymentPlanDto(
    val amount: PaymentAmount,
    val scheduled: LocalDateTime? = null,
    val repeat: PlanRepeatDto? = null,
)
