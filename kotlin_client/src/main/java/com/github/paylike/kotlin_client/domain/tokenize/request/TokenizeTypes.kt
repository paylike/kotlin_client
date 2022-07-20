package com.github.paylike.kotlin_client.domain.tokenize.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * TokenizeTypes describe the options for tokenizing card number and code
 * [PCN] for card number
 * [PCSC] for CVC code
 */
@Serializable
enum class TokenizeTypes {
    @SerialName("pcn") PCN,
    @SerialName("pcsc") PCSC,
}
