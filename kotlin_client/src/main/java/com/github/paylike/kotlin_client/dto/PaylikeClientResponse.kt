package com.github.paylike.kotlin_client.dto

import java.lang.Exception

/**
 * Describes the client response from the Paylike capture API
 */
class PaylikeClientResponse(
    paymentResponse: PaymentResponse?,
    val isHTML: Boolean,
    HTMLBody: String?,
    val hints: List<String> = mutableListOf(),
) {
    /**
     * Returns the payment response if not null
     * otherwise throws an exception
     */
    val paymentResponse: PaymentResponse? = paymentResponse
        get() {
            if (field == null) {
                    throw Exception("Payment response is null, cannot be acquired")
                }
            return field
        }

    /**
     * Returns HTML body if not null
     * otherwise throws an exception
     */
    val htmlBody: String? = HTMLBody
        get() {
            if (field == null) {
            throw Exception("HTMLBody is null, cannot be acquired")
        }
            return field
        }
}
