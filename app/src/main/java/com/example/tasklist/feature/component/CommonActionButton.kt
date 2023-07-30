package com.example.tasklist.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasklist.basecomponent.ui.theme.Primary

@Composable
fun CommonActionButton(
    onClick: () -> Unit,
    isEnabled: Boolean,
    isLoading: Boolean,
    buttonText: String
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(56.dp)
            .background(
                color = if (isEnabled) Color.Gray
                else Color.Gray.copy(alpha = 0.12f),
                shape = RoundedCornerShape(2.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            contentColor = if (isEnabled) contentColorFor(Color.White)
            else contentColorFor(Color.Gray.copy(alpha = 0.32f))
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp),
                color = contentColorFor(Color.White)
            )
        }
        Text(
            text = buttonText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
