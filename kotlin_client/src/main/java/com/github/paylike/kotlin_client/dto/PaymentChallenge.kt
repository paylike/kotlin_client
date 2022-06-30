package com.github.paylike.kotlin_client.dto

import kotlinx.serialization.json.JsonObject

/**
 * PaymentChallenge describes a challenge after a payment creation
 * is initiated
 */
class PaymentChallenge(json: JsonObject) {
    var name: String = json["name"].toString()
    var type: String = json["type"].toString()
    var path: String = json["path"].toString()
}
