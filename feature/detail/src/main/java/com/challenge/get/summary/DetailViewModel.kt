package com.challenge.get.detail

import androidx.lifecycle.*
import com.challenge.get.base.ErrorHandler
import com.challenge.get.model.Recipe
import com.challenge.get.repository.RecipeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch


/**
 * [ViewModel] to store and manage detail of recipe.
 */
class DetailViewModel @AssistedInject constructor(
    @Assisted id: String,
    private val recipeRepository: RecipeRepository,
    private val errorHandler: ErrorHandler
) : ViewModel() {

    private val mutableRecipe: MutableLiveData<Recipe> = MutableLiveData()
    val recipe: LiveData<Recipe> = mutableRecipe

    init {
        findRecipe(id.toInt())
    }

    private fun findRecipe(id: Int) {
        viewModelScope.launch {
            runCatching {
                recipeRepository.findRecipeById(id)
            }.onSuccess { recipe ->
                mutableRecipe.value = recipe
            }.onFailure { error ->
                errorHandler.handleError(error)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: String): DetailViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            factory: Factory,
            bookId: String,
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(bookId) as T
            }
        }
    }
}
