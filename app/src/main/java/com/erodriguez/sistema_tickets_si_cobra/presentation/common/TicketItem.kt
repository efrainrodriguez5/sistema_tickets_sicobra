package com.erodriguez.sistema_tickets_si_cobra.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.HeadsetMic
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.TimerOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.BordeSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoCampo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoAlerta
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoError
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoFondo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeExito
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeFondo

data class TicketVisual(
    val codigo: String,
    val prioridad: String,
    val estado: String,
    val asunto: String,
    val categoria: String,
    val responsable: String,
    val iniciales: String,
    val tiempoSla: String,
    val iconoCategoria: ImageVector,
    val sinAsignar: Boolean = false,
    val esVencido: Boolean = false,
    val calificacion: Int? = null
)

@Composable
fun TicketItem(ticket: TicketVisual) {
    val colorFondoPrioridad = when (ticket.prioridad) {
        "Alta" -> RojoFondo
        "Media" -> Color(0xFFD6E9FB)
        else -> Color(0xFFE7ECF5)
    }
    val colorTextoPrioridad = when (ticket.prioridad) {
        "Alta" -> RojoError
        "Media" -> AzulPrincipal
        else -> TextoSecundario
    }

    val colorFondoEstado = when (ticket.estado) {
        "En proceso" -> AzulPrincipal
        "Abierto" -> Color(0xFFE3EEFD)
        "Cerrado" -> Color(0xFF9DEFD4)
        else -> RojoAlerta
    }
    val colorTextoEstado = when (ticket.estado) {
        "En proceso" -> Color.White
        "Abierto" -> AzulPrincipal
        "Cerrado" -> VerdeExito
        else -> Color.White
    }
    val iconoEstado = when (ticket.estado) {
        "En proceso" -> Icons.Default.Sync
        "Abierto" -> Icons.Outlined.Inbox
        "Cerrado" -> Icons.Outlined.CheckCircle
        else -> Icons.Outlined.ErrorOutline
    }

    val colorFondoPie = if (ticket.esVencido) RojoSuave else Color(0xFFF4F6FB)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BordeSuave),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EtiquetaChip(
                        texto = ticket.codigo,
                        colorFondo = Color(0xFFEDF2FA),
                        colorTexto = AzulPrincipal
                    )
                    EtiquetaChip(
                        texto = ticket.prioridad,
                        colorFondo = colorFondoPrioridad,
                        colorTexto = colorTextoPrioridad,
                        icono = Icons.Outlined.Flag
                    )
                }

                EtiquetaChip(
                    texto = ticket.estado,
                    colorFondo = colorFondoEstado,
                    colorTexto = colorTextoEstado,
                    icono = iconoEstado,
                    esRedondeado = true
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = ticket.asunto,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = ticket.iconoCategoria,
                    contentDescription = null,
                    tint = TextoSecundario,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = ticket.categoria,
                    fontSize = 13.sp,
                    color = TextoSecundario
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = colorFondoPie,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (ticket.calificacion != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            repeat(ticket.calificacion) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = VerdeExito,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${ticket.calificacion} estrellas",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextoSecundario
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(AzulSuave, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ticket.iniciales,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AzulPrincipal
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = ticket.responsable,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextoPrincipal
                            )
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (ticket.sinAsignar) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(Color(0xFFD5DAE3), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                                        contentDescription = null,
                                        tint = TextoSecundario,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = ticket.responsable,
                                    fontSize = 13.sp,
                                    fontStyle = FontStyle.Italic,
                                    color = TextoSecundario
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(
                                            if (ticket.esVencido) Color(0xFF283548) else AzulPrincipal,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = ticket.iniciales,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = ticket.responsable,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextoPrincipal
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val iconoTiempo = when {
                                ticket.esVencido -> Icons.Outlined.TimerOff
                                ticket.sinAsignar -> Icons.Outlined.Schedule
                                else -> Icons.Outlined.Timer
                            }
                            val colorTiempo = when {
                                ticket.esVencido -> RojoError
                                ticket.sinAsignar -> TextoSecundario
                                else -> VerdeExito
                            }
                            Icon(
                                imageVector = iconoTiempo,
                                contentDescription = null,
                                tint = colorTiempo,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = ticket.tiempoSla,
                                fontSize = 12.sp,
                                fontWeight = if (ticket.sinAsignar) FontWeight.Medium else FontWeight.Bold,
                                color = colorTiempo
                            )
                        }
                    }
                }
            }
        }
    }
}

fun obtenerTicketsMuestra(): List<TicketVisual> {
    return listOf(
        TicketVisual(
            codigo = "TKT-1024",
            prioridad = "Alta",
            estado = "En proceso",
            asunto = "Falla de marcación predictiva en campaña Bancaria 2",
            categoria = "Telefonía IP / Vicidial",
            responsable = "Diego Morales",
            iniciales = "DM",
            tiempoSla = "Restan 45 min",
            iconoCategoria = Icons.Outlined.HeadsetMic
        ),
        TicketVisual(
            codigo = "TKT-1019",
            prioridad = "Media",
            estado = "Abierto",
            asunto = "Error de acceso al CRM Sicobra Cobranzas",
            categoria = "CRM Cobranzas",
            responsable = "Sin asignar",
            iniciales = "?",
            tiempoSla = "Hace 2h",
            iconoCategoria = Icons.Outlined.Storage,
            sinAsignar = true
        ),
        TicketVisual(
            codigo = "TKT-1012",
            prioridad = "Baja",
            estado = "Cerrado",
            asunto = "Auricular con estática en estación 42 - Cobranza Mora Tardía",
            categoria = "Hardware / Periféricos",
            responsable = "L. Soto",
            iniciales = "LS",
            tiempoSla = "",
            iconoCategoria = Icons.Outlined.Devices,
            calificacion = 5
        ),
        TicketVisual(
            codigo = "TKT-1008",
            prioridad = "Alta",
            estado = "Vencido (SLA superado)",
            asunto = "Bloqueo de usuario y conexión VPN Cisco",
            categoria = "Redes y VPN",
            responsable = "Carlos Mendoza",
            iniciales = "CM",
            tiempoSla = "Vencido hace 15m",
            iconoCategoria = Icons.Outlined.Public,
            esVencido = true
        )
    )
}
