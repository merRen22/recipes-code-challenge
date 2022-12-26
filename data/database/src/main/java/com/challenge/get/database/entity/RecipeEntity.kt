package com.challenge.get.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.challenge.get.model.Recipe

@Entity(tableName = "recipe")
class RecipeEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: String,
    val image: String,
    val origin: String,
    val origin_name: String
) {

    fun toModel() = Recipe(
        id = id,
        name = name,
        description = description,
        ingredients = ingredients.split(";"),
        image = image,
        origin = parseOrigin(origin.split(";")),
        origin_name = origin_name
    )

    private fun parseOrigin(coordinates: List<String>): Pair<Double,Double> {
        val lat = coordinates[0].toDouble()
        val lng = coordinates[1].toDouble()
        return Pair(lat, lng)
    }

    companion object {

        fun fromModel(recipe: Recipe): RecipeEntity {
            return RecipeEntity(
                id = recipe.id,
                name = recipe.name,
                description = recipe.description,
                ingredients = recipe.ingredients.joinToString(";"),
                image = recipe.image,
                origin = recipe.origin.toList().joinToString(";"),
                origin_name = recipe.origin_name
            )
        }
    }
}