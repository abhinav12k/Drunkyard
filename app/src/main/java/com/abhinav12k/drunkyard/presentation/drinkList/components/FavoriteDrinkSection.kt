package com.abhinav12k.drunkyard.presentation.drinkList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.common.CustomVerticalRowWithColumns
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.presentation.ui.theme.BlackLight
import com.abhinav12k.drunkyard.presentation.ui.theme.Red800
import com.abhinav12k.drunkyard.presentation.ui.theme.WhiteLight
import java.lang.Integer.min

@Composable
fun FavoriteDrinkSection(
    modifier: Modifier = Modifier,
    title: String = "Favorites",
    favoriteDrinks: List<DrinkCard>,
    onClick: (drinkId: String) -> Unit,
    onViewAllClicked: () -> Unit
) {
    val reversedList = remember {
        favoriteDrinks.reversed().subList(0, min(6, favoriteDrinks.size))
    }
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .shadow(
                8.dp,
                MaterialTheme.shapes.medium,
                spotColor = if (isSystemInDarkTheme()) Color.White else Red800
            )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    color = if (isSystemInDarkTheme()) WhiteLight else BlackLight,
                    style = TextStyle(
                        shadow = Shadow(
                            color = if (isSystemInDarkTheme()) Color.White else Red800,
//                            offset = Offset(5.0f, 8.0f),
//                            blurRadius = 3f
                        )
                    )
                )
                Text(
                    text = "View All",
                    color = if (isSystemInDarkTheme()) WhiteLight else BlackLight,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .clickable { onViewAllClicked.invoke() }
                )
            }
            CustomVerticalRowWithColumns(
                modifier = Modifier.padding(8.dp),
                items = reversedList,
                numOfColumns = 3,
                horizontalArrangement = Arrangement.SpaceAround,
                resetArrangementIfOneItemInRow = true
            ) { default_modifier, idx ->
                DrinkCard(
                    drinkCard = reversedList[idx],
                    modifier = default_modifier.fillMaxSize().weight(1f),
                    onClick = onClick
                )
            }
        }
    }
}