package com.marsn.minitalk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.marsn.minitalk.navigation.ToDoNavHost
import com.marsn.minitalk.ui.theme.MiniTalkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent() {
            Box(modifier = Modifier.safeDrawingPadding()) {
                MiniTalkTheme {
                    ToDoNavHost()
                }
            }
        }
    }
}
