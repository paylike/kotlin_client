package com.github.paylike.kotlin_client.request

import kotlinx.coroutines.Job
import java.util.concurrent.Future

interface RetryHandler<T> {
    fun retry(executor: () -> Job): Job
}
