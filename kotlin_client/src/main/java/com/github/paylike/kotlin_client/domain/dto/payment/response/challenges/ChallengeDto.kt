package com.github.paylike.kotlin_client.domain.dto.payment.response.challenges

import kotlinx.serialization.Serializable

@Serializable
data class ChallengeDto(
    val name: String,
    val type: ChallengeTypes,
    val path: String,
)
