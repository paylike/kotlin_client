package com.github.paylike.kotlin_client.domain

/**
 * Describes endpoints used.
 *
 * [api] for the [PaylikeClient.paymentCreate] method
 * [vault] for the [PaylikeClient.tokenize] method
 */
data class PaylikeHosts(
    var api: String = "https://b.paylike.io",
    var vault: String = "https://vault.paylike.io",
)
