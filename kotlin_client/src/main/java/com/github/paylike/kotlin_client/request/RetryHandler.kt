package com.github.paylike.kotlin_client.request

import kotlinx.coroutines.Job

interface RetryHandler<T> {
    suspend fun retry(executor: () -> Job): Job
}
