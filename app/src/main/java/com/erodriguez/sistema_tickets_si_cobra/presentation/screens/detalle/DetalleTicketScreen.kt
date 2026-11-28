package com.erodriguez.sistema_tickets_si_cobra.presentation.screens.detalle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.automirrored.outlined.Redo
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.HeadsetMic
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoPantalla
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoError
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoFondo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeExito
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeFondo
import kotlinx.coroutines.launch

private data class EventoHistorialVisual(
    val titulo: String,
    val autor: String,
    val hora: String
)

@Composable
fun DetalleTicketScreen(
    codigo: String,
    rolUsuario: String,
    onVolver: () -> Unit,
    onAbrirComentarios: (String) -> Unit,
    onAbrirPorAsignar: () -> Unit
) {
    var estadoActual by remember { mutableStateOf("En proceso") }
    var mostrarHojaEstado by remember { mutableStateOf(false) }
    var tabSeleccionada by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val asuntoTicket = "Falla de marcación predictiva en campaña Bancaria 2"
    val pasos = listOf("Abierto", "Asignado", "En proceso", "Cerrado")
    val pasoActual = when (estadoActual) {
        "Abierto" -> 0
        "Asignado" -> 1
        "En proceso", "Suspendido" -> 2
        else -> 3
    }

    val historialEventos = listOf(
        EventoHistorialVisual(
            titulo = "Estado actualizado a $estadoActual",
            autor = "Diego Morales",
            hora = "10:05 AM"
        ),
        EventoHistorialVisual(
            titulo = "Nota interna TI registrada (Ticket #CL-89231)",
            autor = "Diego Morales",
            hora = "09:42 AM"
        ),
        EventoHistorialVisual(
            titulo = "Ticket asignado a Diego Morales",
            autor = "Efraín Rodríguez",
            hora = "09:28 AM"
        ),
        EventoHistorialVisual(
            titulo = "Ticket creado por incidente en Vicidial",
            autor = "Carlos Mendoza",
            hora = "09:24 AM"
        )
    )

    if (mostrarHojaEstado) {
        HojaEstadoSolucion(
            codigo = codigo,
            asunto = asuntoTicket,
            estadoInicial = estadoActual,
            onDismiss = { mostrarHojaEstado = false },
            onGuardar = { nuevoEstado, _, _ ->
                val estadoPrevio = estadoActual
                estadoActual = nuevoEstado
                mostrarHojaEstado = false
                scope.launch {
                    val resultado = snackbarHostState.showSnackbar(
                        message = "Estado actualizado a $nuevoEstado",
                        actionLabel = "Deshacer",
                        duration = SnackbarDuration.Long
                    )
                    if (resultado == SnackbarResult.ActionPerformed) {
                        estadoActual = estadoPrevio
                    }
                }
            }
        )
    }

    Scaffold(
        containerColor = FondoPantalla,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Surface(color = Color.White, shadowElevation = 6.dp) {
                Column(modifier = Modifier.navigationBarsPadding()) {
                    HorizontalDivider(thickness = 1.dp, color = BordeSuave)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = AzulSuave,
                            border = BorderStroke(1.dp, Color(0xFFC8D9FA)),
                            modifier = Modifier
                                .size(48.dp)
                                .clickable { onAbrirComentarios(codigo) }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.Chat,
                                    contentDescription = "Comentarios",
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(21.dp)
                                )
                            }
                        }

                        Button(
                            onClick = { mostrarHojaEstado = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AzulPrincipal,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Cambiar estado / Solucionar",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
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
                            text = "Detalle del ticket",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = CircleShape,
                            color = AzulSuave,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Perfil",
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    HorizontalDivider(thickness = 1.dp, color = BordeSuave)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        EtiquetaChip(
                            texto = "INCIDENCIA",
                            colorFondo = AzulSuave,
                            colorTexto = AzulPrincipal
                        )
                        EtiquetaChip(
                            texto = "SLA: 42 min restantes",
                            colorFondo = RojoFondo,
                            colorTexto = RojoError,
                            esRedondeado = true
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Enlace de $codigo copiado")
                                }
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Share,
                                contentDescription = "Compartir",
                                tint = TextoSecundario,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                if (rolUsuario == "Jefe de TI") {
                                    onAbrirPorAsignar()
                                } else {
                                    mostrarHojaEstado = true
                                }
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Redo,
                                contentDescription = "Reasignar",
                                tint = TextoSecundario,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, BordeSuave),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            pasos.forEachIndexed { indice, etiqueta ->
                                val completado = indice < pasoActual
                                val activo = indice == pasoActual
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        if (indice > 0) {
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(2.dp)
                                                    .background(
                                                        if (completado || activo) AzulPrincipal else BordeSuave
                                                    )
                                            )
                                        } else {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .background(
                                                    color = when {
                                                        completado -> VerdeExito
                                                        activo -> AzulPrincipal
                                                        else -> Color(0xFFE7ECF5)
                                                    },
                                                    shape = CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            when {
                                                completado -> Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.size(15.dp)
                                                )
                                                activo -> Icon(
                                                    imageVector = Icons.Default.Sync,
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.size(15.dp)
                                                )
                                                else -> Box(
                                                    modifier = Modifier
                                                        .size(8.dp)
                                                        .background(TextoSecundario, CircleShape)
                                                )
                                            }
                                        }

                                        if (indice < pasos.lastIndex) {
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(2.dp)
                                                    .background(
                                                        if (completado) AzulPrincipal else BordeSuave
                                                    )
                                            )
                                        } else {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = if (indice == 2 && estadoActual == "Suspendido") "Suspendido" else etiqueta,
                                        fontSize = 11.sp,
                                        fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium,
                                        color = if (activo) AzulPrincipal else TextoSecundario
                                    )
                                }
                            }
                        }
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, BordeSuave),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = asuntoTicket,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            EtiquetaChip(
                                texto = "Telefonía / Asterisk",
                                colorFondo = AzulSuave,
                                colorTexto = AzulPrincipal,
                                icono = Icons.Outlined.HeadsetMic
                            )
                            EtiquetaChip(
                                texto = "Prioridad Alta",
                                colorFondo = RojoFondo,
                                colorTexto = RojoError,
                                icono = Icons.Outlined.Flag
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = FondoPantalla,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .background(AzulSuave, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "CM",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = AzulPrincipal
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Creado por",
                                            fontSize = 10.sp,
                                            color = TextoSecundario
                                        )
                                        Text(
                                            text = "Carlos Mendoza",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextoPrincipal
                                        )
                                        Text(
                                            text = "Operador Cobranzas",
                                            fontSize = 11.sp,
                                            color = TextoSecundario
                                        )
                                    }
                                    Text(
                                        text = "Hoy 09:24 AM",
                                        fontSize = 11.sp,
                                        color = TextoSecundario
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                HorizontalDivider(color = BordeSuave)
                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .background(AzulOscuro, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "DM",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Asignado actualmente",
                                            fontSize = 10.sp,
                                            color = TextoSecundario
                                        )
                                        Text(
                                            text = "Diego Morales",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextoPrincipal
                                        )
                                        Text(
                                            text = "Especialista TI Nivel 2",
                                            fontSize = 11.sp,
                                            color = TextoSecundario
                                        )
                                    }
                                    EtiquetaChip(
                                        texto = if (estadoActual == "Suspendido") "Suspendido" else "En atención",
                                        colorFondo = if (estadoActual == "Suspendido") AmbarFondo else VerdeFondo,
                                        colorTexto = if (estadoActual == "Suspendido") AmbarAdvertencia else VerdeExito,
                                        esRedondeado = true
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Description,
                                contentDescription = null,
                                tint = AzulPrincipal,
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Descripción detallada",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Desde las 09:10 AM, 18 posiciones de la campaña Bancaria 2 presentan cortes continuos al intentar enlazar llamadas salientes. Las consolas Vicidial arrojan evento de desconexión masiva y los canales SIP muestran congestión inmediata. Se verificó conectividad física y gateway de planta.",
                            fontSize = 13.sp,
                            color = TextoPrincipal,
                            lineHeight = 19.sp
                        )
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, BordeSuave),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.AttachFile,
                                    contentDescription = null,
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Archivos Adjuntos (1)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoPrincipal
                                )
                            }
                            Text(
                                text = "PNG - 428 KB",
                                fontSize = 11.sp,
                                color = TextoSecundario
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = FondoPantalla,
                            border = BorderStroke(1.dp, BordeSuave),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = AzulSuave,
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Outlined.Image,
                                            contentDescription = null,
                                            tint = AzulPrincipal,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "error_sip_503.png",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextoPrincipal
                                    )
                                    Text(
                                        text = "Captura de consola PBX",
                                        fontSize = 11.sp,
                                        color = TextoSecundario
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White,
                                    border = BorderStroke(1.dp, BordeSuave),
                                    modifier = Modifier.clickable {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Vista previa de error_sip_503.png")
                                        }
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Visibility,
                                            contentDescription = "Ver",
                                            tint = AzulPrincipal,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Ver",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = AzulPrincipal
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, BordeSuave),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { tabSeleccionada = 0 }
                            ) {
                                Text(
                                    text = "Comentarios (3)",
                                    fontSize = 13.sp,
                                    fontWeight = if (tabSeleccionada == 0) FontWeight.Bold else FontWeight.Medium,
                                    color = if (tabSeleccionada == 0) AzulPrincipal else TextoSecundario,
                                    modifier = Modifier.padding(vertical = 6.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(2.dp)
                                        .background(if (tabSeleccionada == 0) AzulPrincipal else BordeSuave)
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { tabSeleccionada = 1 }
                            ) {
                                Text(
                                    text = "Historial de Cambios",
                                    fontSize = 13.sp,
                                    fontWeight = if (tabSeleccionada == 1) FontWeight.Bold else FontWeight.Medium,
                                    color = if (tabSeleccionada == 1) AzulPrincipal else TextoSecundario,
                                    modifier = Modifier.padding(vertical = 6.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(2.dp)
                                        .background(if (tabSeleccionada == 1) AzulPrincipal else BordeSuave)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (tabSeleccionada == 0) {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = FondoPantalla,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Carlos Mendoza",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = TextoPrincipal
                                            )
                                            Text(
                                                text = "09:28 AM",
                                                fontSize = 11.sp,
                                                color = TextoSecundario
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Revisé con supervisión y el troncal número 4 de Claro es el único que devuelve rechazo continuo.",
                                            fontSize = 12.sp,
                                            color = TextoPrincipal,
                                            lineHeight = 17.sp
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFFEAF2FC),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Diego Morales",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = AzulPrincipal
                                            )
                                            Text(
                                                text = "09:42 AM",
                                                fontSize = 11.sp,
                                                color = TextoSecundario
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Se detectó re-enrutamiento saturado por balanceo dinámico. Procedo a levantar túnel secundario y purgar registros colgados en Kamailio.",
                                            fontSize = 12.sp,
                                            color = TextoPrincipal,
                                            lineHeight = 17.sp
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFFEAF7F3),
                                    border = BorderStroke(1.dp, Color(0xFFBFE4D8)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.Lock,
                                                    contentDescription = null,
                                                    tint = VerdeExito,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "Nota Interna TI",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = VerdeExito
                                                )
                                            }
                                            Text(
                                                text = "10:05 AM",
                                                fontSize = 11.sp,
                                                color = TextoSecundario
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Ticket escalado a proveedor Claro (Ticket Proveedor #CL-89231). Monitoreando latencia de SIP proxy.",
                                            fontSize = 12.sp,
                                            color = TextoPrincipal,
                                            lineHeight = 17.sp
                                        )
                                    }
                                }

                                Text(
                                    text = "Ver conversación completa",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AzulPrincipal,
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .clickable { onAbrirComentarios(codigo) }
                                        .padding(top = 4.dp)
                                )
                            }
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                historialEventos.forEachIndexed { indice, evento ->
                                    Row(verticalAlignment = Alignment.Top) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Box(
                                                modifier = Modifier
                                                    .size(10.dp)
                                                    .background(
                                                        if (indice == 0) AzulPrincipal else Color(0xFF9FB2CC),
                                                        CircleShape
                                                    )
                                            )
                                            if (indice < historialEventos.lastIndex) {
                                                Box(
                                                    modifier = Modifier
                                                        .width(2.dp)
                                                        .height(34.dp)
                                                        .background(BordeSuave)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = evento.titulo,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = TextoPrincipal
                                            )
                                            Text(
                                                text = "${evento.autor} · ${evento.hora}",
                                                fontSize = 11.sp,
                                                color = TextoSecundario
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
    }
}
