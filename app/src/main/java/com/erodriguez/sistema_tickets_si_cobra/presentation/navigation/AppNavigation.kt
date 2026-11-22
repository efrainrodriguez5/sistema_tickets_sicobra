package com.erodriguez.sistema_tickets_si_cobra.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erodriguez.sistema_tickets_si_cobra.presentation.screens.avisos.AvisosScreen
import com.erodriguez.sistema_tickets_si_cobra.presentation.screens.login.LoginScreen
import com.erodriguez.sistema_tickets_si_cobra.presentation.screens.nuevo_ticket.NuevoTicketPaso1Screen
import com.erodriguez.sistema_tickets_si_cobra.presentation.screens.nuevo_ticket.NuevoTicketPaso2Screen
import com.erodriguez.sistema_tickets_si_cobra.presentation.screens.perfil.PerfilScreen
import com.erodriguez.sistema_tickets_si_cobra.presentation.screens.recuperar.RecuperarPasswordScreen
import com.erodriguez.sistema_tickets_si_cobra.presentation.screens.tickets.MisTicketsScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    var areaTicket by remember { mutableStateOf("Campaña y Telefonía") }
    var categoriaTicket by remember { mutableStateOf("Marcador Predictivo") }
    var prioridadTicket by remember { mutableStateOf("Alta") }
    var asuntoTicket by remember { mutableStateOf("Falla de audio en llamadas de cobranza") }

    NavHost(
        navController = navController,
        startDestination = RutasNav.LOGIN,
        modifier = Modifier
    ) {
        composable(RutasNav.LOGIN) {
            LoginScreen(
                onIngresar = {
                    navController.navigate(RutasNav.MIS_TICKETS) {
                        popUpTo(RutasNav.LOGIN) { inclusive = true }
                    }
                },
                onOlvidePassword = {
                    navController.navigate(RutasNav.RECUPERAR_PASSWORD)
                }
            )
        }

        composable(RutasNav.RECUPERAR_PASSWORD) {
            RecuperarPasswordScreen(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable(RutasNav.MIS_TICKETS) {
            MisTicketsScreen(
                onAbrirAvisos = {
                    navController.navigate(RutasNav.AVISOS) {
                        launchSingleTop = true
                    }
                },
                onAbrirPerfil = {
                    navController.navigate(RutasNav.PERFIL) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(RutasNav.AVISOS) {
            AvisosScreen()
        }

        composable(RutasNav.PERFIL) {
            PerfilScreen(
                onCerrarSesion = {
                    navController.navigate(RutasNav.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(RutasNav.NUEVO_TICKET_PASO_1) {
            NuevoTicketPaso1Screen(
                areaSeleccionada = areaTicket,
                categoriaSeleccionada = categoriaTicket,
                prioridadSeleccionada = prioridadTicket,
                asunto = asuntoTicket,
                onAreaChange = { areaTicket = it },
                onCategoriaChange = { categoriaTicket = it },
                onPrioridadChange = { prioridadTicket = it },
                onAsuntoChange = { asuntoTicket = it },
                onVolver = {
                    navController.popBackStack()
                },
                onContinuar = {
                    navController.navigate(RutasNav.NUEVO_TICKET_PASO_2)
                }
            )
        }

        composable(RutasNav.NUEVO_TICKET_PASO_2) {
            NuevoTicketPaso2Screen(
                areaSeleccionada = areaTicket,
                categoriaSeleccionada = categoriaTicket,
                prioridadSeleccionada = prioridadTicket,
                onVolver = {
                    navController.popBackStack()
                },
                onEditarClasificacion = {
                    navController.popBackStack()
                },
                onEnviarTicket = {
                    navController.popBackStack(RutasNav.MIS_TICKETS, false)
                }
            )
        }
    }
}
