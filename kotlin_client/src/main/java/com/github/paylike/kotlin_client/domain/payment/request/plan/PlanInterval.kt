package com.github.paylike.kotlin_client.domain.payment.request.plan

import kotlinx.serialization.Serializable

/**
 * Subsequent payments occur each [unit] * [value] after [PlanRepeat.first].
 * [value] defaults to 1.
 */
@Serializable
data class PlanInterval(
    val unit: PlanIntervalUnit,
    val value: Int? = null,
)
