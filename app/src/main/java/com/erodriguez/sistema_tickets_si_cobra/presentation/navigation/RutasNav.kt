package com.erodriguez.sistema_tickets_si_cobra.presentation.navigation

object RutasNav {
    const val LOGIN = "login"
    const val RECUPERAR_PASSWORD = "recuperar_password"
    const val MIS_TICKETS = "mis_tickets"
    const val AVISOS = "avisos"
    const val PERFIL = "perfil"
    const val NUEVO_TICKET_PASO_1 = "nuevo_ticket_paso_1"
    const val NUEVO_TICKET_PASO_2 = "nuevo_ticket_paso_2"

    fun getTitulo(ruta: String?): String {
        return when {
            ruta == LOGIN -> "Iniciar Sesión"
            ruta == RECUPERAR_PASSWORD -> "Recuperar contraseña"
            ruta == MIS_TICKETS -> "Mis tickets"
            ruta == AVISOS -> "Avisos"
            ruta == PERFIL -> "Mi perfil"
            ruta == NUEVO_TICKET_PASO_1 -> "Nuevo Ticket"
            ruta == NUEVO_TICKET_PASO_2 -> "Nuevo Ticket"
            else -> "Sicobra Soporte"
        }
    }
}
