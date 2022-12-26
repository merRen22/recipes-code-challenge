package com.challenge.get.api.response

import com.challenge.get.model.Recipe
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response data for [Recipes]
 */
@Serializable
class RecipesResponse {

    @SerialName("id")
    var id: Int? = null

    @SerialName("name")
    var name: String? = null

    @SerialName("description")
    var description: String? = null

    @SerialName("ingredients")
    var ingredients: List<String>? = null

    @SerialName("image_url")
    var image_url: String? = null

    @SerialName("origin")
    var origin: Origin? = null


    fun toModel() = Recipe(
        id = id ?: 0,
        name = name?:"no name given",
        description = description?:"no description at the moment :(",
        ingredients = ingredients?: listOf("No items registered"),
        image = image_url?:"https://www.rawshorts.com/freeicons/wp-content/uploads/2017/01/orange_travelpictdinner_1484336833.png",
        origin = origin?.let {
            Pair(it.latitude,it.longitude)
        }?:Pair(0.0,0.0),
        origin_name = origin?.let { it.name }?:"no name given"
    )
}

@Serializable
data class Origin(
    val name: String,
    val latitude: Double,
    val longitude: Double
)