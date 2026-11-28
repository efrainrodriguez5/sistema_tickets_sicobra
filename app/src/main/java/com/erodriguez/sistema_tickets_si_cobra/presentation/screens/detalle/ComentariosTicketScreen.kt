package com.erodriguez.sistema_tickets_si_cobra.presentation.screens.detalle

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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.Reply
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoCampo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoPantalla
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoError
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoFondo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeExito
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeFondo

private data class ComentarioVisual(
    val autor: String,
    val cargo: String,
    val hora: String,
    val mensaje: String,
    val pie: String? = null,
    val esNotaInterna: Boolean = false,
    val archivoAdjunto: String? = null
)

@Composable
fun ComentariosTicketScreen(
    codigo: String,
    onVolver: () -> Unit
) {
    var mostrarNotasInternas by remember { mutableStateOf(true) }
    var tabSeleccionada by remember { mutableIntStateOf(1) }
    var modoNotaInterna by remember { mutableStateOf(true) }
    var textoNuevo by remember { mutableStateOf("") }

    val comentarios = remember {
        mutableStateListOf(
            ComentarioVisual(
                autor = "Carlos Mendoza",
                cargo = "Operador Cobranzas",
                hora = "09:10 AM · Estación #14",
                mensaje = "El marcador predictivo arroja error DISPO_ERR_403 al pasar llamada con cliente bancario. Ya reinicié el softphone como indica la guía pero persiste.",
                pie = "Campaña: Recuperación BBVA Retail"
            ),
            ComentarioVisual(
                autor = "Diego Morales",
                cargo = "Soporte TI N1",
                hora = "09:22 AM",
                mensaje = "Hola Carlos, recibido. Tomando el caso de inmediato. Estoy monitoreando el troncal de Claro y la traza IP de tu estación #14.",
                pie = "Diagnóstico en curso"
            ),
            ComentarioVisual(
                autor = "Diego Morales",
                cargo = "Nota Interna TI",
                hora = "09:35 AM · Solo Visible para TI",
                mensaje = "Se detectaron paquetes descartados en Kamailio Proxy hacia el gateway 192.168.10.25. Escalado directamente con telecomunicaciones Nivel 2 para descarte de congestión en VLAN de voz.",
                esNotaInterna = true,
                archivoAdjunto = "kamailio_err_packet_drop.log · 42 KB"
            ),
            ComentarioVisual(
                autor = "Elena Rojas",
                cargo = "Sup. Operaciones",
                hora = "09:48 AM",
                mensaje = "@DiegoMorales Por favor priorizar, tenemos 12 agentes detenidos con el mismo error en la campaña Bancaria 2. El tiempo de abandono está subiendo.",
                pie = "Impacto Operativo Alto"
            ),
            ComentarioVisual(
                autor = "Diego Morales",
                cargo = "Soporte TI N1",
                hora = "09:55 AM (Ahora)",
                mensaje = "Paso secundario activo. Procediendo a liberar puertos SIP y reiniciar el pool en el servidor de marcado. En 5 minutos realizamos prueba piloto en estación #14.",
                pie = "Reiniciando sockets SIP Kamailio"
            )
        )
    }

    val comentariosVisibles = comentarios.filter {
        mostrarNotasInternas || !it.esNotaInterna
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

                HorizontalDivider(color = BordeSuave)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = codigo,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    EtiquetaChip(
                        texto = "En proceso",
                        colorFondo = AzulSuave,
                        colorTexto = AzulPrincipal,
                        esRedondeado = true
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = RojoFondo
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = null,
                                tint = RojoError,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "SLA: Restan 38 min",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = RojoError
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = TextoSecundario,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(18.dp)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    listOf("Información", "Comentarios (${comentarios.size})", "Historial").forEachIndexed { indice, titulo ->
                        val activo = indice == tabSeleccionada
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    if (indice == 0) {
                                        onVolver()
                                    } else {
                                        tabSeleccionada = indice
                                    }
                                }
                        ) {
                            Text(
                                text = titulo,
                                fontSize = 13.sp,
                                fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium,
                                color = if (activo) AzulPrincipal else TextoSecundario,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(if (activo) AzulPrincipal else BordeSuave)
                            )
                        }
                    }
                }
            }
        }

        if (tabSeleccionada == 1) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFE7F5F1),
                border = BorderStroke(1.dp, Color(0xFFBFE3D9)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = VerdeExito,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(17.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Notas internas de TI",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Text(
                            text = "Visibles solo para equipo técnico",
                            fontSize = 11.sp,
                            color = VerdeExito
                        )
                    }
                    Switch(
                        checked = mostrarNotasInternas,
                        onCheckedChange = { mostrarNotasInternas = it },
                        colors = SwitchDefaults.colors(checkedTrackColor = AzulPrincipal)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color(0xFFE7ECF5)
                        ) {
                            Text(
                                text = "Hoy, 24 de Octubre",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextoSecundario,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                items(comentariosVisibles) { item ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (item.esNotaInterna) Color(0xFFEAF7F3) else Color.White
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (item.esNotaInterna) Color(0xFFB5E0D2) else BordeSuave
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(
                                            if (item.esNotaInterna) VerdeExito else AzulSuave,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.autor.split(" ").take(2).joinToString("") { it.take(1) },
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (item.esNotaInterna) Color.White else AzulPrincipal
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = item.autor,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextoPrincipal
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        EtiquetaChip(
                                            texto = item.cargo,
                                            colorFondo = if (item.esNotaInterna) VerdeFondo else AzulSuave,
                                            colorTexto = if (item.esNotaInterna) VerdeExito else AzulPrincipal
                                        )
                                    }
                                    Text(
                                        text = item.hora,
                                        fontSize = 11.sp,
                                        color = TextoSecundario
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = item.mensaje,
                                fontSize = 13.sp,
                                color = TextoPrincipal,
                                lineHeight = 18.sp
                            )

                            if (item.archivoAdjunto != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.White,
                                    border = BorderStroke(1.dp, Color(0xFFC8E6DC)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.AttachFile,
                                            contentDescription = null,
                                            tint = VerdeExito,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = item.archivoAdjunto,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = TextoPrincipal,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(
                                            imageVector = Icons.Outlined.Download,
                                            contentDescription = "Descargar",
                                            tint = VerdeExito,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            if (item.pie != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.pie,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextoSecundario
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable {
                                            textoNuevo = "@${item.autor.replace(" ", "")} "
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Outlined.Reply,
                                            contentDescription = null,
                                            tint = AzulPrincipal,
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            text = "Responder",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = AzulPrincipal
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Surface(color = Color.White, shadowElevation = 6.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    HorizontalDivider(color = BordeSuave)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (!modoNotaInterna) AzulSuave else FondoCampo,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { modoNotaInterna = false }
                        ) {
                            Text(
                                text = "Público (Operador)",
                                fontSize = 11.sp,
                                fontWeight = if (!modoNotaInterna) FontWeight.Bold else FontWeight.Medium,
                                color = if (!modoNotaInterna) AzulPrincipal else TextoSecundario,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (modoNotaInterna) VerdeExito else FondoCampo,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { modoNotaInterna = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (modoNotaInterna) Color.White else TextoSecundario,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Nota Interna (TI)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (modoNotaInterna) Color.White else TextoSecundario
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 12.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {}, modifier = Modifier.size(34.dp)) {
                            Icon(
                                imageVector = Icons.Outlined.AttachFile,
                                contentDescription = "Adjuntar",
                                tint = TextoSecundario,
                                modifier = Modifier.size(19.dp)
                            )
                        }
                        IconButton(onClick = {}, modifier = Modifier.size(34.dp)) {
                            Icon(
                                imageVector = Icons.Outlined.PhotoCamera,
                                contentDescription = "Cámara",
                                tint = TextoSecundario,
                                modifier = Modifier.size(19.dp)
                            )
                        }
                        OutlinedTextField(
                            value = textoNuevo,
                            onValueChange = { if (it.length <= 500) textoNuevo = it },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = if (modoNotaInterna) "Escribe una nota técnica..." else "Escribe una respuesta...",
                                    fontSize = 13.sp,
                                    color = TextoSecundario
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = FondoCampo,
                                unfocusedContainerColor = FondoCampo,
                                focusedBorderColor = AzulPrincipal,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = CircleShape,
                            color = if (textoNuevo.isNotBlank()) AzulPrincipal else Color(0xFFBCC7D8),
                            modifier = Modifier
                                .size(42.dp)
                                .clickable(enabled = textoNuevo.isNotBlank()) {
                                    comentarios.add(
                                        ComentarioVisual(
                                            autor = "Efraín Rodríguez",
                                            cargo = if (modoNotaInterna) "Nota Interna TI" else "Jefe de TI",
                                            hora = "Ahora",
                                            mensaje = textoNuevo.trim(),
                                            esNotaInterna = modoNotaInterna
                                        )
                                    )
                                    textoNuevo = ""
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Enviar",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    listOf(
                        "10:05 AM · Diego Morales cambió estado a En proceso",
                        "09:35 AM · Diego Morales registró Nota Interna TI (#CL-89231)",
                        "09:28 AM · Efraín Rodríguez asignó ticket a Diego Morales",
                        "09:24 AM · Carlos Mendoza creó ticket $codigo"
                    )
                ) { evento ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, BordeSuave),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = evento,
                            fontSize = 13.sp,
                            color = TextoPrincipal,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            }
        }
    }
}
