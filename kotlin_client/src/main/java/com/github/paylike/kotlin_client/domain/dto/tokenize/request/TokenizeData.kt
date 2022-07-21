package com.github.paylike.kotlin_client.domain.dto.tokenize.request

import kotlinx.serialization.Serializable

/**
 * Holds information for the tokenize request jsonbody
 *
 * For API reference
 * @see <a href="https://github.com/paylike/api-reference/blob/main/vault.md">link</a>
 */
@Serializable
data class TokenizeData(
    val type: TokenizeTypes,
    val value: String,
)
