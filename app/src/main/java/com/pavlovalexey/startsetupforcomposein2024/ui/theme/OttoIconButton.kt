package com.pavlovalexey.startsetupforcomposein2024.ui.theme

import androidx.compose.ui.Modifier
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity

@Composable
fun OttoIconButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    painter: Painter? = null,
    buttonColor: Color = Color.Green,
    isFillMaxWidth: Boolean = true,
    outlined: Boolean = false,
    modifier: Modifier = Modifier,
) {

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = if (isFillMaxWidth)
            modifier
                .heightIn(min = dp48)
                .fillMaxWidth()
        else {
            modifier
                .heightIn(min = dp48)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        shape = RoundedCornerShape(dp6),
        border = if (outlined) BorderStroke(dp1, Color.Gray) else null,
        contentPadding = if (isFillMaxWidth) PaddingValues(dp0) else ButtonDefaults.ContentPadding
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (buttonColor == Color.White) Color.Gray else Color.White
                )
            } else if (painter != null) {
                Icon(
                    painter = painter,
                    contentDescription = null,
                    tint = if (buttonColor == Color.White) Color.Gray else Color.White
                )
            }

            Text(
                text = text,
                color = if (buttonColor == Color.White || buttonColor == Color.Gray) Color.Gray else Color.White,
                modifier = if (icon != null || painter != null) Modifier.padding(start = dp8) else Modifier.padding(start = dp0)
            )
        }
    }
}
