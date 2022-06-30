package com.github.paylike.kotlin_client

/**
 * Describes endpoints used.
 */
class PaylikeHosts() {
    var api: String = "https://b.paylike.io"
    var vault: String = "https://vault.paylike.io"
    val applePayAPI: String =  "https://applepay.paylike.io/token"

    /**
     * Creates a new PaylikeHosts from two given URLs.
     */
    constructor(api: String, vault: String) : this() {
        this.api = api
        this.vault = vault
    }
}
