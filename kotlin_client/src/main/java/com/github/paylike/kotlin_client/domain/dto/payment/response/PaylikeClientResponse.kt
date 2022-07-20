package com.github.paylike.kotlin_client.domain.dto.payment.response

import java.lang.Exception

/**
 * Describes the client response from the Paylike capture API
 */
class PaylikeClientResponse(
    val paymentResponse: PaymentResponse,
    val isHTML: Boolean,
    htmlBody: String?,
) {
    /**
     * Returns HTML body if not null
     * otherwise throws an exception
     */
    val htmlBody: String? = htmlBody
        get() {
            if (field == null) {
            throw Exception("HTMLBody is null, cannot be acquired")
        }
            return field
        }
}
