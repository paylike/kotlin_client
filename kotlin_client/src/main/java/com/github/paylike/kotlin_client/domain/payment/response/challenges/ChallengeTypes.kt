package com.github.paylike.kotlin_client.domain.payment.response.challenges

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Types of challenges
 */
@Serializable
enum class ChallengeTypes {
    @SerialName("fetch") FETCH,
    @SerialName("background-iframe") BACKGROUNDIFRAME,
    @SerialName("iframe") IFRAME,
}
