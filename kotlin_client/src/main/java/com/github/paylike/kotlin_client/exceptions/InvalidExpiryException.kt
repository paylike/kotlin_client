package com.github.paylike.kotlin_client.exceptions

import java.lang.Exception

/** In case the expiry set to invalid date we throw this. */
class InvalidExpiryException(month: Int, year: Int) : Exception() {
    override val message =
        (if (month > 12 || month < 1) "Invalid month: $month. " else "")
            .plus(if (year > 2099 || year < 2000) "Invalid year: $year. " else "")
            .plus("Invalid expiry date, month range is [1..12] and year range is [2000..2099]")
}
