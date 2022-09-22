package com.github.paylike.kotlin_client.domain.dto.payment.request.test

import com.github.paylike.kotlin_client.domain.dto.payment.request.test.card.TestCardDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.test.tds.TestTdsDto
import kotlinx.serialization.Serializable

/**
 * Test Object for the payload of the requests. Describes different use-cases what we expect from
 * the server to test them.
 *
 * For API reference
 * @see <a href="https://github.com/paylike/api-reference/blob/main/payments/index.md#test">link</a>
 */
@Serializable
data class PaymentTestDto(
    val card: TestCardDto? = null,
    val fingerprint: FingerprintOptions? = null,
    val tds: TestTdsDto? = null,
)
