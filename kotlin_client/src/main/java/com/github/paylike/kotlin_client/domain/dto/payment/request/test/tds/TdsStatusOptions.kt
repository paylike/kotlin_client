package com.github.paylike.kotlin_client.domain.dto.payment.request.test.tds

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** [AUTHENTICATED] default by the API */
@Serializable
enum class TdsStatusOptions {
    @SerialName("authenticated") AUTHENTICATED,
    @SerialName("rejected") REJECTED,
    @SerialName("unavailable") UNAVAILABLE,
    @SerialName("attempted") ATTEMPTED,
}
