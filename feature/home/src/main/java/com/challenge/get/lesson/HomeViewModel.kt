package com.challenge.get.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.get.base.ErrorHandler
import com.challenge.get.model.Recipe
import com.challenge.get.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [ViewModel] to manage home view data.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipesRepository: RecipeRepository,
    private val errorHandler: ErrorHandler
) : ViewModel() {
    private val mutableRecipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    private val mutableError: MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var allRecipes: List<Recipe>

    val recipes: LiveData<List<Recipe>> get() = mutableRecipes
    val error: LiveData<Boolean> get() = mutableError

    private fun findRecipes() {
        viewModelScope.launch {
            runCatching {
                recipesRepository.findAllRecipes()
            }.onSuccess { recipes ->
                if (recipes.isEmpty()) {
                    fetchRecipes()
                } else {
                    allRecipes = recipes
                    mutableError.value = false
                    mutableRecipes.value = recipes as MutableList<Recipe>
                }
            }.onFailure { error ->
                mutableError.value = true
                if (error !is CancellationException) {
                    errorHandler.handleError(error)
                }
            }
        }
    }

    private fun fetchRecipes() {
        viewModelScope.launch {
            runCatching {
                recipesRepository.getRecipes()
            }.onSuccess { recipes ->
                for (recipe in recipes) {
                    save(recipe)
                }
                allRecipes = recipes
                mutableError.value = false
                mutableRecipes.value = recipes as MutableList<Recipe>
            }.onFailure { error ->
                mutableError.value = true
                if (error !is CancellationException) {
                    errorHandler.handleError(error)
                }
            }
        }
    }

    private fun save(recipe: Recipe) {
        viewModelScope.launch {
            runCatching {
                recipesRepository.addRecipe(recipe)
            }.onSuccess { _ ->
                Log.i("DB", "Success DB saved")
            }.onFailure { error ->
                errorHandler.handleError(error)
            }
        }
    }

    fun filterRecipes(searchTerm: String) {
        if (searchTerm.isEmpty()) {
            mutableRecipes.value = allRecipes
            return
        }

        mutableRecipes.value = allRecipes.filter {
            it.name.contains(searchTerm, ignoreCase = true) || it.ingredients.any { ingredient -> ingredient.contains(searchTerm, ignoreCase = true) }
        }
    }

    init {
        findRecipes()
    }
}