package com.stevdza_san.testing.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.stevdza_san.testing.util.TestTag.ACTIVE_ERROR_TEXT
import com.stevdza_san.testing.util.TestTag.COMPLETED_ERROR_TEXT

@Composable
fun ErrorScreen(
    message: String? = null,
    activeSection: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .testTag(
                    tag = if (activeSection) ACTIVE_ERROR_TEXT
                    else COMPLETED_ERROR_TEXT
                ),
            text = message ?: "Empty"
        )
    }
}