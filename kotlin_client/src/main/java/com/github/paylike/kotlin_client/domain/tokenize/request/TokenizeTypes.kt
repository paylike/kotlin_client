package com.github.paylike.kotlin_client.domain.tokenize.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * TokenizeTypes describe the options for tokenizing card number and code
 */
@Serializable
enum class TokenizeTypes {
    /**
     * For card number
     */
    @SerialName("pcn") PCN,
    /**
     * For CVC codes
     */
    @SerialName("pcsc") PCSC,
}
