package com.abhinav12k.drunkyard.common

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class NetworkUrlPreviewProvider : PreviewParameterProvider<String> {
    override val count: Int
        get() = super.count
    override val values: Sequence<String>
        get() = sequenceOf("https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg/preview")
}
