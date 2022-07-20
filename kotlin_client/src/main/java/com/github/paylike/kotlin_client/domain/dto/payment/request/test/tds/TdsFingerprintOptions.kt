package com.github.paylike.kotlin_client.domain.dto.payment.request.test.tds

import com.github.paylike.kotlin_client.domain.dto.payment.request.test.FingerprintOptions.SUCCESS
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [SUCCESS] default by the API
 */
@Serializable
enum class TdsFingerprintOptions {
    @SerialName("timeout") TIMEOUT,
    @SerialName("unavailable") UNAVAILABLE,
    @SerialName("success") SUCCESS,
}