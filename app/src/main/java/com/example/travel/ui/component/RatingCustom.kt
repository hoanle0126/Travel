package com.example.travel.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingCustom(
    modifier: Modifier = Modifier,
    color:Color = MaterialTheme.colorScheme.primary,
    size: Float = 1f,
    value:Int = 3
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.times(size).dp)
    ) {
        repeat(value){
            Box(
                modifier = Modifier
                    .size(12.times(size).dp)
                    .background(
                        color,
                        RoundedCornerShape(99.dp)
                    )
            )
        }
        repeat(5 - value){
            Box(
                modifier = Modifier
                    .size(12.times(size).dp)
                    .background(
                        Color.Transparent,
                        RoundedCornerShape(99.dp)
                    )
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(99.dp),
                        color = color
                    )
            )
        }
    }
}