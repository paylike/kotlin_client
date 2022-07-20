package com.github.paylike.kotlin_client.domain.dto.payment.request.plan

import kotlinx.serialization.Serializable

/**
 * Subsequent payments occur each [unit] * [value] after [PlanRepeatDto.first].
 * [value] defaults to 1.
 */
@Serializable
data class PlanIntervalDto(
    val unit: PlanIntervalUnit,
    val value: Int? = null,
)
