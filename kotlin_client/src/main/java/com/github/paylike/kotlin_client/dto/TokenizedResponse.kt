package com.github.paylike.kotlin_client.dto

// Describes a response from tokenize.
class TokenizedResponse(json: Map<String, Any>) {
    val token: String = json["token"] as String
}
