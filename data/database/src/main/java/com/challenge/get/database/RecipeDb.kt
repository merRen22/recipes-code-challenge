package com.challenge.get.database

import com.challenge.get.model.Recipe

/**
 * [Recipe] DB
 */
interface RecipeDb {
    /**
     * get all [Recipe]
     */
    suspend fun getAll(): List<Recipe>

    /**
     * get [Recipe] by id
     */
    suspend fun getByID(id: Int): Recipe

    /**
     * insert [Recipe]
     */
    suspend fun insert(recipe: Recipe)
}