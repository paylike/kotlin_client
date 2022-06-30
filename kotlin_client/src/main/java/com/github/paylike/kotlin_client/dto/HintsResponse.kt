package com.github.paylike.kotlin_client.dto

import kotlinx.serialization.json.*

/**
 * Describes the hints array received after executing a challenge successfully.
 */
class HintsResponse(json: JsonObject) {
    val hints: List<String>
    init {
        hints = if (json["hints"] !== null) {
            Json.decodeFromJsonElement(json["hints"]!!)
        } else {
            listOf()
        }
    }
}
