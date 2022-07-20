package com.github.paylike.kotlin_client.domain.dto.payment.request.integration

import kotlinx.serialization.Serializable

/**
 * Merchant's public key for the api
 */
@Serializable
data class PaymentIntegrationDto(
    val key: String,
)
