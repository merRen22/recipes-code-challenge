package com.challenge.get.api

import com.challenge.get.api.response.ListResponse
import com.challenge.get.model.Recipe
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.http.GET

/**
 * Recipes API client
 */
class RecipeApiClient(retrofit: Retrofit) : RecipesApi {

    interface Service {

        @GET("071e5c74c8c42e37143d")
        suspend fun getRecipes(
        ): ListResponse
    }

    private val service = retrofit.create(Service::class.java)

    override suspend fun getRecipes(): List<Recipe> {
        return withContext(IO) {
            service.getRecipes().items?.let {
                it.map { item ->
                    item.toModel()
                }
            } ?: emptyList()
        }
    }
}