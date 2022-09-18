package com.abhinav12k.drunkyard.presentation.drinkDetail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.R
import com.abhinav12k.drunkyard.common.CustomVertical2DRow
import com.abhinav12k.drunkyard.common.NetworkImage
import com.abhinav12k.drunkyard.domain.model.DrinkDetail
import com.abhinav12k.drunkyard.domain.model.Ingredient

@Composable
fun DrinkDetailScreen(
    viewModel: DrinkDetailViewModel,
    drinkId: String
) {
    LaunchedEffect(key1 = drinkId) {
        viewModel.getDrinkDetailsById(drinkId)
    }
    val viewState = viewModel.drinkDetailViewState.value

    Box(modifier = Modifier.fillMaxSize()) {
        if (viewState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (!viewState.error.isNullOrBlank()) {
            Text(
                text = viewState.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (viewState.drinkDetail != null) {
            DrinkDetailScreenContent(
                viewState.drinkDetail,
                Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun DrinkDetailScreenContent(
    drinkDetail: DrinkDetail,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            DrinkHeroCard(
                imageUrl = drinkDetail.image,
                drinkName = drinkDetail.drinkName
            )
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth()
            ) {
                DrinkInfoCard(
                    icon = if (drinkDetail.alcoholic.lowercase() == "alcoholic") R.drawable.wine_bottle else R.drawable.no_drinking,
                    info = drinkDetail.alcoholic,
                    backgroundColor = Color.White
                )
                DrinkInfoCard(
                    icon = R.drawable.wine_glass,
                    info = drinkDetail.glass,
                    backgroundColor = Color.White
                )
            }
        }
        item {
            DrinkIngredientsSection(modifier, drinkDetail.ingredients)
        }
        item {
            DrinkInstructions(modifier = modifier, instructions = drinkDetail.instructions)
        }
    }
}

@Composable
fun DrinkHeroCard(imageUrl: String, drinkName: String) {
    Column {
        NetworkImage(
            url = imageUrl,
            modifier = Modifier
                .padding(16.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(4.dp, Color.White, CircleShape)
                .shadow(8.dp)
        )
        Text(
            text = drinkName,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 4.dp),
            style = MaterialTheme.typography.h6,
            fontWeight = Bold,
        )
    }
}

@Composable
fun DrinkInfoCard(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    info: String,
    backgroundColor: Color = Color.White
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = info,
                modifier = modifier.size(24.dp)
            )
            Text(
                text = info,
                style = MaterialTheme.typography.caption,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun DrinkIngredientsSection(
    modifier: Modifier = Modifier,
    ingredients: List<Ingredient>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.subtitle1,
            modifier = modifier
                .padding(top = 12.dp, bottom = 4.dp)
                .fillMaxWidth()
        )

        CustomVertical2DRow(items = ingredients) { default_modifier, idx ->
            IngredientCard(
                default_modifier,
                ingredientNumber = idx + 1,
                ingredient = ingredients[idx]
            )
        }
    }
}

@Composable
fun IngredientCard(
    modifier: Modifier = Modifier,
    ingredientNumber: Int,
    ingredient: Ingredient
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        Surface(
            elevation = 4.dp,
            shape = CircleShape,
            border = BorderStroke(4.dp, Color.White),
            color = Color.White
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
            ) {
                Text(
                    text = "$ingredientNumber",
                    style = MaterialTheme.typography.overline,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Column {
            Text(
                text = ingredient.name,
                style = MaterialTheme.typography.caption,
                color = Color.Black
            )
            Text(
                text = ingredient.amount,
                style = MaterialTheme.typography.overline,
                color = Color.Black
            )
        }
    }
}

@Composable
fun DrinkInstructions(
    modifier: Modifier = Modifier,
    instructions: String
) {
    Surface(
        elevation = 1.dp,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(4.dp, Color.White),
        color = Color.White,
        modifier = modifier
    ) {
        Text(
            text = "Instructions - $instructions",
            style = MaterialTheme.typography.caption,
            color = Color.Black,
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}