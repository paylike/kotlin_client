package com.github.paylike.kotlin_client.dto

import java.lang.Exception

/**
 * Describes the client response from the Paylike capture API
 */
class PaylikeClientResponse(
    @JvmField val paymentResponse: PaymentResponse?,
    val isHTML: Boolean,
    @JvmField val HTMLBody: String?,
    val hints: List<String> = mutableListOf(),
) {
    /**
     * Returns the payment response if not null
     * otherwise throws an exception
     */
    fun getPaymentResponse(): PaymentResponse {
        if (paymentResponse == null) {
            throw Exception("Payment response is null, cannot be acquired")
        }
        return paymentResponse
    }
    /**
     * Returns HTML body if not null
     * otherwise throws an exception
     */
    fun getHTMLBody(): String {
        if (HTMLBody == null) {
            throw Exception("HTMLBody is null, cannot be acquired")
        }
        return HTMLBody
    }
}
