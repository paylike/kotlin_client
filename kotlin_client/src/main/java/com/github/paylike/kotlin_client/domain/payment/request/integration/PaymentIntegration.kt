package com.github.paylike.kotlin_client.domain.payment.request.integration

import kotlinx.serialization.Serializable

/**
 * Merchant's public key for the api
 */
@Serializable
data class PaymentIntegration(
    val key: String,
)
