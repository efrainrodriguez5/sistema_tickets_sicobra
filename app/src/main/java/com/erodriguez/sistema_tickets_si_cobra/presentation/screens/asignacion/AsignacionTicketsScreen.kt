package com.erodriguez.sistema_tickets_si_cobra.presentation.screens.asignacion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erodriguez.sistema_tickets_si_cobra.R
import com.erodriguez.sistema_tickets_si_cobra.presentation.common.EtiquetaChip
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.BordeSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoPantalla
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoError
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoFondo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeExito
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeFondo
import kotlinx.coroutines.launch

private data class TicketColaVisual(
    val codigo: String,
    val prioridad: String,
    val asunto: String,
    val area: String,
    val espera: String,
    val tecnicoActual: String?
)

private data class TecnicoCargaVisual(
    val nombre: String,
    val especialidad: String,
    val iniciales: String,
    val ticketsActivos: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignacionTicketsScreen(
    onVolver: () -> Unit,
    onVerDetalle: (String) -> Unit
) {
    val colaTickets = remember {
        mutableStateListOf(
            TicketColaVisual(
                codigo = "TKT-1019",
                prioridad = "Media",
                asunto = "Error de acceso al CRM Sicobra Cobranzas",
                area = "CRM Cobranzas · Piso 3",
                espera = "Abierto hace 2h",
                tecnicoActual = null
            ),
            TicketColaVisual(
                codigo = "TKT-2051",
                prioridad = "Alta",
                asunto = "Falla de enlace primario E1 en Campaña Bancaria 3",
                area = "Campaña y Telefonía · Piso 2",
                espera = "Abierto hace 4 min",
                tecnicoActual = null
            ),
            TicketColaVisual(
                codigo = "TKT-1008",
                prioridad = "Alta",
                asunto = "Bloqueo de usuario y conexión VPN Cisco",
                area = "Redes y VPN · Remoto",
                espera = "SLA vencido hace 15m",
                tecnicoActual = "Carlos Mendoza"
            ),
            TicketColaVisual(
                codigo = "TKT-1024",
                prioridad = "Alta",
                asunto = "Falla de marcación predictiva en campaña Bancaria 2",
                area = "Telefonía IP / Vicidial",
                espera = "Restan 45 min",
                tecnicoActual = "Diego Morales"
            )
        )
    }

    val tecnicos = remember {
        mutableStateListOf(
            TecnicoCargaVisual("Diego Morales", "Telefonía IP & Asterisk", "DM", 3),
            TecnicoCargaVisual("Laura Soto", "CRM Cobranzas & Base de Datos", "LS", 5),
            TecnicoCargaVisual("Marco Quispe", "Redes, VPN & Enlaces", "MQ", 2),
            TecnicoCargaVisual("Ana Torres", "Hardware & Estaciones BPO", "AT", 4)
        )
    }

    var ticketSeleccionado by remember { mutableStateOf<TicketColaVisual?>(null) }
    var tecnicoPendienteConfirmacion by remember { mutableStateOf<TecnicoCargaVisual?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun aplicarAsignacion(ticket: TicketColaVisual, tecnico: TecnicoCargaVisual) {
        val indice = colaTickets.indexOfFirst { it.codigo == ticket.codigo }
        if (indice != -1) {
            colaTickets[indice] = ticket.copy(tecnicoActual = tecnico.nombre)
        }
        val idxTec = tecnicos.indexOfFirst { it.nombre == tecnico.nombre }
        if (idxTec != -1) {
            val actual = tecnicos[idxTec]
            tecnicos[idxTec] = actual.copy(ticketsActivos = actual.ticketsActivos + 1)
        }
        ticketSeleccionado = null
        tecnicoPendienteConfirmacion = null
        scope.launch {
            snackbarHostState.showSnackbar("Ticket ${ticket.codigo} asignado a ${tecnico.nombre}")
        }
    }

    if (tecnicoPendienteConfirmacion != null && ticketSeleccionado != null) {
        val ticket = ticketSeleccionado!!
        val tecnico = tecnicoPendienteConfirmacion!!
        AlertDialog(
            onDismissRequest = { tecnicoPendienteConfirmacion = null },
            title = { Text("Confirmar reasignación", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "El ticket ${ticket.codigo} ya está asignado a ${ticket.tecnicoActual}. ¿Deseas reasignarlo a ${tecnico.nombre}?"
                )
            },
            confirmButton = {
                TextButton(onClick = { aplicarAsignacion(ticket, tecnico) }) {
                    Text("Reasignar", color = AzulPrincipal, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { tecnicoPendienteConfirmacion = null }) {
                    Text("Cancelar", color = TextoSecundario)
                }
            }
        )
    }

    if (ticketSeleccionado != null) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val ticket = ticketSeleccionado!!
        ModalBottomSheet(
            onDismissRequest = { ticketSeleccionado = null },
            sheetState = sheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 18.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Seleccionar Técnico de Soporte",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Text(
                            text = "${ticket.codigo} · ${ticket.asunto}",
                            fontSize = 12.sp,
                            color = TextoSecundario
                        )
                    }
                    IconButton(onClick = { ticketSeleccionado = null }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = TextoSecundario
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    tecnicos.forEach { tecnico ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = FondoPantalla),
                            border = BorderStroke(1.dp, BordeSuave),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (ticket.tecnicoActual != null) {
                                        tecnicoPendienteConfirmacion = tecnico
                                    } else {
                                        aplicarAsignacion(ticket, tecnico)
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .background(AzulPrincipal, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = tecnico.iniciales,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = tecnico.nombre,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextoPrincipal
                                    )
                                    Text(
                                        text = tecnico.especialidad,
                                        fontSize = 11.sp,
                                        color = TextoSecundario
                                    )
                                }
                                EtiquetaChip(
                                    texto = "${tecnico.ticketsActivos} activos",
                                    colorFondo = if (tecnico.ticketsActivos <= 2) VerdeFondo else AzulSuave,
                                    colorTexto = if (tecnico.ticketsActivos <= 2) VerdeExito else AzulPrincipal,
                                    esRedondeado = true
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    Scaffold(
        containerColor = FondoPantalla,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .background(FondoPantalla)
        ) {
            Surface(color = Color.White, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.statusBarsPadding()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onVolver) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = TextoPrincipal
                            )
                        }
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, BordeSuave),
                            modifier = Modifier.size(30.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_si_cobra),
                                contentDescription = "Logo Sicobra",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Por asignar",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal,
                            modifier = Modifier.weight(1f)
                        )
                        val sinAsignarCount = colaTickets.count { it.tecnicoActual == null }
                        EtiquetaChip(
                            texto = "$sinAsignarCount sin técnico",
                            colorFondo = if (sinAsignarCount > 0) RojoFondo else VerdeFondo,
                            colorTexto = if (sinAsignarCount > 0) RojoError else VerdeExito,
                            esRedondeado = true
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    HorizontalDivider(thickness = 1.dp, color = BordeSuave)
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(colaTickets) { ticket ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, BordeSuave),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onVerDetalle(ticket.codigo) }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    EtiquetaChip(
                                        texto = ticket.codigo,
                                        colorFondo = AzulSuave,
                                        colorTexto = AzulPrincipal
                                    )
                                    EtiquetaChip(
                                        texto = ticket.prioridad,
                                        colorFondo = if (ticket.prioridad == "Alta") RojoFondo else AzulSuave,
                                        colorTexto = if (ticket.prioridad == "Alta") RojoError else AzulPrincipal,
                                        icono = Icons.Outlined.Flag
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.Schedule,
                                        contentDescription = null,
                                        tint = TextoSecundario,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = ticket.espera,
                                        fontSize = 11.sp,
                                        color = TextoSecundario
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = ticket.asunto,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = ticket.area,
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = if (ticket.tecnicoActual == null) TextoSecundario else AzulPrincipal,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = ticket.tecnicoActual ?: "Sin técnico asignado",
                                        fontSize = 12.sp,
                                        fontWeight = if (ticket.tecnicoActual != null) FontWeight.Bold else FontWeight.Normal,
                                        color = if (ticket.tecnicoActual != null) TextoPrincipal else TextoSecundario
                                    )
                                }

                                Button(
                                    onClick = { ticketSeleccionado = ticket },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (ticket.tecnicoActual == null) AzulPrincipal else AzulSuave,
                                        contentColor = if (ticket.tecnicoActual == null) Color.White else AzulPrincipal
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.PersonAdd,
                                        contentDescription = null,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (ticket.tecnicoActual == null) "Asignar" else "Reasignar",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
