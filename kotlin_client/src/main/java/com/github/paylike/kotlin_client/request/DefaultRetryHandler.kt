package com.github.paylike.kotlin_client.request

import com.github.paylike.kotlin_request.exceptions.RateLimitException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.lang.Exception
import java.util.concurrent.TimeoutException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
 * DefaultRetryHandler is used as the default retry backoff
 * mechanism for handling RateLimitExceptions.
 */
class DefaultRetryHandler<T>: RetryHandler<T> {
    /**
     * Counts the number of attempts made so far
     */
    private var attempts = 0

    /**
     * Gives back the duration suggested by the API
     * or a Duration based on the number of attempts if no
     * retry headers were provided.
     */
    private fun getRetryAfter(retryAfter: Duration?): Duration { // TODO Actual value of parameter retryAfter is always null
        var usedDuration = retryAfter ?: 0.milliseconds
        if (retryAfter == null) {
            usedDuration = when (attempts) {
                0 -> 0.milliseconds
                1 -> 0.milliseconds
                2 -> 100.milliseconds
                3 -> 2.seconds
                else -> 10.seconds
            }
        }
        return usedDuration
    }

    /**
     * Implementation of the retry mechanism.
     */
    override suspend fun retry(executor: () -> Job): Job {
        try {
            return executor()
        } catch (e: RateLimitException) {
            attempts++
            if (attempts > 10) {
                throw  e
            }
            delay(getRetryAfter(null))
            return retry(executor)
        } catch (e: TimeoutException) {
            attempts++
            if (attempts > 2) {
                throw e
            }
            return retry(executor)
        } catch (e: Exception) {
            throw e
        }
    }
}
