package com.abhinav12k.drunkyard.presentation.drinkList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.common.CustomVertical2DRow
import com.abhinav12k.drunkyard.common.CustomVerticalRowWithColumns
import com.abhinav12k.drunkyard.common.NetworkImage
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.presentation.ui.theme.DrunkyardTheme

@Composable
fun DrinkCard(
    drinkCard: DrinkCard,
    modifier: Modifier = Modifier,
    onClick: (id: String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .width(100.dp)
            .requiredHeight(140.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onClick(drinkCard.id) }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .shadow(8.dp),
            url = drinkCard.thumbnail
        )
        Text(
            modifier = Modifier
                .paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            text = drinkCard.drinkName,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = .6f),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun DrinkCardsGrid(
    drinkCards: List<DrinkCard>,
    modifier: Modifier = Modifier,
    onClick: (id: String) -> Unit
) {
    CustomVerticalRowWithColumns(
        modifier = modifier,
        items = drinkCards,
        numOfColumns = 2,
        horizontalArrangement = Arrangement.SpaceAround,
        resetArrangementIfOneItemInRow = false
    ) { default_modifier, idx ->
        DrinkCard(
            modifier = default_modifier,
            drinkCard = drinkCards[idx],
            onClick = onClick
        )
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