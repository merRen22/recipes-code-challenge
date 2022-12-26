package com.challenge.get.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.challenge.get.database.dao.RecipeDao
import com.challenge.get.database.entity.RecipeEntity

/**
 * App Database
 */
@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val repositoryDao: RecipeDao
}