package com.github.paylike.kotlin_client

import com.github.paylike.kotlin_client.domain.dto.payment.request.PaymentData
import com.github.paylike.kotlin_client.domain.dto.payment.request.card.ExpiryDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.card.PaylikeCardDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.integration.PaymentIntegrationDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.money.PaymentAmount
import com.github.paylike.kotlin_client.domain.dto.payment.request.test.PaymentTestDto
import com.github.paylike.kotlin_client.domain.dto.tokenize.request.TokenizeData
import com.github.paylike.kotlin_client.domain.dto.tokenize.request.TokenizeTypes
import com.github.paylike.kotlin_client.exceptions.InvalidExpiryException
import java.lang.Exception
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class ClientTest {
    private val client = PaylikeClient("TestID01")

    @Test
    fun tokenizeCardTest() {
        runBlocking {
            val responseCard = client.tokenize(TokenizeData(TokenizeTypes.PCN, "4100000000000000"))
            assertTrue(responseCard.token.isNotEmpty())
        }
    }
    @Test
    fun tokenizeCvcTest() {
        runBlocking {
            val responseCode = client.tokenize(TokenizeData(TokenizeTypes.PCSC, "111"))
            assertTrue(responseCode.token.isNotEmpty())
        }
    }
    @Test
    fun paymentCreation() {
        if (BuildConfig.PaylikeMerchantApiKey.isNullOrEmpty()) {
            throw Exception("The environmental field is not set to local.properties file")
        }
        runBlocking {
            val responseCard = client.tokenize(TokenizeData(TokenizeTypes.PCN, "4100000000000000"))
            val responseCode = client.tokenize(TokenizeData(TokenizeTypes.PCSC, "111"))
            assertTrue(responseCard.token.isNotEmpty())
            assertTrue(responseCode.token.isNotEmpty())

            val paymentCard =
                PaylikeCardDto(
                    number = responseCard,
                    cvc = responseCode,
                    expiry = ExpiryDto(12, 2023),
                )
            val paymentAmount = PaymentAmount("EUR", 1, 0)
            val paymentData =
                PaymentData(
                    test = PaymentTestDto(),
                    integration = PaymentIntegrationDto(BuildConfig.PaylikeMerchantApiKey),
                    amount = paymentAmount,
                    card = paymentCard,
                )
            val responsePayment = client.paymentCreate(paymentData)
            assertTrue(responsePayment.isHTML)
            assertTrue(responsePayment.htmlBody!!.isNotEmpty())
        }
    }
    @Test
    fun expiryExceptionTest() {
        var invalidExpiryException =
            assertThrows(InvalidExpiryException::class.java) {
                var expiry = ExpiryDto(0, 2023)
            }
        assertTrue(invalidExpiryException is InvalidExpiryException)
        assertEquals(
            "Invalid expiry date, month range is [1..12] and year range is [2000..2099]",
            invalidExpiryException.message
        )

        invalidExpiryException =
            assertThrows(InvalidExpiryException::class.java) {
                var expiry = ExpiryDto(13, 2023)
            }
        assertTrue(invalidExpiryException is InvalidExpiryException)
        assertEquals(
            "Invalid expiry date, month range is [1..12] and year range is [2000..2099]",
            invalidExpiryException.message
        )
        invalidExpiryException =
            assertThrows(InvalidExpiryException::class.java) {
                var expiry = ExpiryDto(1, 1999)
            }
        assertTrue(invalidExpiryException is InvalidExpiryException)
        assertEquals(
            "Invalid expiry date, month range is [1..12] and year range is [2000..2099]",
            invalidExpiryException.message
        )

        invalidExpiryException =
            assertThrows(InvalidExpiryException::class.java) {
                var expiry = ExpiryDto(1, 2100)
            }
        assertTrue(invalidExpiryException is InvalidExpiryException)
        assertEquals(
            "Invalid expiry date, month range is [1..12] and year range is [2000..2099]",
            invalidExpiryException.message
        )
    }
}
