package com.example.project2

data class event(
    val name: String,
    val url: String,
    val urlToImage: String
){
    constructor() : this("", "", "")
}

