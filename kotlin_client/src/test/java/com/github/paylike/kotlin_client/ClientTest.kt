package com.github.paylike.kotlin_client

import com.github.paylike.kotlin_client.domain.payment.request.PaymentData
import com.github.paylike.kotlin_client.domain.payment.request.card.Expiry
import com.github.paylike.kotlin_client.domain.payment.request.card.PaylikeCard
import com.github.paylike.kotlin_client.domain.payment.request.integration.PaymentIntegration
import com.github.paylike.kotlin_client.domain.payment.request.test.PaymentTest
import com.github.paylike.kotlin_client.domain.tokenize.request.TokenizeData
import com.github.paylike.kotlin_client.domain.tokenize.request.TokenizeTypes
import com.github.paylike.kotlin_money.PaymentAmount
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*

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
        runBlocking {
            val responseCard = client.tokenize(TokenizeData(TokenizeTypes.PCN, "4100000000000000"))
            val responseCode = client.tokenize(TokenizeData(TokenizeTypes.PCSC, "111"))
            assertTrue(responseCard.token.isNotEmpty())
            assertTrue(responseCode.token.isNotEmpty())

            val paymentCard = PaylikeCard(
                number = responseCard,
                cvc = responseCode,
                expiry = Expiry(12,2022),
            )
            val paymentAmount = PaymentAmount("EUR", 1, 0)
            val paymentData = PaymentData(
                test = PaymentTest(),
                integration = PaymentIntegration(BuildConfig.E2E_Client_KEY),
                amount = paymentAmount,
                card = paymentCard,
            )
            val responsePayment = client.paymentCreate(paymentData)
            assertTrue(responsePayment.isHTML)
            assertTrue(responsePayment.htmlBody!!.isNotEmpty())
        }
    }
}
