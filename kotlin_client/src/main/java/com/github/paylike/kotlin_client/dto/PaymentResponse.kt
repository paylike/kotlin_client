package com.github.paylike.kotlin_client.dto

// Describes a payment response.
class PaymentResponse(json: Map<String, Any>) {
    val transaction: PaylikeTransaction
    val custom: Map<String, Any>
    init {
        transaction = PaylikeTransaction(json)
        custom = json["custom"] as Map<String, Any>
    }
}
