package com.erodriguez.sistema_tickets_si_cobra.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.erodriguez.sistema_tickets_si_cobra.presentation.navigation.RutasNav
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoPantalla

@Composable
fun AppScaffold(
    navController: NavHostController,
    rolUsuario: String,
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val ruta = navBackStackEntry?.destination?.route
    val rutasConBarraInferior = listOf(
        RutasNav.MIS_TICKETS,
        RutasNav.DASHBOARD,
        RutasNav.AVISOS,
        RutasNav.PERFIL
    )
    val mostrarBarraInferior = ruta in rutasConBarraInferior
    val mostrarBotonNuevo = ruta == RutasNav.MIS_TICKETS

    Scaffold(
        containerColor = FondoPantalla,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (mostrarBarraInferior) {
                AppBottomBar(
                    navController = navController,
                    rolUsuario = rolUsuario
                )
            }
        },
        floatingActionButton = {
            if (mostrarBotonNuevo) {
                ExtendedFloatingActionButton(
                    onClick = { navController.navigate(RutasNav.NUEVO_TICKET_PASO_1) },
                    containerColor = AzulPrincipal,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Nuevo ticket"
                        )
                    },
                    text = {
                        Text(
                            text = "Nuevo ticket",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                )
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}
