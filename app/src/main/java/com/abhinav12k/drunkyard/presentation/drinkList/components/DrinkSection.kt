package com.abhinav12k.drunkyard.presentation.drinkList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.presentation.drinkList.model.DrinkSection

@Composable
fun DrinkSection(
    modifier: Modifier = Modifier,
    drinkSection: DrinkSection,
    onDrinkCardClicked: (id: String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .shadow(2.dp, shape = RoundedCornerShape(8.dp))
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(vertical = 4.dp, horizontal = 6.dp)
    ) {
        Text(
            text = drinkSection.category.categoryName,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
            style = MaterialTheme.typography.subtitle2
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
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