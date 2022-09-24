package com.abhinav12k.drunkyard.presentation.drinkList.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.presentation.drinkList.model.DrinkSection

@Composable
fun DrinkSection(
    modifier: Modifier = Modifier,
    drinkSection: DrinkSection,
    onDrinkCardClicked: (id: String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = drinkSection.category.categoryName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.subtitle2
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(drinkSection.list) { card ->
                    DrinkCard(
                        drinkCard = card,
                        onClick = onDrinkCardClicked
                    )
                }
            }
        }
    }
}