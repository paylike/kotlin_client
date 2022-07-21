package com.github.paylike.kotlin_client.domain.dto.payment.request.test.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [SUPPORTED] default by the API
 */
@Serializable
enum class CardSchemeOptions {
    @SerialName("supported") SUPPORTED,
    @SerialName("unsupported") UNSUPPORTED,
    @SerialName("unknown") UNKNOWN,
}
