package com.github.paylike.kotlin_client.domain.payment.request.card

import kotlinx.serialization.Serializable

/**
 * Describes expiry information for cards
 */
@Serializable
data class Expiry(
    val month: Int, // 1..12 // TODO make these constraints
    val year: Int, // 2000..2099
)
