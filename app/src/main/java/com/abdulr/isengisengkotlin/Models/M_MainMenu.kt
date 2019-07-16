package com.abdulr.isengisengkotlin.Models

class M_MainMenu(private val body: String, private val isactive: Boolean) {

    fun getBody(): String {
        return body
    }

    fun getIsactive(): Boolean? {
        return isactive
    }
}