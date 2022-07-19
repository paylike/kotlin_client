package com.github.paylike.kotlin_client.domain.payment.request.plan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Options for interval units.
 */
@Serializable
enum class PlanIntervalUnit {
    @SerialName("day") DAY,
    @SerialName("week") WEEK,
    @SerialName("month") MONTH,
    @SerialName("year") YEAR,
}
