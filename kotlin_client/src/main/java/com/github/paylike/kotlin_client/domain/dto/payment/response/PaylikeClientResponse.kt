package com.github.paylike.kotlin_client.domain.dto.payment.response

/** Describes the client response from the Paylike capture API */
class PaylikeClientResponse(
    val paymentResponse: PaymentResponse,
    val isHTML: Boolean,
    val htmlBody: String?,
)
