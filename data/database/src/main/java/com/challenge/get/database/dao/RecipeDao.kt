package com.challenge.get.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challenge.get.database.entity.RecipeEntity

/**
 * [Recipe] Dao
 */
@Dao
abstract class RecipeDao {

    @Query("SELECT * FROM recipe")
    abstract suspend fun getAll(): List<RecipeEntity>

    @Query("SELECT * FROM recipe WHERE id=:id")
    abstract suspend fun getByID(id: Int): RecipeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(detailEntity: RecipeEntity)

}