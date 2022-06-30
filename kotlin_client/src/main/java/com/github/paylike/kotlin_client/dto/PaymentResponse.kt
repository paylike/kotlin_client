package com.github.paylike.kotlin_client.dto

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

/**
 * Describes a payment response.
 */
class PaymentResponse(json: JsonObject) {
    val transaction: PaylikeTransaction
    val custom: JsonElement?
    init {
        transaction = PaylikeTransaction(json)
        custom = json["custom"]
    }
}
