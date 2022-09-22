package com.github.paylike.kotlin_client.domain.dto.payment.request.plan

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/**
 * Repeating components begin at [first] (defaulting to "now") and repeat indefinitely or [count]
 * times at a fixed interval [interval].
 *
 * Limitations: [count] is optional only for the last component. [first] must be chronologically
 * later than the previous component.
 */
@Serializable
data class PlanRepeatDto(
    val first: LocalDateTime? = null,
    val count: Int? = null,
    val interval: PlanIntervalDto,
)
