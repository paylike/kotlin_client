package com.github.paylike.kotlin_client

/// RetryException is used for throwing an exception
/// when retry count is reached.
class RetryException(val attempts: Int): Exception("Reached maximum attempts in retrying")
