package com.erodriguez.sistema_tickets_si_cobra.presentation.screens.detalle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.PauseCircle
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.BordeSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoCampo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoPantalla
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoError
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HojaEstadoSolucion(
    codigo: String,
    asunto: String,
    estadoInicial: String,
    onDismiss: () -> Unit,
    onGuardar: (String, String, String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var estadoSeleccionado by remember {
        mutableStateOf(
            if (estadoInicial in listOf("En proceso", "Suspendido", "Cerrado")) estadoInicial else "Cerrado"
        )
    }
    var causaRaiz by remember { mutableStateOf("Saturación en túnel SIP / Carrier Claro") }
    var menuCausaAbierto by remember { mutableStateOf(false) }
    var solucion by remember {
        mutableStateOf(
            "Se realizó flush de sockets UDP atascados en el gateway SIP y se reinició el demonio asterisk en nodo PBX-02."
        )
    }
    var notificarOperador by remember { mutableStateOf(true) }
    var promoverBaseConocimiento by remember { mutableStateOf(true) }
    var mostrarConfirmacionCierre by remember { mutableStateOf(false) }

    val causas = listOf(
        "Saturación en túnel SIP / Carrier Claro",
        "Falla de autenticación Active Directory",
        "Caída de enlace de fibra",
        "Desconfiguración de softphone"
    )
    val estados = listOf("En proceso", "Suspendido", "Cerrado")

    if (mostrarConfirmacionCierre) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacionCierre = false },
            title = { Text("Confirmar cierre de ticket", fontWeight = FontWeight.Bold) },
            text = {
                Text("¿Confirmas cerrar el ticket $codigo con la solución aplicada? Se notificará al colaborador.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacionCierre = false
                        onGuardar(estadoSeleccionado, causaRaiz, solucion)
                    }
                ) {
                    Text("Confirmar cierre", color = AzulPrincipal, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacionCierre = false }) {
                    Text("Cancelar", color = TextoSecundario)
                }
            }
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
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
                        text = "Actualizar Estado y Solución",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "$codigo  $asunto",
                        fontSize = 12.sp,
                        color = TextoSecundario
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = TextoSecundario
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Estado Operativo Técnico",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                estados.forEach { estado ->
                    val seleccionado = estado == estadoSeleccionado
                    val iconoEstado = when (estado) {
                        "En proceso" -> Icons.Default.Sync
                        "Suspendido" -> Icons.Outlined.PauseCircle
                        else -> Icons.Outlined.CheckCircle
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (seleccionado) AzulSuave else FondoPantalla,
                            border = BorderStroke(
                                if (seleccionado) 1.5.dp else 1.dp,
                                if (seleccionado) AzulPrincipal else BordeSuave
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { estadoSeleccionado = estado }
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = iconoEstado,
                                    contentDescription = estado,
                                    tint = if (seleccionado) AzulPrincipal else TextoSecundario,
                                    modifier = Modifier.size(19.dp)
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = estado,
                                    fontSize = 12.sp,
                                    fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Medium,
                                    color = if (seleccionado) AzulPrincipal else TextoPrincipal
                                )
                            }
                        }
                        if (seleccionado) {
                            Surface(
                                shape = CircleShape,
                                color = AzulPrincipal,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                                    .size(16.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(10.dp)
                                    )
                                }
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
                    text = buildAnnotatedString {
                        append("Tipo de Causa Raíz")
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
                expanded = menuCausaAbierto,
                onExpandedChange = { menuCausaAbierto = !menuCausaAbierto },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = causaRaiz,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuCausaAbierto)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = FondoCampo,
                        unfocusedContainerColor = FondoCampo,
                        focusedBorderColor = AzulPrincipal,
                        unfocusedBorderColor = BordeSuave
                    ),
                    modifier = Modifier
                        .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = menuCausaAbierto,
                    onDismissRequest = { menuCausaAbierto = false }
                ) {
                    causas.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                causaRaiz = opcion
                                menuCausaAbierto = false
                            }
                        )
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
                    text = buildAnnotatedString {
                        append("Descripción de la Solución Aplicada")
                        withStyle(SpanStyle(color = RojoError)) {
                            append(" *")
                        }
                    },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
                Text(
                    text = "${solucion.length}/300",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextoSecundario
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = solucion,
                onValueChange = { if (it.length <= 300) solucion = it },
                minLines = 3,
                placeholder = { Text("Describe la solución técnica aplicada", color = TextoSecundario) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = FondoCampo,
                    unfocusedContainerColor = FondoCampo,
                    focusedBorderColor = AzulPrincipal,
                    unfocusedBorderColor = BordeSuave
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Esta información quedará archivada en la auditoría técnica de TI.",
                fontSize = 11.sp,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = FondoPantalla,
                border = BorderStroke(1.dp, BordeSuave),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { notificarOperador = !notificarOperador }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.NotificationsActive,
                            contentDescription = null,
                            tint = AzulPrincipal,
                            modifier = Modifier.size(19.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Notificar al operador en cabina",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                            Text(
                                text = "Envía aviso a Carlos Mendoza en su pantalla",
                                fontSize = 11.sp,
                                color = TextoSecundario
                            )
                        }
                        Checkbox(
                            checked = notificarOperador,
                            onCheckedChange = { notificarOperador = it },
                            colors = CheckboxDefaults.colors(checkedColor = AzulPrincipal)
                        )
                    }

                    HorizontalDivider(color = BordeSuave)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { promoverBaseConocimiento = !promoverBaseConocimiento }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                            contentDescription = null,
                            tint = AzulPrincipal,
                            modifier = Modifier.size(19.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Promover a Base de Conocimiento",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                            Text(
                                text = "Sugerir como plantilla para tickets similares",
                                fontSize = 11.sp,
                                color = TextoSecundario
                            )
                        }
                        Checkbox(
                            checked = promoverBaseConocimiento,
                            onCheckedChange = { promoverBaseConocimiento = it },
                            colors = CheckboxDefaults.colors(checkedColor = AzulPrincipal)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEDF1F8),
                        contentColor = TextoPrincipal
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(0.38f)
                        .height(48.dp)
                ) {
                    Text(
                        text = "Cancelar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        if (estadoSeleccionado == "Cerrado") {
                            mostrarConfirmacionCierre = true
                        } else {
                            onGuardar(estadoSeleccionado, causaRaiz, solucion)
                        }
                    },
                    enabled = solucion.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulPrincipal,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(0.62f)
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Save,
                        contentDescription = null,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Guardar Solución",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
