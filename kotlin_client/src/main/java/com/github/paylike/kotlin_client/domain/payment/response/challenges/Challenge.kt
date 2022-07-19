package com.github.paylike.kotlin_client.domain.payment.response.challenges

import kotlinx.serialization.Serializable

@Serializable
data class Challenge(
    val name: String,
    val type: ChallengeTypes,
    val path: String,
)
