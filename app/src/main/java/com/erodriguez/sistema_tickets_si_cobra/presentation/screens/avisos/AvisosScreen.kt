package com.erodriguez.sistema_tickets_si_cobra.presentation.screens.avisos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.CelesteBadge
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoPantalla
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoError
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoFondo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeExito

private data class AvisoVisual(
    val codigo: String,
    val titulo: String,
    val detalle: String,
    val hace: String,
    val urgente: Boolean
)

@Composable
fun AvisosScreen() {
    val avisos = listOf(
        AvisoVisual(
            codigo = "TKT-1024",
            titulo = "Ticket asignado: Falla de marcación predictiva",
            detalle = "Campaña Bancaria 2 · SLA restante: 45 min",
            hace = "Hace 4 min",
            urgente = true
        ),
        AvisoVisual(
            codigo = "TKT-1008",
            titulo = "Alerta de SLA vencido: VPN Cisco",
            detalle = "Redes y VPN · Vencido hace 15 min",
            hace = "Hace 15 min",
            urgente = true
        ),
        AvisoVisual(
            codigo = "TKT-1019",
            titulo = "Nuevo ticket en cola prioritaria",
            detalle = "Error de acceso al CRM Sicobra Cobranzas",
            hace = "Hace 2h",
            urgente = false
        )
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
                    Column {
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
                            text = "Avisos",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = BordeSuave)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(avisos) { aviso ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, BordeSuave),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (aviso.urgente) RojoFondo else AzulSuave,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (aviso.urgente) Icons.Outlined.WarningAmber else Icons.Outlined.NotificationsActive,
                                    contentDescription = null,
                                    tint = if (aviso.urgente) RojoError else AzulPrincipal,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                EtiquetaChip(
                                    texto = aviso.codigo,
                                    colorFondo = AzulSuave,
                                    colorTexto = AzulPrincipal
                                )
                                Text(
                                    text = aviso.hace,
                                    fontSize = 11.sp,
                                    color = TextoSecundario
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = aviso.titulo,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = aviso.detalle,
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )
                        }
                    }
                }
            }
        }
    }
}
