package com.github.paylike.kotlin_client.exceptions

class UnsafeNumberException(n: Double) : Exception() {
    override val message: String = "Number is not in safe range $n"
}
