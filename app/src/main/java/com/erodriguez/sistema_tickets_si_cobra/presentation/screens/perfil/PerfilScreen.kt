package com.erodriguez.sistema_tickets_si_cobra.presentation.screens.perfil

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erodriguez.sistema_tickets_si_cobra.R
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

@Composable
fun PerfilScreen(onCerrarSesion: () -> Unit) {
    var huellaActiva by remember { mutableStateOf(true) }
    var avisosPush by remember { mutableStateOf(true) }

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
                            text = "Mi perfil",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoPrincipal
                        )
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = BordeSuave)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, BordeSuave),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = AzulSuave,
                            modifier = Modifier.size(52.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Efraín Rodríguez",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoPrincipal
                            )
                            Text(
                                text = "Técnico de Soporte TI · Nivel 1",
                                fontSize = 13.sp,
                                color = AzulPrincipal,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = BordeSuave)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null,
                            tint = TextoSecundario,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "e.rodriguez@sicobra.com",
                            fontSize = 13.sp,
                            color = TextoPrincipal
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Badge,
                            contentDescription = null,
                            tint = TextoSecundario,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Terminal BPO-772 • Piso 3",
                            fontSize = 13.sp,
                            color = TextoSecundario
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
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Seguridad y Preferencias",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fingerprint,
                            contentDescription = null,
                            tint = AzulPrincipal,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Acceso con huella digital",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextoPrincipal
                            )
                            Text(
                                text = "Inicio de sesión rápido biométrico",
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )
                        }
                        Switch(
                            checked = huellaActiva,
                            onCheckedChange = { huellaActiva = it },
                            colors = SwitchDefaults.colors(checkedTrackColor = AzulPrincipal)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = BordeSuave)
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            tint = AzulPrincipal,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Notificaciones de tickets",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextoPrincipal
                            )
                            Text(
                                text = "Alertas de asignación y SLA",
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )
                        }
                        Switch(
                            checked = avisosPush,
                            onCheckedChange = { avisosPush = it },
                            colors = SwitchDefaults.colors(checkedTrackColor = AzulPrincipal)
                        )
                    }
                }
            }

            Button(
                onClick = onCerrarSesion,
                colors = ButtonDefaults.buttonColors(
                    containerColor = RojoFondo,
                    contentColor = RojoError
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cerrar sesión",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
