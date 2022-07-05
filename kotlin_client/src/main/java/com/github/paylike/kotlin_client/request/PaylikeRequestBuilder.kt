package com.github.paylike.kotlin_client.request

import kotlinx.coroutines.Job
import java.util.concurrent.Future

/**
 * PaylikeRequestBuilder provides a flexible way to add
 * custom retry mechanism into the flow.
 */
class PaylikeRequestBuilder<T> {
    /**
     * Handler to do retry
     */
    lateinit var retryHandler: RetryHandler<T>

    /**
     * Indicates if retry is enabled
     */
    var retryEnabled: Boolean = false

    /**
     * Async function to execute
     */
    lateinit var fn: () -> Job

    /**
     * Enables default retry - backoff mechanism.
     */
    constructor() {
        retryEnabled = true
        this.retryHandler = DefaultRetryHandler<T>()
    }

    /**
     * Receives a custom retry implementation and enables retry mechanism.
     */
    constructor(retryHandler: RetryHandler<T>) {
        retryEnabled = true
        this.retryHandler = retryHandler
    }

    /**
     * Without retry mechanism
     */
    constructor(fn: () -> Job) {
        retryEnabled = false
        this.fn = fn
    }

    /**
     * Executes the request
     */
    fun execute(): Job {
        return if (retryEnabled)
            retryHandler.retry(fn)
        else
            fn()
    }
}
