package com.github.paylike.kotlin_client.domain.dto.payment.response.challenges

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Types of challengeDtos */
@Serializable
enum class ChallengeTypes {
    @SerialName("fetch") FETCH,
    @SerialName("background-iframe") BACKGROUNDIFRAME,
    @SerialName("iframe") IFRAME,
}
