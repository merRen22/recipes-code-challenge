package com.challenge.get.model

data class Recipe (
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val image: String,
    val origin: Pair<Double,Double>,
    val origin_name: String
)