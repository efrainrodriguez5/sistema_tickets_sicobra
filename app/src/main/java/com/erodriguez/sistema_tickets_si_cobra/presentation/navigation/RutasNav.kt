package com.erodriguez.sistema_tickets_si_cobra.presentation.navigation

object RutasNav {
    const val LOGIN = "login"
    const val RECUPERAR_PASSWORD = "recuperar_password"
    const val MIS_TICKETS = "mis_tickets"
    const val DASHBOARD = "dashboard"
    const val AVISOS = "avisos"
    const val PERFIL = "perfil"
    const val NUEVO_TICKET_PASO_1 = "nuevo_ticket_paso_1"
    const val NUEVO_TICKET_PASO_2 = "nuevo_ticket_paso_2"
    const val DETALLE_TICKET = "detalle_ticket/{codigo}"
    const val COMENTARIOS_TICKET = "comentarios_ticket/{codigo}"
    const val POR_ASIGNAR = "por_asignar"

    fun detalleTicket(codigo: String): String = "detalle_ticket/$codigo"

    fun comentariosTicket(codigo: String): String = "comentarios_ticket/$codigo"

    fun getTitulo(ruta: String?): String {
        return when {
            ruta == LOGIN -> "Iniciar Sesión"
            ruta == RECUPERAR_PASSWORD -> "Recuperar contraseña"
            ruta == MIS_TICKETS -> "Mis tickets"
            ruta == DASHBOARD -> "Dashboard"
            ruta == AVISOS -> "Avisos"
            ruta == PERFIL -> "Mi perfil"
            ruta == NUEVO_TICKET_PASO_1 -> "Nuevo Ticket"
            ruta == NUEVO_TICKET_PASO_2 -> "Nuevo Ticket"
            ruta?.startsWith("detalle_ticket") == true -> "Detalle del ticket"
            ruta?.startsWith("comentarios_ticket") == true -> "Detalle del ticket"
            ruta == POR_ASIGNAR -> "Por asignar"
            else -> "Sicobra Soporte"
        }
    }
}
