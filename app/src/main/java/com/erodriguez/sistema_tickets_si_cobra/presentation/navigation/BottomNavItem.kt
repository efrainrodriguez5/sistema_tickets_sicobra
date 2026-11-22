package com.erodriguez.sistema_tickets_si_cobra.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector,
    val cantidadAvisos: Int = 0
) {
    object Tickets : BottomNavItem(RutasNav.MIS_TICKETS, "Tickets", Icons.Outlined.ConfirmationNumber)
    object Avisos : BottomNavItem(RutasNav.AVISOS, "Avisos", Icons.Outlined.Campaign, 3)
    object Perfil : BottomNavItem(RutasNav.PERFIL, "Perfil", Icons.Outlined.AccountCircle)
}
