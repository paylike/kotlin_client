package com.github.paylike.kotlin_client.domain.dto.payment.request.test.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [VALID] default by the API
 */
@Serializable
enum class CardStatusOptions {
    @SerialName("valid") VALID,
    @SerialName("invalid") INVALID,
    @SerialName("expired") EXPIRED,
    @SerialName("disabled") DISABLED,
    @SerialName("lost") LOST,
}
