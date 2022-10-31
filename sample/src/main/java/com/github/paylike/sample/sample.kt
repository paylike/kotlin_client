package com.github.paylike.sample

import com.github.paylike.kotlin_client.PaylikeClient
import com.github.paylike.kotlin_client.domain.dto.payment.request.PaymentData
import com.github.paylike.kotlin_client.domain.dto.payment.request.card.ExpiryDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.card.PaylikeCardDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.integration.PaymentIntegrationDto
import com.github.paylike.kotlin_client.domain.dto.payment.request.money.PaymentAmount
import com.github.paylike.kotlin_client.domain.dto.tokenize.request.TokenizeData
import com.github.paylike.kotlin_client.domain.dto.tokenize.request.TokenizeTypes
import kotlinx.coroutines.runBlocking

/** This a dummy example usage flow, this code wont execute successfully. */
fun main() {
    // create client instance
    val client = PaylikeClient("TestID01")
    // get your merchant id, preferably from environmental variable
    // in your local.properties file set: PaylikeMerchantApiKey=<yourMerchantId>
    val yourMerchantId = "PaylikeMerchantApiKey"

    // indicate coroutineScope
    runBlocking {
        // Tokenize the card data
        val responseCard = client.tokenize(TokenizeData(TokenizeTypes.PCN, "4012111111111111"))
        val responseCode = client.tokenize(TokenizeData(TokenizeTypes.PCSC, "111"))

        // create the payment dto
        val paymentAmount = PaymentAmount("EUR", 1, 0)
        val paymentCard =
            PaylikeCardDto(
                number = responseCard,
                cvc = responseCode,
                expiry = ExpiryDto(12, 2023),
            )
        val paymentData =
            PaymentData(
                integration = PaymentIntegrationDto(yourMerchantId), // here comes your merchant id
                amount = paymentAmount,
                card = paymentCard,
            )

        // Payment flow:
        // Send the payment request
        val tdsResponse = client.paymentCreate(paymentData)
        // If the response is HTML, then you encountered a payment challenge
        // like TDS
        if (tdsResponse.isHTML) {
            val htmlBody = tdsResponse.htmlBody
            val hints = tdsResponse.paymentResponse.hints!!
            // handle tds
            // ...
            // get the hint form the tds
            val tdsHint = String() // dummy hint from tds
            // add it to the list
            val freshSet = mutableSetOf<String>()
            freshSet.addAll(hints)
            freshSet.add(tdsHint)
            paymentData.hints = freshSet.toList()
            // after auth we start continue the payment process
            val paymentResponse = client.paymentCreate(paymentData)
            // and finally we get the transactionId at the end of the flow
            val transactionId = paymentResponse.paymentResponse.transactionId
        }
    }
}
