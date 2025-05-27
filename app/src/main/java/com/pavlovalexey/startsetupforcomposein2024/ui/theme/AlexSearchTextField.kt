package com.pavlovalexey.startsetupforcomposein2024.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.zIndex
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color

@Composable
fun AlexSearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    textStyle: TextStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
    cursorBrush: SolidColor = SolidColor(MaterialTheme.colorScheme.primary),
) {
    Box(
        modifier = modifier
            .padding(horizontal = dp16, vertical = dp8)
            .fillMaxWidth()
            .clip(RoundedCornerShape(dp8))
            .background(Color.LightGray)
            .padding(dp0)
            .height(dp40)
            .zIndex(1f)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            cursorBrush = cursorBrush,
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(
                onDone = { onValueChange(value) }
            ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск",
                        tint = if (value.isEmpty()) Color.White else Color.Gray,
                        modifier = Modifier.padding(start = dp16)
                    )
                    Spacer(modifier = Modifier.width(dp20))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholderText
                            )
                        }
                        innerTextField()
                    }
                    if (value.isNotEmpty()) {
                        AlexClearButton(
                            onClear = { onValueChange("") },
                            verticalOffset = dp0
                        )
                    }
                }
            }
        )
    }
}