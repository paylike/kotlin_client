package com.github.paylike.kotlin_client.dto

// PaymentChallenge describes a challenge after a payment creation
// is initiated
class PaymentChallenge(json: Map<String, Any>) {
    var name = json["name"]
    var type = json["type"]
    var path = json["path"]
}
