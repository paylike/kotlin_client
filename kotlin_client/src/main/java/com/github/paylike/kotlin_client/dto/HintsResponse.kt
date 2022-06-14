package com.github.paylike.kotlin_client.dto

// Describes the hints array received after executing a challenge successfully.
class HintsResponse(json: Map<String, Any>){
    val hints: List<String> = listOf()
    init {
        (json["hints"] as List<Any>).forEach { hints.plus(it.toString()) }
    }
}
