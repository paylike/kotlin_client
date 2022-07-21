package com.github.paylike.kotlin_client.domain.dto.payment.request.test

import com.github.paylike.kotlin_client.domain.dto.payment.request.test.card.CardStatusOptions.VALID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [SUCCESS] default by the API
 */
@Serializable
enum class FingerprintOptions {
    @SerialName("timeout") TIMEOUT,
    @SerialName("success") SUCCESS,
}
