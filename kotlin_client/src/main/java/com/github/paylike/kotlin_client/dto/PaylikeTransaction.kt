package com.github.paylike.kotlin_client.dto

// Describes a paylike transaction.
class PaylikeTransaction(val id: String) {
    constructor(json: Map<String, Any>) :
            this((json["authorizationId"] ?: json["transactionId"]) as String)
}
