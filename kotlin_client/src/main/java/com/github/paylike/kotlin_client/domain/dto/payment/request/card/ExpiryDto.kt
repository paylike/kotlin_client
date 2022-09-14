package com.github.paylike.kotlin_client.domain.dto.payment.request.card

import com.github.paylike.kotlin_client.exceptions.InvalidExpiryException
import kotlinx.serialization.Serializable

/**
 * Describes expiry information for cards
 *
 * [month] range 1..12 [year] range 2000..2099
 */
@Serializable
data class ExpiryDto(
    val month: Int,
    val year: Int,
) {
    init {
        if (month > 12 || month < 1 || year > 2099 || year < 2000) {
            throw InvalidExpiryException(month, year)
        }
    }
}
