package com.challenge.get.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.challenge.get.base.compose.largePadding
import com.challenge.get.base.compose.normalPadding
import com.challenge.get.base.compose.smallPadding
import com.challenge.get.model.Recipe

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel,
    onItemClicked: (Int) -> Unit
) {
    val recipe = detailViewModel.recipe.observeAsState()
    recipe.value?.let {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .systemBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            RecipeDescription(it, onItemClicked)
        }
    } ?: ProgressIndicator()
}

@Composable
fun RecipeDescription(recipe: Recipe, onItemClicked: (Int) -> Unit) {
    Column(modifier = Modifier.padding(largePadding), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            recipe.name,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            modifier = Modifier.padding(vertical = normalPadding)
        )
        AsyncImage(
            model = recipe.image,
            contentDescription = recipe.name,
            modifier = Modifier.fillMaxWidth().padding(vertical = normalPadding)
        )
        Button(onClick = { onItemClicked(recipe.id) },  shape = RoundedCornerShape(20.dp)) {
            Text(text = "Place of origin")
        }
        Text(
            recipe.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = normalPadding)
        )
        for (ingredient in recipe.ingredients) {
            Text(
                "- $ingredient",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = smallPadding)
            )
        }
    }
}


@Composable
fun ProgressIndicator() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
