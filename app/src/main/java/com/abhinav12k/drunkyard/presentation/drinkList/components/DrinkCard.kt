package com.abhinav12k.drunkyard.presentation.drinkList.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.common.NetworkImage
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.presentation.ui.theme.DrunkyardTheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrinkCard(
    drinkCard: DrinkCard,
    modifier: Modifier = Modifier,
    onClick: (id: String) -> Unit = {}
) {
    Surface(
        modifier = modifier.padding(4.dp),
        onClick = {
            onClick(drinkCard.id)
        },
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NetworkImage(
                modifier = Modifier
                    .aspectRatio(0.8f),
                url = drinkCard.thumbnail
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = drinkCard.drinkName,
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(name = "Drink Card Light Theme")
@Composable
fun DrinkCardPreviewLightTheme() {
    DrunkyardTheme {
        DrinkCard(
            DrinkCard.mock()
        )
    }
}

@Preview(name = "Drink Card Dark Theme")
@Composable
fun DrinkCardsPreviewDarkTheme() {
    DrunkyardTheme(darkTheme = true) {
        DrinkCard(
            DrinkCard.mock()
        )
    }
}