package com.github.paylike.kotlin_client.exceptions

class UnsafeNumberException(n: Double) : ClientException() {
    override val message: String = "Number is not in safe range $n"
}
