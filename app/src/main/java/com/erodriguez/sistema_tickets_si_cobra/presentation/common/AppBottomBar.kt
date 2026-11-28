package com.erodriguez.sistema_tickets_si_cobra.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.erodriguez.sistema_tickets_si_cobra.presentation.navigation.BottomNavItem
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.BordeSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoAlerta
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario

@Composable
fun AppBottomBar(
    navController: NavHostController,
    rolUsuario: String
) {
    val opciones = if (rolUsuario == "Jefe de TI") {
        listOf(
            BottomNavItem.Tickets,
            BottomNavItem.Dashboard,
            BottomNavItem.Avisos,
            BottomNavItem.Perfil
        )
    } else {
        listOf(
            BottomNavItem.Tickets,
            BottomNavItem.Avisos,
            BottomNavItem.Perfil
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column {
        HorizontalDivider(thickness = 1.dp, color = BordeSuave)
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp
        ) {
            opciones.forEach { item ->
                val seleccionado = currentRoute == item.ruta
                NavigationBarItem(
                    icon = {
                        if (item.cantidadAvisos > 0) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = RojoAlerta,
                                        contentColor = Color.White
                                    ) {
                                        Text(
                                            text = item.cantidadAvisos.toString(),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = item.icono,
                                    contentDescription = item.titulo,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icono,
                                contentDescription = item.titulo,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.titulo,
                            fontSize = 11.sp,
                            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    selected = seleccionado,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AzulPrincipal,
                        selectedTextColor = AzulPrincipal,
                        indicatorColor = AzulSuave,
                        unselectedIconColor = TextoSecundario,
                        unselectedTextColor = TextoSecundario
                    ),
                    onClick = {
                        navController.navigate(item.ruta) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
