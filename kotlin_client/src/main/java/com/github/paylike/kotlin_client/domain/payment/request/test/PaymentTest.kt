package com.github.paylike.kotlin_client.domain.payment.request.test

import kotlinx.serialization.Serializable

/**
 * Test Object for the payload of the requests.
 * Describes different use-cases what we expect
 * from the server to test them.
 */
@Serializable
data class PaymentTest(
    val nothing: String? = null, // TODO implement test subclasses, so far not needed tho
)
