package com.github.paylike.kotlin_client.exceptions

import java.lang.Exception

/**
 * In case the expiry set to invalid date we throw this.
 */
class InvalidExpiryException: Exception() {
    override val message = "Invalid expiry date, month range is [1..12] and year range is [2000..2099]"
}
