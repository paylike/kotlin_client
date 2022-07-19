package com.github.paylike.sample

import com.github.paylike.kotlin_client.PaylikeClient
import kotlinx.coroutines.runBlocking

fun main()
{
    val client = PaylikeClient("TestID01")
    runBlocking {
        println("hello")
    }
}
