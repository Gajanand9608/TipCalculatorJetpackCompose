package com.example.myapplication.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapplication.MyApp

val IconButtonSizeModifier = Modifier.size(40.dp)

@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    elevation: Dp = 4.dp,
) {
    Card(modifier = Modifier
        .padding(all = 3.dp)
        .clickable { onClick.invoke() },
//        .then(IconButtonSizeModifier),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)) {
        Box(modifier = Modifier, contentAlignment = Alignment.BottomCenter) {
            Icon(imageVector = imageVector, contentDescription = "Plus or Minus icon", tint = tint)
        }

    }
}

@Preview
@Composable
private fun RoundIconPreview() {
    MyApp {
        RoundIconButton(
            imageVector = Icons.Outlined.Add,
            onClick = {}
        )
    }
}