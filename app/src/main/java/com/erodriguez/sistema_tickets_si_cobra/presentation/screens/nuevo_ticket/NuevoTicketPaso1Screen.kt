package com.erodriguez.sistema_tickets_si_cobra.presentation.screens.nuevo_ticket

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.automirrored.outlined.VolumeOff
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.HeadsetMic
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.SyncProblem
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.BordeSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoPantalla
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoError
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoFondo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeExito

private data class GuiaSugeridaVisual(
    val titulo: String,
    val icono: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoTicketPaso1Screen(
    areaSeleccionada: String,
    categoriaSeleccionada: String,
    prioridadSeleccionada: String,
    asunto: String,
    onAreaChange: (String) -> Unit,
    onCategoriaChange: (String) -> Unit,
    onPrioridadChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onVolver: () -> Unit,
    onContinuar: () -> Unit
) {
    var menuAreaAbierto by remember { mutableStateOf(false) }
    var menuCategoriaAbierto by remember { mutableStateOf(false) }
    var mostrarDialogoDescartar by remember { mutableStateOf(false) }

    val areas = listOf(
        "Campaña y Telefonía",
        "CRM Cobranzas",
        "Hardware / Periféricos",
        "Redes y VPN",
        "Sistemas Core"
    )
    val categorias = listOf(
        "Marcador Predictivo",
        "Acceso / Contraseña",
        "Falla de Audio / Diadema",
        "Caída de VPN",
        "Lentitud en CRM"
    )
    val prioridades = listOf("Baja", "Media", "Alta")
    val guiasSugeridas = listOf(
        GuiaSugeridaVisual(
            titulo = "Reinicio y configuración del softphone...",
            icono = Icons.Outlined.HeadsetMic
        ),
        GuiaSugeridaVisual(
            titulo = "Guía de prueba y calibración de audio...",
            icono = Icons.AutoMirrored.Outlined.VolumeOff
        ),
        GuiaSugeridaVisual(
            titulo = "Desbloqueo de cola en Marcador Predictivo...",
            icono = Icons.Outlined.SyncProblem
        )
    )

    val puedeContinuar = areaSeleccionada.isNotBlank() &&
        categoriaSeleccionada.isNotBlank() &&
        prioridadSeleccionada.isNotBlank() &&
        asunto.isNotBlank()

    if (mostrarDialogoDescartar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoDescartar = false },
            title = { Text("¿Descartar borrador?", fontWeight = FontWeight.Bold) },
            text = { Text("Si sales ahora perderás los datos ingresados en el registro del ticket.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogoDescartar = false
                        onVolver()
                    }
                ) {
                    Text("Descartar", color = RojoError, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoDescartar = false }) {
                    Text("Seguir editando", color = AzulPrincipal)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                    IconButton(onClick = { mostrarDialogoDescartar = true }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = TextoPrincipal
                        )
                    }
                    Text(
                        text = "Nuevo ticket",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal,
                        modifier = Modifier.weight(1f)
                    )
                    Surface(
                        shape = CircleShape,
                        color = AzulSuave,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Perfil",
                                tint = AzulPrincipal,
                                modifier = Modifier.size(20.dp)
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
                .navigationBarsPadding()
                .padding(horizontal = 18.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = AzulPrincipal,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Paso 1 de 2: Clasificación inicial",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulPrincipal
                    )
                }
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFE7EFFC)
                ) {
                    Text(
                        text = "50% completado",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulPrincipal,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { 0.5f },
                color = AzulPrincipal,
                trackColor = Color(0xFFDCE5F5),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50))
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(verticalAlignment = Alignment.Top) {
                Surface(
                    shape = CircleShape,
                    color = AzulSuave,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Assignment,
                            contentDescription = null,
                            tint = AzulPrincipal,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Detalles del Incidente",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Ingresa el área y categoría correspondientes para direccionar tu solicitud sin demoras.",
                        fontSize = 12.sp,
                        color = TextoSecundario,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Área")
                        withStyle(SpanStyle(color = RojoError)) {
                            append(" *")
                        }
                    },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
                Text(
                    text = "Obligatorio",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AzulPrincipal
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            ExposedDropdownMenuBox(
                expanded = menuAreaAbierto,
                onExpandedChange = { menuAreaAbierto = !menuAreaAbierto },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = areaSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuAreaAbierto) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = AzulPrincipal,
                        unfocusedBorderColor = BordeSuave
                    ),
                    modifier = Modifier
                        .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = menuAreaAbierto,
                    onDismissRequest = { menuAreaAbierto = false }
                ) {
                    areas.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                onAreaChange(opcion)
                                menuAreaAbierto = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Define la cola técnica de atención inmediata",
                fontSize = 11.sp,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Categoría")
                        withStyle(SpanStyle(color = RojoError)) {
                            append(" *")
                        }
                    },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
                Text(
                    text = "Obligatorio",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AzulPrincipal
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            ExposedDropdownMenuBox(
                expanded = menuCategoriaAbierto,
                onExpandedChange = { menuCategoriaAbierto = !menuCategoriaAbierto },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = categoriaSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuCategoriaAbierto) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = AzulPrincipal,
                        unfocusedBorderColor = BordeSuave
                    ),
                    modifier = Modifier
                        .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = menuCategoriaAbierto,
                    onDismissRequest = { menuCategoriaAbierto = false }
                ) {
                    categorias.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                onCategoriaChange(opcion)
                                menuCategoriaAbierto = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Ajustado automáticamente al área seleccionada",
                fontSize = 11.sp,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Prioridad",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
                Surface(
                    shape = RoundedCornerShape(50),
                    color = RojoFondo
                ) {
                    Text(
                        text = "Impacto en operación",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = RojoError,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFE7EFFA),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    prioridades.forEach { nivel ->
                        val seleccionada = nivel == prioridadSeleccionada
                        val colorFondo = when {
                            seleccionada && nivel == "Alta" -> RojoFondo
                            seleccionada && nivel == "Media" -> Color(0xFFFFE6C2)
                            seleccionada -> Color.White
                            else -> Color.Transparent
                        }
                        val colorTexto = when {
                            seleccionada && nivel == "Alta" -> RojoError
                            seleccionada && nivel == "Media" -> Color(0xFF8A4F00)
                            seleccionada -> AzulPrincipal
                            else -> TextoSecundario
                        }
                        val iconoPrioridad = when (nivel) {
                            "Baja" -> Icons.AutoMirrored.Outlined.Sort
                            "Media" -> Icons.Default.Remove
                            else -> Icons.Default.PriorityHigh
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = colorFondo,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onPrioridadChange(nivel) }
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = iconoPrioridad,
                                    contentDescription = null,
                                    tint = colorTexto,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = nivel,
                                    fontSize = 13.sp,
                                    fontWeight = if (seleccionada) FontWeight.Bold else FontWeight.Medium,
                                    color = colorTexto
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Asunto",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
                Text(
                    text = "Obligatorio",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AzulPrincipal
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = asunto,
                onValueChange = { if (it.length <= 80) onAsuntoChange(it) },
                singleLine = true,
                placeholder = { Text("Describe brevemente la incidencia", color = TextoSecundario) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = AzulPrincipal,
                    unfocusedBorderColor = BordeSuave
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = TextoSecundario,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Máximo 80 caracteres",
                        fontSize = 11.sp,
                        color = TextoSecundario
                    )
                }
                Text(
                    text = "${asunto.length}/80",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulPrincipal
                )
            }

            if (asunto.length >= 3) {
                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFEDF3FC),
                    border = BorderStroke(1.dp, Color(0xFFD5E2F7)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                                    contentDescription = null,
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Base de Conocimiento ·\nSoluciones sugeridas",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoPrincipal,
                                    lineHeight = 18.sp
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White,
                                border = BorderStroke(1.dp, BordeSuave)
                            ) {
                                Text(
                                    text = "3 artículos",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextoSecundario,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Revisa estas alternativas frecuentes antes de enviar tu ticket para resolver tu incidencia al instante:",
                            fontSize = 12.sp,
                            color = TextoSecundario,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            guiasSugeridas.forEach { guia ->
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color.White,
                                    border = BorderStroke(1.dp, BordeSuave),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 12.dp, vertical = 11.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = guia.icono,
                                            contentDescription = null,
                                            tint = AzulPrincipal,
                                            modifier = Modifier.size(17.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = guia.titulo,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = TextoPrincipal,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                                            contentDescription = "Abrir guía",
                                            tint = TextoSecundario,
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Verified,
                                contentDescription = null,
                                tint = VerdeExito,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "94% de agentes resolvieron con estas guías",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VerdeExito
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onContinuar,
                enabled = puedeContinuar,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulPrincipal,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Continuar al Paso 2",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Security,
                    contentDescription = null,
                    tint = TextoSecundario,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Tus datos se sincronizan con la mesa de servicio Sicobra en tiempo real",
                    fontSize = 11.sp,
                    color = TextoSecundario
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
