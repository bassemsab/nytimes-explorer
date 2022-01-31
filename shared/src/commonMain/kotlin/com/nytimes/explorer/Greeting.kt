package com.nytimes.explorer

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}