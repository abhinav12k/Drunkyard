package com.abhinav12k.drunkyard.presentation.drinkList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.presentation.ui.theme.Red800
import com.abhinav12k.drunkyard.presentation.ui.theme.WhiteLight

@Composable
fun FavoriteDrinkSection(
    modifier: Modifier = Modifier,
    title: String = "Favorites",
    favoriteDrinks: List<DrinkCard>,
    onClick: (drinkId: String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .shadow(8.dp, MaterialTheme.shapes.medium, spotColor = if(isSystemInDarkTheme()) Color.White else Red800)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                color = WhiteLight,
                style = TextStyle(
                    shadow = Shadow(
                        color = if(isSystemInDarkTheme()) Color.White else Red800,
                        offset = Offset(5.0f, 8.0f),
                        blurRadius = 3f
                    )
                )
            )
            LazyRow {
                items(favoriteDrinks) { drink ->
                    DrinkCard(drinkCard = drink, onClick = onClick)
                }
            }
        }
    }
}