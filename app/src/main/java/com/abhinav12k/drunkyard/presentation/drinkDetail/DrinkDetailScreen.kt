package com.abhinav12k.drunkyard.presentation.drinkDetail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.R
import com.abhinav12k.drunkyard.common.*
import com.abhinav12k.drunkyard.domain.model.DrinkDetail
import com.abhinav12k.drunkyard.domain.model.Ingredient
import com.abhinav12k.drunkyard.presentation.ui.theme.TopAppBarDarkBackground

@Composable
fun DrinkDetailScreen(
    viewModel: DrinkDetailViewModel,
    drinkId: String,
    onBackPressed: () -> Unit
) {
    LaunchedEffect(key1 = drinkId) {
        viewModel.getDrinkDetailsById(drinkId)
    }
    val viewState = rememberSaveable(inputs = arrayOf(drinkId), key = drinkId) {
        viewModel.drinkDetailViewState
    }

    val context = LocalContext.current
    val view = LocalView.current

    val onShareClicked = {
        view.takeScreenshot(context) {
            val uri = FileUtil.storeScreenShot(it, context, viewState.value.drinkDetail?.drinkName ?: "Drink")
            context.openIntentChooser(
                "Hey there check this drink. It's mind blowing!",
                contentUri = uri
            )
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                onBackPressed = onBackPressed,
                onFavoriteClicked = { },
                onShareClicked = { onShareClicked() }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (viewState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            viewState.value.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            viewState.value.drinkDetail?.let {
                DrinkDetailScreenContent(
                    it
                )
            }
        }
    }
}

@Composable
private fun AppBar(
    onBackPressed: () -> Unit,
    onShareClicked: () -> Unit,
    onFavoriteClicked: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onBackPressed() }
                    )
            )
        },
        title = {},
        backgroundColor = if(isSystemInDarkTheme()) TopAppBarDarkBackground else MaterialTheme.colors.background,
        actions = {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onFavoriteClicked() }
                    )
            )
            Icon(
                imageVector = Icons.Rounded.Share,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onShareClicked() }
                    )
            )
        }
    )
}

@Composable
fun DrinkDetailScreenContent(
    drinkDetail: DrinkDetail,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        item {
            NetworkImage(
                url = drinkDetail.image,
                modifier = Modifier
                    .padding(16.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .shadow(8.dp)
            )
        }
        item {
            Text(
                text = drinkDetail.drinkName,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.h6,
                fontWeight = Bold,
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
                    info = drinkDetail.alcoholic
                )
                DrinkInfoCard(
                    icon = R.drawable.wine_glass,
                    info = drinkDetail.glass
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
fun DrinkInfoCard(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    info: String
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = info,
                modifier = modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
            )
            Text(
                text = info,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(start = 4.dp)
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
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
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
        Card(
            elevation = 4.dp,
            shape = CircleShape
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
            ) {
                Text(
                    text = "$ingredientNumber",
                    style = MaterialTheme.typography.overline,
                    color = MaterialTheme.colors.onSurface,
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
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = ingredient.amount,
                style = MaterialTheme.typography.overline,
                color = MaterialTheme.colors.onSurface.copy(alpha = .5f)
            )
        }
    }
}

@Composable
fun DrinkInstructions(
    modifier: Modifier = Modifier,
    instructions: String
) {
    Card(
        elevation = 1.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "Instructions - $instructions",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.9f),
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}