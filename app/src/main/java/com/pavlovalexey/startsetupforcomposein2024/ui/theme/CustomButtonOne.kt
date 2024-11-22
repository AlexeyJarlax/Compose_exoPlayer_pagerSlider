package com.pavlovalexey.startsetupforcomposein2024.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButtonOne(
    onClick: () -> Unit,
    text: String,
    iconResId: ImageVector,
    modifier: Modifier = Modifier,
    textColor: Color = Color.DarkGray,
    iconColor: Color = Color.DarkGray,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = textColor
        ),
        modifier = modifier
            .padding(end = 6.dp, bottom = 6.dp)
            .background(Color.Transparent)
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(1.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            letterSpacing = 0.0.sp,
            fontFamily = FontFamily.Default,
            color = textColor,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = iconResId,
            contentDescription = null,
            tint = iconColor
        )
    }
}
/**
CustomButtonOne(
onClick = { viewModel.seePrivacyPolicy() },
text = stringResource(R.string.pp),
iconResId = R.drawable.description_30dp,
modifier = Modifier.fillMaxWidth()
)
 */