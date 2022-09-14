package com.github.paylike.kotlin_client.domain

import com.github.paylike.kotlin_client.PaylikeClient

/**
 * Describes used endpoints
 *
 * [api] for the [PaylikeClient.paymentCreate] method and [vault] for the [PaylikeClient.tokenize]
 * method
 */
data class PaylikeHosts(
    var api: String = "https://b.paylike.io",
    var vault: String = "https://vault.paylike.io",
)
