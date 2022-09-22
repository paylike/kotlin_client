package com.github.paylike.kotlin_client.domain.dto.payment.request.unplanned

import kotlinx.serialization.Serializable

/**
 * Flag the types of unplanned payments the card will be used for. The supported types are:
 * [costumer] (initiated by the customer from your website/application) [merchant] (initiated by the
 * merchant or an off-site customer) This is required for unplanned subsequent payments to ensure
 * compliance and high approval rates.
 */
@Serializable
data class PaymentUnplannedDto(
    val costumer: Boolean? = null,
    val merchant: Boolean? = null,
)
