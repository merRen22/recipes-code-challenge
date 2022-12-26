package com.challenge.get.database.di

import android.app.Application
import androidx.room.Room
import com.challenge.get.database.AppDatabase
import com.challenge.get.database.RecipeDb
import com.challenge.get.database.RecipeDbClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "database-name").build()
    }

    @Provides
    @Singleton
    fun provideRecipeDb(appDatabase: AppDatabase): RecipeDb {
        return RecipeDbClient(appDatabase.repositoryDao)
    }
}