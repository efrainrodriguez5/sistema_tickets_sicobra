package com.erodriguez.sistema_tickets_si_cobra.presentation.screens.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.HeadsetMic
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material.icons.outlined.ReportProblem
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erodriguez.sistema_tickets_si_cobra.R
import com.erodriguez.sistema_tickets_si_cobra.presentation.common.EtiquetaChip
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AmbarAdvertencia
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulOscuro
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.BordeSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.CelesteBadge
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoPantalla
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoAlerta
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoError
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoFondo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeExito
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeFondo

private data class ResumenPeriodoVisual(
    val totalTickets: String,
    val variacion: String,
    val criticosAbiertos: String,
    val cumplimientoSla: String,
    val satisfaccion: String,
    val resueltos: String,
    val enProceso: String,
    val abiertos: String
)

private data class AreaBpoVisual(
    val nombre: String,
    val porcentaje: Int,
    val cantidad: String,
    val sla: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onExportarPdf: () -> Unit,
    onAbrirPorAsignar: () -> Unit,
    onAbrirAvisos: () -> Unit,
    onAbrirPerfil: () -> Unit
) {
    var periodoSeleccionado by remember { mutableStateOf("Este Mes (Octubre 2024)") }
    var menuPeriodoAbierto by remember { mutableStateOf(false) }

    val periodos = listOf(
        "Este Mes (Octubre 2024)",
        "Mes Anterior (Septiembre 2024)",
        "Últimos 7 días"
    )

    val resumen = when (periodoSeleccionado) {
        "Mes Anterior (Septiembre 2024)" -> ResumenPeriodoVisual(
            totalTickets = "1,317",
            variacion = "+4.1% vs mes ant.",
            criticosAbiertos = "1",
            cumplimientoSla = "94.8%",
            satisfaccion = "4.7",
            resueltos = "1,110 (84.3%)",
            enProceso = "128 (9.7%)",
            abiertos = "79 (6.0%)"
        )
        "Últimos 7 días" -> ResumenPeriodoVisual(
            totalTickets = "342",
            variacion = "+6.2% vs sem. ant.",
            criticosAbiertos = "2",
            cumplimientoSla = "97.1%",
            satisfaccion = "4.9",
            resueltos = "284 (83.0%)",
            enProceso = "38 (11.1%)",
            abiertos = "20 (5.9%)"
        )
        else -> ResumenPeriodoVisual(
            totalTickets = "1,428",
            variacion = "+8.4% vs mes ant.",
            criticosAbiertos = "3",
            cumplimientoSla = "96.2%",
            satisfaccion = "4.8",
            resueltos = "1,180 (82.6%)",
            enProceso = "165 (11.6%)",
            abiertos = "80 (5.6%)"
        )
    }

    val areasBpo = listOf(
        AreaBpoVisual("Telefonía & Asterisk / Vicidial", 38, "542 tickets acumulados", "SLA: 97.4%", AzulPrincipal),
        AreaBpoVisual("Sistemas CRM & Marcador Predictivo", 29, "418 tickets acumulados", "SLA: 95.8%", Color(0xFF0E7C7B)),
        AreaBpoVisual("Conectividad & Redes / VPN Cisco", 20, "280 tickets acumulados", "SLA: 98.2%", Color(0xFF4788D9)),
        AreaBpoVisual("Hardware & Diademas / Estaciones", 13, "188 tickets acumulados", "SLA: 99.1%", Color(0xFF7B8FA8))
    )

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
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, BordeSuave),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_si_cobra),
                            contentDescription = "Logo Sicobra",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "SICOBRA SOPORTE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoSecundario
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(shape = RoundedCornerShape(50), color = CelesteBadge) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .background(VerdeExito, CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "En línea",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VerdeExito
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Dashboard",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                    }
                    IconButton(onClick = onAbrirAvisos) {
                        Box {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Avisos",
                                tint = TextoPrincipal,
                                modifier = Modifier.size(24.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(8.dp)
                                    .background(RojoAlerta, CircleShape)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Surface(
                        shape = CircleShape,
                        color = AzulSuave,
                        modifier = Modifier
                            .size(34.dp)
                            .clickable { onAbrirPerfil() }
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
                }
                HorizontalDivider(thickness = 1.dp, color = BordeSuave)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Dashboard Operativo",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )
                    Text(
                        text = "Jefe de TI • Infraestructura & Soporte",
                        fontSize = 12.sp,
                        color = TextoSecundario
                    )
                }
                Button(
                    onClick = onExportarPdf,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulPrincipal,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PictureAsPdf,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Exportar PDF",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = menuPeriodoAbierto,
                    onExpandedChange = { menuPeriodoAbierto = !menuPeriodoAbierto },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = periodoSeleccionado,
                        onValueChange = {},
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = null,
                                tint = AzulPrincipal,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuPeriodoAbierto)
                        },
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
                        expanded = menuPeriodoAbierto,
                        onDismissRequest = { menuPeriodoAbierto = false }
                    ) {
                        periodos.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    periodoSeleccionado = opcion
                                    menuPeriodoAbierto = false
                                }
                            )
                        }
                    }
                }
                Text(
                    text = "Actualizado:\nhace 2 min",
                    fontSize = 11.sp,
                    color = TextoSecundario
                )
            }

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, BordeSuave),
                modifier = Modifier.fillMaxWidth()
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
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Outlined.HeadsetMic,
                                contentDescription = null,
                                tint = AzulPrincipal,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Campañas Bancarias y Cobranzas",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Text(
                            text = "Operación en Tiempo Real · Pisos 2, 3 y 4",
                            fontSize = 11.sp,
                            color = TextoSecundario
                        )
                    }
                    EtiquetaChip(
                        texto = "EN VIVO",
                        colorFondo = VerdeExito,
                        colorTexto = Color.White,
                        esRedondeado = true
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, BordeSuave),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tickets del Mes",
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )
                            Icon(
                                imageVector = Icons.Default.ConfirmationNumber,
                                contentDescription = null,
                                tint = AzulPrincipal,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = resumen.totalTickets,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        EtiquetaChip(
                            texto = resumen.variacion,
                            colorFondo = VerdeFondo,
                            colorTexto = VerdeExito
                        )
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, BordeSuave),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Críticos Abiertos",
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )
                            Icon(
                                imageVector = Icons.Outlined.ReportProblem,
                                contentDescription = null,
                                tint = RojoError,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = resumen.criticosAbiertos,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = RojoError
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        EtiquetaChip(
                            texto = "Atención urgente",
                            colorFondo = RojoFondo,
                            colorTexto = RojoError
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, BordeSuave),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Cumplimiento SLA",
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )
                            Icon(
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = null,
                                tint = VerdeExito,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = resumen.cumplimientoSla,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulPrincipal
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        EtiquetaChip(
                            texto = "Meta > 95%",
                            colorFondo = VerdeFondo,
                            colorTexto = VerdeExito
                        )
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, BordeSuave),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Satisfacción CSAT",
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = AzulPrincipal,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = resumen.satisfaccion,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                            Text(
                                text = " / 5.0",
                                fontSize = 13.sp,
                                color = TextoSecundario,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        EtiquetaChip(
                            texto = "94% promotores",
                            colorFondo = AzulSuave,
                            colorTexto = AzulPrincipal
                        )
                    }
                }
            }

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = RojoSuave,
                border = BorderStroke(1.dp, Color(0xFFF6C6C3)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ReportProblem,
                        contentDescription = null,
                        tint = RojoError,
                        modifier = Modifier.size(19.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${resumen.criticosAbiertos} Incidentes P1 en curso",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = RojoError
                        )
                        Text(
                            text = "Campaña BBVA y Banorte: Vicidial",
                            fontSize = 11.sp,
                            color = TextoSecundario
                        )
                    }
                    Button(
                        onClick = onAbrirPorAsignar,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RojoError,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(
                            text = "Ver P1",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
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
                        Column {
                            Text(
                                text = "Tickets por Estado",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                            Text(
                                text = "Distribución del volumen mensual",
                                fontSize = 11.sp,
                                color = TextoSecundario
                            )
                        }
                        EtiquetaChip(
                            texto = "Total ${resumen.totalTickets}",
                            colorFondo = FondoPantalla,
                            colorTexto = TextoPrincipal
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(50))
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(0.82f)
                                .height(10.dp)
                                .background(AzulPrincipal)
                        )
                        Box(
                            modifier = Modifier
                                .weight(0.12f)
                                .height(10.dp)
                                .background(Color(0xFF4788D9))
                        )
                        Box(
                            modifier = Modifier
                                .weight(0.05f)
                                .height(10.dp)
                                .background(AmbarAdvertencia)
                        )
                        Box(
                            modifier = Modifier
                                .weight(0.01f)
                                .height(10.dp)
                                .background(RojoError)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    EstadoFila("Resueltos", resumen.resueltos, AzulPrincipal)
                    HorizontalDivider(color = BordeSuave, modifier = Modifier.padding(vertical = 8.dp))
                    EstadoFila("En Proceso", resumen.enProceso, Color(0xFF4788D9))
                    HorizontalDivider(color = BordeSuave, modifier = Modifier.padding(vertical = 8.dp))
                    EstadoFila("Abiertos / Nuevos", resumen.abiertos, AmbarAdvertencia)
                    HorizontalDivider(color = BordeSuave, modifier = Modifier.padding(vertical = 8.dp))
                    EstadoFila("Críticos / Escalados", "${resumen.criticosAbiertos} (0.2%)", RojoError)
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, BordeSuave),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Áreas Operativas BPO",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )
                    Text(
                        text = "Carga de tickets por plataforma",
                        fontSize = 11.sp,
                        color = TextoSecundario
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(96.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(84.dp)) {
                                val grosor = 14.dp.toPx()
                                var anguloInicio = -90f
                                areasBpo.forEach { area ->
                                    val barrido = (area.porcentaje / 100f) * 360f
                                    drawArc(
                                        color = area.color,
                                        startAngle = anguloInicio,
                                        sweepAngle = barrido - 4f,
                                        useCenter = false,
                                        style = Stroke(width = grosor, cap = StrokeCap.Butt)
                                    )
                                    anguloInicio += barrido
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "4",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoPrincipal
                                )
                                Text(
                                    text = "Áreas",
                                    fontSize = 10.sp,
                                    color = TextoSecundario
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            areasBpo.forEach { area ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(area.color, CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = area.nombre.substringBefore(" /"),
                                            fontSize = 11.sp,
                                            color = TextoPrincipal,
                                            maxLines = 1
                                        )
                                    }
                                    Text(
                                        text = "${area.porcentaje}%",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextoPrincipal
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        areasBpo.forEach { area ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = FondoPantalla,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = area.nombre,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextoPrincipal
                                        )
                                        Text(
                                            text = area.cantidad,
                                            fontSize = 11.sp,
                                            color = TextoSecundario
                                        )
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "${area.porcentaje}%",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextoPrincipal
                                        )
                                        Text(
                                            text = area.sla,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = VerdeExito
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
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Salud de Operación TI",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                        Icon(
                            imageVector = Icons.Outlined.Shield,
                            contentDescription = null,
                            tint = AzulPrincipal,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SaludCaja("MTTR\n(Resolución)", "18m", Modifier.weight(1f))
                        SaludCaja("Primera\nRespuesta", "3.2m", Modifier.weight(1f))
                        SaludCaja("FCR (1er\ncontacto)", "78%", Modifier.weight(1f))
                    }
                }
            }

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = AzulSuave,
                border = BorderStroke(1.dp, Color(0xFFC4D7FA)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAbrirPorAsignar() }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Assignment,
                        contentDescription = null,
                        tint = AzulPrincipal,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Asignación de Tickets (Cola Abierta)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                        Text(
                            text = "Ver carga por técnico y asignar incidencias",
                            fontSize = 11.sp,
                            color = AzulPrincipal
                        )
                    }
                    Text(
                        text = "Abrir",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulPrincipal
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = AzulOscuro,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.PictureAsPdf,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Cierre Mensual & Auditoría TI",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Genera reporte consolidado con firma",
                                fontSize = 11.sp,
                                color = Color(0xFFD0DCF0)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onExportarPdf,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = AzulOscuro
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Generar Reporte Gerencial PDF",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EstadoFila(
    nombre: String,
    valor: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = nombre,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = TextoPrincipal
            )
        }
        Text(
            text = valor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )
    }
}

@Composable
private fun SaludCaja(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = FondoPantalla,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = titulo,
                fontSize = 10.sp,
                color = TextoSecundario,
                lineHeight = 13.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = valor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AzulPrincipal
            )
        }
    }
}
