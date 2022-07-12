package com.github.paylike.kotlin_client

/**
 * Describes endpoints used.
 */
data class PaylikeHosts(
    var api: String = "https://b.paylike.io",
    var vault: String = "https://vault.paylike.io",
)
