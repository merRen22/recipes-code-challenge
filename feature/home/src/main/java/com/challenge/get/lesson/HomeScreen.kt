package com.challenge.get.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.challenge.get.base.compose.normalPadding
import com.challenge.get.base.compose.smallPadding
import com.challenge.get.model.Recipe

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onItemClicked: (Int) -> Unit,
) {
    val recipes = homeViewModel.recipes.observeAsState()
    val error = homeViewModel.error.observeAsState()
    val searchText = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if(error.value == true) {
            ScreenState(ScreenStatus.ERROR)
        } else {
            recipes.value?.let {
                Card(modifier = Modifier.padding(vertical = smallPadding)) {
                    TextField(
                        value = searchText.value,
                        onValueChange = {
                            searchText.value = it
                            homeViewModel.filterRecipes(searchText.value.text)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Search") },
                        textStyle = MaterialTheme.typography.bodyMedium,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "",
                                modifier = Modifier.padding(15.dp).size(24.dp)
                            )
                        },
                        singleLine = true,
                        shape = RectangleShape,
                    )
                }
                if (it.isEmpty()) {
                    ScreenState(ScreenStatus.EMPTY)
                } else {
                    LazyColumn( modifier = Modifier.fillMaxHeight()) {
                        item {
                            it.forEach { recipe ->
                                Row(
                                    modifier = Modifier
                                        .clickable { onItemClicked(recipe.id) }
                                        .padding(vertical = 16.dp)
                                ) {
                                    RecipeRow(recipe = recipe)
                                }
                            }
                        }
                    }
                }
            }?:ScreenState(ScreenStatus.LOADING)
        }
    }
}

@Composable
fun ScreenState(status: ScreenStatus) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (status) {
                ScreenStatus.LOADING -> {
                    CircularProgressIndicator()
                }
                ScreenStatus.ERROR -> {
                    Text(
                        text = "There is no connection to internet and no items are stored in the DB",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(vertical = normalPadding),
                        textAlign = TextAlign.Justify,
                    )
                    Text(
                        text = "Retry with an internet connection",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(vertical = normalPadding),
                        textAlign = TextAlign.Justify,
                    )
                }
                ScreenStatus.EMPTY -> {
                    Text(
                        text = "sorry, we don't have item with those ingredients or name",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(vertical = normalPadding),
                        textAlign = TextAlign.Justify,
                    )
                    Text(
                        text = "Try with another term",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(vertical = normalPadding),
                        textAlign = TextAlign.Justify,
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeRow(
    recipe: Recipe,
) {
    Card(modifier = Modifier.padding(vertical = smallPadding)) {
        Column(modifier = Modifier.padding(vertical = smallPadding)) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = normalPadding)
            )
            AsyncImage(
                model = recipe.image,
                contentDescription = recipe.name
            )
        }
    }
}

enum class ScreenStatus {
    LOADING,
    ERROR,
    EMPTY
}

