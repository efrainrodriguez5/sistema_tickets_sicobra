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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Dialpad
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.HeadsetMic
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.PhotoSizeSelectSmall
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erodriguez.sistema_tickets_si_cobra.presentation.common.EtiquetaChip
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulOscuro
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.BordeSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoCampo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoPantalla
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoError
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoFondo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeExito
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeFondo

@Composable
fun NuevoTicketPaso2Screen(
    areaSeleccionada: String,
    categoriaSeleccionada: String,
    prioridadSeleccionada: String,
    onVolver: () -> Unit,
    onEditarClasificacion: () -> Unit,
    onEnviarTicket: () -> Unit
) {
    var descripcion by remember {
        mutableStateOf(
            "El marcador se desconecta inesperadamente al ingresar la llamada. Aparece el código DISPO_ERR_403 en la consola del agente de Vicidial."
        )
    }
    var tieneAdjunto by remember { mutableStateOf(true) }
    var afectaMultiplesPosiciones by remember { mutableStateOf(true) }

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
                    IconButton(onClick = onVolver) {
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
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = AzulPrincipal,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Paso 2 de 2",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                    }
                    Text(
                        text = "Evidencia y Detalles",
                        fontSize = 12.sp,
                        color = TextoSecundario
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { 1f },
                    color = AzulPrincipal,
                    trackColor = Color(0xFFDCE5F5),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(50))
                )
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
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = TextoSecundario,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CLASIFICACIÓN SELECCIONADA",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoSecundario
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { onEditarClasificacion() }
                        ) {
                            Text(
                                text = "Editar",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AzulPrincipal
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Editar",
                                tint = AzulPrincipal,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    EtiquetaChip(
                        texto = areaSeleccionada,
                        colorFondo = AzulSuave,
                        colorTexto = AzulPrincipal,
                        icono = Icons.Outlined.HeadsetMic
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        EtiquetaChip(
                            texto = categoriaSeleccionada,
                            colorFondo = AzulSuave,
                            colorTexto = AzulPrincipal,
                            icono = Icons.Outlined.Dialpad
                        )
                        EtiquetaChip(
                            texto = "Prioridad $prioridadSeleccionada",
                            colorFondo = if (prioridadSeleccionada == "Alta") RojoFondo else AzulSuave,
                            colorTexto = if (prioridadSeleccionada == "Alta") RojoError else AzulPrincipal,
                            icono = Icons.Outlined.Flag
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                        }
                        Text(
                            text = "* Requerido",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = RojoError
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { if (it.length <= 500) descripcion = it },
                        minLines = 4,
                        placeholder = { Text("Describe lo sucedido con el mayor detalle posible", color = TextoSecundario) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = FondoCampo,
                            unfocusedContainerColor = FondoCampo,
                            focusedBorderColor = AzulPrincipal,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

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
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null,
                                tint = TextoSecundario,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Sé lo más específico posible con números de estación",
                                fontSize = 11.sp,
                                color = TextoSecundario
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${descripcion.length}/500",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulPrincipal
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.AttachFile,
                            contentDescription = null,
                            tint = AzulPrincipal,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Adjuntar evidencia fotográfica o capturas",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Sube fotos del monitor o errores en consola para acelerar el diagnóstico de soporte TI.",
                        fontSize = 12.sp,
                        color = TextoSecundario,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = FondoPantalla,
                            border = BorderStroke(1.dp, BordeSuave),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { tieneAdjunto = true }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = AzulSuave,
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Outlined.PhotoCamera,
                                            contentDescription = "Tomar foto",
                                            tint = AzulPrincipal,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Tomar foto",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoPrincipal
                                )
                                Text(
                                    text = "Cámara directa",
                                    fontSize = 11.sp,
                                    color = TextoSecundario
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = FondoPantalla,
                            border = BorderStroke(1.dp, BordeSuave),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { tieneAdjunto = true }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = AzulSuave,
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Outlined.PhotoLibrary,
                                            contentDescription = "Galería",
                                            tint = AzulPrincipal,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Galería",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoPrincipal
                                )
                                Text(
                                    text = "Subir archivo",
                                    fontSize = 11.sp,
                                    color = TextoSecundario
                                )
                            }
                        }
                    }

                    if (tieneAdjunto) {
                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "ARCHIVOS ADJUNTOS (1)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoSecundario
                        )

                        Spacer(modifier = Modifier.height(8.dp))

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
                                    modifier = Modifier.size(46.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Outlined.Image,
                                            contentDescription = null,
                                            tint = AzulPrincipal,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "captura_error_dialer.jpg",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextoPrincipal
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "1.2 MB",
                                            fontSize = 11.sp,
                                            color = TextoSecundario
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = VerdeFondo
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = VerdeExito,
                                                    modifier = Modifier.size(11.dp)
                                                )
                                                Spacer(modifier = Modifier.width(3.dp))
                                                Text(
                                                    text = "Listo",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = VerdeExito
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Outlined.PhotoSizeSelectSmall,
                                            contentDescription = null,
                                            tint = TextoSecundario,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Compresión automática (> 2 MB)",
                                            fontSize = 11.sp,
                                            color = TextoSecundario
                                        )
                                    }
                                }
                                Surface(
                                    shape = CircleShape,
                                    color = RojoFondo,
                                    modifier = Modifier.size(34.dp)
                                ) {
                                    IconButton(onClick = { tieneAdjunto = false }) {
                                        Icon(
                                            imageVector = Icons.Outlined.DeleteOutline,
                                            contentDescription = "Eliminar adjunto",
                                            tint = RojoError,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Groups,
                                contentDescription = null,
                                tint = AzulPrincipal,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Afecta múltiples posiciones",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Marca esta opción si varios operadores en sala presentan la misma falla simultáneamente.",
                            fontSize = 12.sp,
                            color = TextoSecundario,
                            lineHeight = 17.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Switch(
                        checked = afectaMultiplesPosiciones,
                        onCheckedChange = { afectaMultiplesPosiciones = it },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = AzulOscuro,
                            checkedThumbColor = Color.White
                        )
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFEAF2FC),
                border = BorderStroke(1.dp, Color(0xFFD2E2F8)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Outlined.VerifiedUser,
                        contentDescription = null,
                        tint = AzulOscuro,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Compromiso de Respuesta TI",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "Al ser reportado como prioridad alta en marcador, un ingeniero de infraestructura responderá en menos de 15 minutos.",
                            fontSize = 12.sp,
                            color = AzulOscuro,
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onVolver,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEDF1F8),
                        contentColor = TextoPrincipal
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(0.38f)
                        .height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Atrás",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onEnviarTicket,
                    enabled = descripcion.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulPrincipal,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(0.62f)
                        .height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = null,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Enviar ticket",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
