package com.github.paylike.kotlin_client.domain.dto.payment.request.test.tds

import kotlinx.serialization.Serializable

/**
 *  [challenge] is true by default for the API
 *  for "frictionless" flow use [challenge] = false
 */
@Serializable
data class TestTdsDto(
    val fingerprint: TdsFingerprintOptions? = null,
    val challenge: Boolean? = null,
    val status: TdsStatusOptions? = null,
)
