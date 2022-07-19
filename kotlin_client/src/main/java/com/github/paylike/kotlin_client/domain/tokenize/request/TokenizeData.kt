package com.github.paylike.kotlin_client.domain.tokenize.request

import kotlinx.serialization.Serializable

/**
 * Holds information for the tokenize request jsonbody
 */
@Serializable
data class TokenizeData(
    val type: TokenizeTypes,
    val value: String,
)
