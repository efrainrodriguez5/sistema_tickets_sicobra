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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.HeadsetMic
import androidx.compose.material.icons.outlined.HowToReg
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material.icons.outlined.Timer
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erodriguez.sistema_tickets_si_cobra.R
import com.erodriguez.sistema_tickets_si_cobra.presentation.common.EtiquetaChip
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AmbarAdvertencia
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AmbarFondo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulOscuro
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.BordeSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoCampo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoPantalla
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoError
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoFondo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeExito
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeFondo
import kotlinx.coroutines.launch

private data class TicketPorAsignarVisual(
    val codigo: String,
    val prioridad: String,
    val asunto: String,
    val ubicacion: String,
    val tiempo: String,
    val enRiesgo: Boolean,
    val icono: ImageVector,
    val tecnicoActual: String? = null
)

private data class TecnicoTurnoVisual(
    val nombre: String,
    val iniciales: String,
    val especialidad: String,
    val disponibilidad: String,
    val tipoDisponibilidad: String,
    val ticketsActivos: Int,
    val nivelCarga: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignacionTicketsScreen(
    onVolver: () -> Unit,
    onVerDetalle: (String) -> Unit
) {
    val colaTickets = remember {
        mutableStateListOf(
            TicketPorAsignarVisual(
                codigo = "TKT-1028",
                prioridad = "Crítica",
                asunto = "Caída de troncal SIP principal en campaña Banorte",
                ubicacion = "Telefonía IP • Edificio B • Piso 3",
                tiempo = "12 min",
                enRiesgo = true,
                icono = Icons.Outlined.HeadsetMic
            ),
            TicketPorAsignarVisual(
                codigo = "TKT-1029",
                prioridad = "Alta",
                asunto = "Falla de inicio de sesión en CRM Cobranza Móvil",
                ubicacion = "Sistemas Core • 18 agentes afectados",
                tiempo = "25 min",
                enRiesgo = false,
                icono = Icons.Outlined.Storage
            ),
            TicketPorAsignarVisual(
                codigo = "TKT-1030",
                prioridad = "Media",
                asunto = "Reconfiguración de puerto de red en Estación 24",
                ubicacion = "Hardware • Terminal POS-04",
                tiempo = "40 min",
                enRiesgo = false,
                icono = Icons.Outlined.Devices
            ),
            TicketPorAsignarVisual(
                codigo = "TKT-1031",
                prioridad = "Baja",
                asunto = "Actualización de certificado SSL en servidor de pruebas",
                ubicacion = "Infraestructura • Ambiente QA",
                tiempo = "55 min",
                enRiesgo = false,
                icono = Icons.Outlined.Dns
            )
        )
    }

    val tecnicos = remember {
        mutableStateListOf(
            TecnicoTurnoVisual(
                nombre = "Diego Morales",
                iniciales = "DM",
                especialidad = "Especialidad: Redes y Telefonía IP",
                disponibilidad = "Disp. ahora",
                tipoDisponibilidad = "VERDE",
                ticketsActivos = 2,
                nivelCarga = "Carga baja"
            ),
            TecnicoTurnoVisual(
                nombre = "Miguel Á. Torres",
                iniciales = "MT",
                especialidad = "Especialidad: Telefonía y Asterisk",
                disponibilidad = "En 15 min",
                tipoDisponibilidad = "AMBAR",
                ticketsActivos = 3,
                nivelCarga = "Carga media"
            ),
            TecnicoTurnoVisual(
                nombre = "Carlos Mendoza",
                iniciales = "CM",
                especialidad = "Especialidad: Hardware y Redes",
                disponibilidad = "En turno",
                tipoDisponibilidad = "GRIS",
                ticketsActivos = 4,
                nivelCarga = "Carga media"
            ),
            TecnicoTurnoVisual(
                nombre = "Laura Soto",
                iniciales = "LS",
                especialidad = "Especialidad: CRM y Bases de Datos",
                disponibilidad = "Disp. ahora",
                tipoDisponibilidad = "VERDE",
                ticketsActivos = 2,
                nivelCarga = "Carga baja"
            )
        )
    }

    var ticketSeleccionado by remember { mutableStateOf<TicketPorAsignarVisual?>(null) }
    var busquedaTecnico by remember { mutableStateOf("") }
    var tecnicoSeleccionadoNombre by remember { mutableStateOf("Diego Morales") }
    var mostrarConfirmacionReasignacion by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun aplicarAsignacion(ticket: TicketPorAsignarVisual, nombreTecnico: String) {
        val idxTicket = colaTickets.indexOfFirst { it.codigo == ticket.codigo }
        if (idxTicket != -1) {
            colaTickets[idxTicket] = ticket.copy(tecnicoActual = nombreTecnico)
        }
        val idxTecnico = tecnicos.indexOfFirst { it.nombre == nombreTecnico }
        if (idxTecnico != -1) {
            val actual = tecnicos[idxTecnico]
            val nuevaCantidad = actual.ticketsActivos + 1
            tecnicos[idxTecnico] = actual.copy(
                ticketsActivos = nuevaCantidad,
                nivelCarga = if (nuevaCantidad <= 2) "Carga baja" else "Carga media"
            )
        }
        ticketSeleccionado = null
        mostrarConfirmacionReasignacion = false
        scope.launch {
            snackbarHostState.showSnackbar("Ticket ${ticket.codigo} asignado a $nombreTecnico")
        }
    }

    if (mostrarConfirmacionReasignacion && ticketSeleccionado != null) {
        val ticket = ticketSeleccionado!!
        AlertDialog(
            onDismissRequest = { mostrarConfirmacionReasignacion = false },
            title = { Text("Confirmar reasignación", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "El ticket ${ticket.codigo} ya está asignado a ${ticket.tecnicoActual}. ¿Deseas reasignarlo a $tecnicoSeleccionadoNombre?"
                )
            },
            confirmButton = {
                TextButton(onClick = { aplicarAsignacion(ticket, tecnicoSeleccionadoNombre) }) {
                    Text("Confirmar", color = AzulPrincipal, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacionReasignacion = false }) {
                    Text("Cancelar", color = TextoSecundario)
                }
            }
        )
    }

    if (ticketSeleccionado != null) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val ticket = ticketSeleccionado!!
        val tecnicosFiltrados = tecnicos.filter {
            busquedaTecnico.isBlank() ||
                it.nombre.contains(busquedaTecnico, ignoreCase = true) ||
                it.especialidad.contains(busquedaTecnico, ignoreCase = true)
        }

        ModalBottomSheet(
            onDismissRequest = { ticketSeleccionado = null },
            sheetState = sheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
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
                            text = "Asignar técnico",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Text(
                            text = "Escalamiento operativo prioritario",
                            fontSize = 12.sp,
                            color = TextoSecundario
                        )
                    }
                    IconButton(
                        onClick = { ticketSeleccionado = null },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = TextoSecundario
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = FondoPantalla,
                    border = BorderStroke(1.dp, BordeSuave),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = ticket.codigo,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AzulPrincipal
                                )
                                EtiquetaChip(
                                    texto = ticket.prioridad,
                                    colorFondo = if (ticket.prioridad == "Crítica" || ticket.prioridad == "Alta") RojoFondo else AzulSuave,
                                    colorTexto = if (ticket.prioridad == "Crítica" || ticket.prioridad == "Alta") RojoError else AzulPrincipal
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Timer,
                                    contentDescription = null,
                                    tint = RojoError,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "SLA: ${ticket.tiempo}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RojoError
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = ticket.asunto,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = ticket.icono,
                                contentDescription = null,
                                tint = TextoSecundario,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = ticket.ubicacion,
                                fontSize = 11.sp,
                                color = TextoSecundario
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = busquedaTecnico,
                    onValueChange = { busquedaTecnico = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Buscar técnico por nombre o habilidad...",
                            fontSize = 13.sp,
                            color = TextoSecundario
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = TextoSecundario
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = FondoCampo,
                        unfocusedContainerColor = FondoCampo,
                        focusedBorderColor = AzulPrincipal,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TÉCNICOS DE TURNO (${tecnicosFiltrados.size})",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoSecundario
                    )
                    Text(
                        text = "Especialistas IP",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulPrincipal
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    tecnicosFiltrados.forEach { tecnico ->
                        val seleccionado = tecnico.nombre == tecnicoSeleccionadoNombre
                        val fondoBadgeDisp = when (tecnico.tipoDisponibilidad) {
                            "VERDE" -> VerdeFondo
                            "AMBAR" -> AmbarFondo
                            else -> Color(0xFFEDF1F8)
                        }
                        val textoBadgeDisp = when (tecnico.tipoDisponibilidad) {
                            "VERDE" -> VerdeExito
                            "AMBAR" -> AmbarAdvertencia
                            else -> TextoSecundario
                        }
                        val colorPunto = when (tecnico.tipoDisponibilidad) {
                            "VERDE" -> VerdeExito
                            "AMBAR" -> AmbarAdvertencia
                            else -> VerdeExito
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (seleccionado) AzulSuave else Color.White,
                            border = BorderStroke(
                                if (seleccionado) 1.5.dp else 1.dp,
                                if (seleccionado) Color(0xFFB8CEFA) else BordeSuave
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { tecnicoSeleccionadoNombre = tecnico.nombre }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.size(44.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .size(42.dp)
                                            .background(
                                                if (seleccionado) AzulPrincipal else Color(0xFF3E526B),
                                                RoundedCornerShape(12.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = tecnico.iniciales,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .size(12.dp)
                                            .background(Color.White, CircleShape)
                                            .padding(2.dp)
                                            .background(colorPunto, CircleShape)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = tecnico.nombre,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextoPrincipal
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        EtiquetaChip(
                                            texto = tecnico.disponibilidad,
                                            colorFondo = fondoBadgeDisp,
                                            colorTexto = textoBadgeDisp,
                                            icono = if (tecnico.tipoDisponibilidad == "VERDE") Icons.Default.Bolt else null
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = tecnico.especialidad,
                                        fontSize = 11.sp,
                                        color = TextoSecundario
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${tecnico.ticketsActivos} tickets activos",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (tecnico.ticketsActivos <= 2) Color(0xFF0E7C7B) else AzulPrincipal
                                        )
                                        Text(
                                            text = " • ${tecnico.nivelCarga}",
                                            fontSize = 11.sp,
                                            color = TextoSecundario
                                        )
                                    }
                                }

                                RadioButton(
                                    selected = seleccionado,
                                    onClick = { tecnicoSeleccionadoNombre = tecnico.nombre },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = AzulPrincipal,
                                        unselectedColor = Color(0xFFD5DCE8)
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (ticket.tecnicoActual != null) {
                            mostrarConfirmacionReasignacion = true
                        } else {
                            aplicarAsignacion(ticket, tecnicoSeleccionadoNombre)
                        }
                    },
                    enabled = tecnicosFiltrados.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulPrincipal,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.HowToReg,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Confirmar Asignación",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                TextButton(
                    onClick = { ticketSeleccionado = null },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    Text(
                        text = "Cancelar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )
                }
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
                            text = "Por Asignar",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = CircleShape,
                            color = AzulOscuro,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Perfil",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    HorizontalDivider(thickness = 1.dp, color = BordeSuave)
                }
            }

            val pendientesCount = colaTickets.count { it.tecnicoActual == null }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Tickets por asignar",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoPrincipal
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                EtiquetaChip(
                                    texto = "$pendientesCount pendientes",
                                    colorFondo = if (pendientesCount > 0) RojoFondo else VerdeFondo,
                                    colorTexto = if (pendientesCount > 0) RojoError else VerdeExito,
                                    esRedondeado = true
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(VerdeExito, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "En vivo",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VerdeExito
                                )
                            }
                        }

                        Text(
                            text = "Tickets abiertos sin técnico asignado en mesa de ayuda",
                            fontSize = 12.sp,
                            color = TextoSecundario
                        )

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = RojoSuave,
                            border = BorderStroke(1.dp, Color(0xFFF6C6C3)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 9.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.Timer,
                                        contentDescription = null,
                                        tint = RojoError,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "SLA máximo de asignación: 15 min",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = RojoError
                                    )
                                }
                                Text(
                                    text = "1 en riesgo",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RojoError
                                )
                            }
                        }
                    }
                }

                items(colaTickets) { ticket ->
                    val colorPrioridadFondo = when (ticket.prioridad) {
                        "Crítica", "Alta" -> RojoFondo
                        "Media" -> AmbarFondo
                        else -> Color(0xFFEDF1F8)
                    }
                    val colorPrioridadTexto = when (ticket.prioridad) {
                        "Crítica", "Alta" -> RojoError
                        "Media" -> AmbarAdvertencia
                        else -> TextoSecundario
                    }

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
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = ticket.codigo,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextoSecundario
                                    )
                                    EtiquetaChip(
                                        texto = ticket.prioridad,
                                        colorFondo = colorPrioridadFondo,
                                        colorTexto = colorPrioridadTexto
                                    )
                                    if (ticket.tecnicoActual != null) {
                                        EtiquetaChip(
                                            texto = ticket.tecnicoActual,
                                            colorFondo = VerdeFondo,
                                            colorTexto = VerdeExito
                                        )
                                    }
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.Schedule,
                                        contentDescription = null,
                                        tint = if (ticket.enRiesgo) RojoError else TextoSecundario,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = ticket.tiempo,
                                        fontSize = 11.sp,
                                        fontWeight = if (ticket.enRiesgo) FontWeight.Bold else FontWeight.Medium,
                                        color = if (ticket.enRiesgo) RojoError else TextoSecundario
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

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
                                    imageVector = ticket.icono,
                                    contentDescription = null,
                                    tint = TextoSecundario,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = ticket.ubicacion,
                                    fontSize = 12.sp,
                                    color = TextoSecundario
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    busquedaTecnico = ""
                                    tecnicoSeleccionadoNombre = ticket.tecnicoActual ?: "Diego Morales"
                                    ticketSeleccionado = ticket
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (ticket.tecnicoActual == null) AzulPrincipal else AzulSuave,
                                    contentColor = if (ticket.tecnicoActual == null) Color.White else AzulPrincipal
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(42.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.PersonAdd,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (ticket.tecnicoActual == null) "Asignar" else "Reasignar",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                item {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFEDF2FA),
                        border = BorderStroke(1.dp, Color(0xFFD5E2F7)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val siguiente = colaTickets.firstOrNull { it.tecnicoActual == null } ?: colaTickets.first()
                                busquedaTecnico = ""
                                tecnicoSeleccionadoNombre = "Diego Morales"
                                ticketSeleccionado = siguiente
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = AzulSuave,
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Outlined.Groups,
                                        contentDescription = null,
                                        tint = AzulPrincipal,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "5 técnicos activos",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoPrincipal
                                )
                                Text(
                                    text = "3 disponibles para despacho inmediato",
                                    fontSize = 11.sp,
                                    color = TextoSecundario
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = TextoSecundario,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
