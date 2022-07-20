package com.github.paylike.kotlin_client.domain.dto.payment.request.test

import kotlinx.serialization.Serializable

/**
 * Test Object for the payload of the requests.
 * Describes different use-cases what we expect
 * from the server to test them.
 */
@Serializable
data class PaymentTestDto(
    val nothing: String? = null, // TODO implement test subclasses, so far not needed tho
)
