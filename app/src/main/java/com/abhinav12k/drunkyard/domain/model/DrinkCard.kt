package com.abhinav12k.drunkyard.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "drink_cards")
data class DrinkCard(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "drink_name") val drinkName: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "image") val image: String
) : Parcelable {
    companion object {
        fun mock() = DrinkCard(
            "1",
            "Cocktail",
            "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg/preview",
            "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg"
        )
    }
}