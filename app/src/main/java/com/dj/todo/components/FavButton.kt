package com.dj.todo.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.dj.todo.R
import com.dj.todo.ui.theme.FAVORITE_INDICATOR_SIZE
import com.dj.todo.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun FavButton(
    modifier: Modifier = Modifier,
    color: Color = Color(0xffE91E63),
    favorite: Boolean,
    onFavoriteSelected: (Boolean) -> Unit
) {
    var isFavorite by remember { mutableStateOf(false) }

    IconToggleButton(
        modifier = Modifier
            .width(PRIORITY_INDICATOR_SIZE)
            .height(FAVORITE_INDICATOR_SIZE),
        checked = isFavorite,

        onCheckedChange = {
            isFavorite = !isFavorite
            onFavoriteSelected(isFavorite)
        }
    ) {
        Icon(
            tint = color,
            modifier = modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            painter = if (favorite) {
                painterResource(id = R.drawable.ic_favorite)
            } else {
                painterResource(id = R.drawable.ic_favorite_empty)
            },
            contentDescription = null
        )
    }
}

