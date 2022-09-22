package com.github.paylike.kotlin_client.domain.dto.payment.request.money

import com.github.paylike.kotlin_client.exceptions.UnsafeNumberException
import kotlin.math.pow
import kotlinx.serialization.Serializable

/**
 * Describes payment amount. Has some other functionalities:
 * * Can generate PaymentAmount DTO from double: [PaymentAmount.fromDouble].
 * * Can check if a Long is in the safe range of numbers: [PaymentAmount.isInSafeRange]
 * @see <a href="https://github.com/paylike/api-reference/blob/main/payments/index.md#money">Api
 * Docs about the DTO</a>
 */
@Serializable
data class PaymentAmount(
    val currency: String,
    val value: Long,
    val exponent: Int,
) {
    /** Described globally and exposed to be able to use */
    companion object {
        const val maxInt: Long = 9007199254740991L

        fun isInSafeRange(n: Double): Boolean = (n <= maxInt && n >= -maxInt)

        fun fromDouble(currency: String, n: Double): PaymentAmount {
            if (isInSafeRange(n) || !n.isFinite()) {
                throw UnsafeNumberException(n)
            }
            val split = n.toString().split('.')
            val wholes = split[0]
            val fraction: String = split[1]
            val value: Long = Integer.parseInt(wholes + fraction).toLong()
            val exponent: Int =
                if (value == 0L) {
                    0
                } else {
                    fraction.length
                }
            return PaymentAmount(currency, value, exponent)
        }
    }
    fun calculateValue(): Number = value * 10.0.pow(exponent.toDouble())
}
