package com.github.paylike.kotlin_client

import kotlin.random.Random

// Handles high level requests towards the paylike ecosystem
class PaylikeClient {
     // Generates a new client ID
     companion object {
         fun generateClientID(): String {
             val chars: String = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890"
             val rnd = Random.Default
             var uniqueString = String()
             for (i in 0..5)
                 uniqueString += chars[rnd.nextInt(chars.length)]
             return "kotlin-1-$uniqueString"
         }
     }

    // SDK Client ID used while making requests
    //
    // Note: This is not the merchant's client ID
    val clientId = generateClientID()

    
}
