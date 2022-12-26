package com.challenge.get.database

import com.challenge.get.database.dao.RecipeDao
import com.challenge.get.database.entity.RecipeEntity
import com.challenge.get.model.Recipe
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class RecipeDbClient(private val recipeDao: RecipeDao) : RecipeDb {
    override suspend fun getAll(): List<Recipe> =
        recipeDao.getAll().map { recipe -> recipe.toModel() }

    override suspend fun getByID(id: Int): Recipe =
        recipeDao.getByID(id).toModel()

    override suspend fun insert(recipe: Recipe) {
        withContext(IO) {
            recipeDao.insert(RecipeEntity.fromModel(recipe))
        }
    }
}