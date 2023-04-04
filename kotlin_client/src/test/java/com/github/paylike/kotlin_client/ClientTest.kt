package com.github.paylike.kotlin_client

import com.github.paylike.kotlin_client.domain.dto.payment.request.PaymentData
import com.github.paylike.kotlin_client.domain.dto.payment.request.card.ExpiryDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.card.PaylikeCardDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.integration.PaymentIntegrationDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.money.PaymentAmount
import com.github.paylike.kotlin_client.domain.dto.payment.request.test.PaymentTestDto
import com.github.paylike.kotlin_client.domain.dto.tokenize.request.TokenizeData
import com.github.paylike.kotlin_client.domain.dto.tokenize.request.TokenizeTypes
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
        if (BuildConfig.PAYLIKE_MERCHANT_API_KEY.isNullOrEmpty()) {
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
                    integration = PaymentIntegrationDto(BuildConfig.PAYLIKE_MERCHANT_API_KEY),
                    amount = paymentAmount,
                    card = paymentCard,
                )
            val responsePayment = client.paymentCreate(paymentData)
            assertTrue(responsePayment.isHTML)
            assertTrue(responsePayment.htmlBody!!.isNotEmpty())
        }
    }
}
