package com.github.paylike.kotlin_client.request

import java.util.concurrent.Future

interface RetryHandler<T> {
    fun retry(executor: () -> Future<T>): Future<T>
}
