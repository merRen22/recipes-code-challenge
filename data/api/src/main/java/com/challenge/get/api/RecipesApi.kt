package com.challenge.get.api

import com.challenge.get.model.Recipe

/**
 * Recipe API
 */
interface RecipesApi {

    /**
     * get list of [Recipe] list from remote.
     */
    suspend fun getRecipes(): List<Recipe>
}