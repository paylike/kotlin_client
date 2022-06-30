package com.github.paylike.kotlin_client.dto

import kotlinx.serialization.json.JsonObject

/**
 * Describes a response from tokenize.
 */
class TokenizedResponse(json: JsonObject) {
    val token: String = json["token"].toString()
}
