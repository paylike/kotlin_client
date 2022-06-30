package com.github.paylike.kotlin_client.dto

import kotlinx.serialization.json.JsonObject

/**
 * Describes a paylike transaction.
 */
class PaylikeTransaction(val id: String) {
    constructor(json: JsonObject) :
            this((json["authorizationId"] ?: json["transactionId"]) as String)
}
