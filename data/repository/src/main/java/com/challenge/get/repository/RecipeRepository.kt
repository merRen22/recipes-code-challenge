package com.challenge.get.repository

import com.challenge.get.api.RecipesApi
import com.challenge.get.database.RecipeDb
import com.challenge.get.model.Recipe
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for [Recipe]
 */
@Singleton
class RecipeRepository @Inject constructor(
    private val recipesApi: RecipesApi,
    private val recipeDb: RecipeDb
) {

    /**
     * Makes a request to the api to get the recipes from the api
     */
    suspend fun getRecipes(): List<Recipe> {
        return recipesApi.getRecipes()
    }

    /**
     * Makes a request to the local DB to get the recipes
     */
    suspend fun findAllRecipes(): List<Recipe> {
        return recipeDb.getAll()
    }

    /**
     * Makes a request to the local DB to get the recipe based on the ID
     */
    suspend fun findRecipeById(id: Int): Recipe {
        return recipeDb.getByID(id)
    }

    /**
     * Inserts a [recipe] to the local db
     */
    suspend fun addRecipe(recipe: Recipe) {
        return recipeDb.insert(recipe)
    }

}