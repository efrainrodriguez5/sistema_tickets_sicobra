package com.erodriguez.sistema_tickets_si_cobra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.erodriguez.sistema_tickets_si_cobra.presentation.common.AppScaffold
import com.erodriguez.sistema_tickets_si_cobra.presentation.navigation.AppNavigation
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.Sistema_tickets_si_cobraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema_tickets_si_cobraTheme {
                val navController = rememberNavController()
                AppScaffold(navController) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        AppNavigation(navController)
                    }
                }
            }
        }
    }
}
