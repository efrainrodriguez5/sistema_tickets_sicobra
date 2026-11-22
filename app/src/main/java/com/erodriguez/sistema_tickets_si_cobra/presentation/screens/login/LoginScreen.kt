package com.erodriguez.sistema_tickets_si_cobra.presentation.screens.login

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.HeadsetMic
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erodriguez.sistema_tickets_si_cobra.R
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.AzulSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.BordeSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.CelesteBadge
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoCampo
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoPantalla
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.FondoTarjetaSuave
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.RojoError
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoPrincipal
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.TextoSecundario
import com.erodriguez.sistema_tickets_si_cobra.ui.theme.VerdeExito

@Composable
fun LoginScreen(
    onIngresar: () -> Unit,
    onOlvidePassword: () -> Unit
) {
    var rolSeleccionado by remember { mutableStateOf("Técnico") }
    var correo by remember { mutableStateOf("e.rodriguez@sicobra.com") }
    var password by remember { mutableStateOf("Sicobra2026") }
    var mostrarPassword by remember { mutableStateOf(false) }
    var recordarCorreo by remember { mutableStateOf(true) }

    val roles = listOf("Colaborador", "Técnico", "Jefe de TI")
    val etiquetaRol = when (rolSeleccionado) {
        "Colaborador" -> "Operador BPO"
        "Técnico" -> "Técnico N1"
        else -> "Jefe de TI"
    }
    val correoValido = correo.trim().endsWith("@sicobra.com") && correo.trim().length > 12
    val formularioValido = correoValido && password.length >= 8

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            border = BorderStroke(1.dp, BordeSuave),
            modifier = Modifier.size(66.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_si_cobra),
                contentDescription = "Logo Sicobra",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            shape = RoundedCornerShape(50),
            color = CelesteBadge
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Bolt,
                    contentDescription = null,
                    tint = AzulPrincipal,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "SISTEMA OPERATIVO CENTRAL",
                    color = AzulPrincipal,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.6.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Sicobra Soporte TI",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Mesa de Ayuda - Operaciones BPO de\nCobranzas",
            fontSize = 14.sp,
            color = TextoSecundario,
            textAlign = TextAlign.Center,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Badge,
                    contentDescription = null,
                    tint = AzulPrincipal,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Perfil de Acceso Operativo",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextoPrincipal
                )
            }
            Text(
                text = etiquetaRol,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AzulPrincipal
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFFEDF1F8),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                roles.forEach { rol ->
                    val seleccionado = rol == rolSeleccionado
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (seleccionado) AzulPrincipal else Color.Transparent,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { rolSeleccionado = rol }
                    ) {
                        Text(
                            text = rol,
                            color = if (seleccionado) Color.White else TextoPrincipal,
                            fontSize = 13.sp,
                            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            border = BorderStroke(1.dp, BordeSuave),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Correo Corporativo",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    singleLine = true,
                    placeholder = { Text("usuario@sicobra.com", color = TextoSecundario) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.AlternateEmail,
                            contentDescription = "Correo",
                            tint = TextoSecundario
                        )
                    },
                    trailingIcon = {
                        if (correoValido) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Correo válido",
                                tint = VerdeExito,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = FondoCampo,
                        unfocusedContainerColor = FondoCampo,
                        focusedBorderColor = AzulPrincipal,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dominio autorizado: @sicobra.com",
                        fontSize = 11.sp,
                        color = TextoSecundario
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(
                                    if (correoValido) VerdeExito else RojoError,
                                    CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (correoValido) "Válido" else "Inválido",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (correoValido) VerdeExito else RojoError
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Contraseña",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )
                    Text(
                        text = "¿Olvidé mi contraseña?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulPrincipal,
                        modifier = Modifier.clickable { onOlvidePassword() }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    placeholder = { Text("Ingresa tu contraseña", color = TextoSecundario) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "Contraseña",
                            tint = TextoSecundario
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { mostrarPassword = !mostrarPassword }) {
                            Icon(
                                imageVector = if (mostrarPassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = "Mostrar contraseña",
                                tint = TextoSecundario
                            )
                        }
                    },
                    visualTransformation = if (mostrarPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = FondoCampo,
                        unfocusedContainerColor = FondoCampo,
                        focusedBorderColor = AzulPrincipal,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Autenticación vinculada a Active Directory BPO",
                    fontSize = 11.sp,
                    color = TextoSecundario
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = recordarCorreo,
                        onCheckedChange = { recordarCorreo = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = AzulPrincipal,
                            uncheckedColor = TextoSecundario
                        )
                    )
                    Text(
                        text = "Recordar correo",
                        fontSize = 14.sp,
                        color = TextoPrincipal,
                        modifier = Modifier.clickable { recordarCorreo = !recordarCorreo }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                        contentDescription = "Ayuda",
                        tint = TextoSecundario,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onIngresar,
                    enabled = formularioValido,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulPrincipal,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Login,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ingresar",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onIngresar,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulSuave,
                        contentColor = AzulPrincipal
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ingresar con huella digital",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = FondoTarjetaSuave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = CelesteBadge,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.HeadsetMic,
                            contentDescription = "Soporte",
                            tint = AzulPrincipal,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "¿Inconvenientes con tu acceso?",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoPrincipal
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("Comunícate por anexo interno ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = AzulPrincipal)) {
                                append("#4040")
                            }
                            append(" o acércate a la cabina de Soporte TI en Piso 3.")
                        },
                        fontSize = 12.sp,
                        color = TextoSecundario,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            shape = RoundedCornerShape(50),
            color = Color(0xFFEDF2FA)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = VerdeExito,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Conexión Cifrada SSL 256-bit • Red Sicobra",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextoPrincipal
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Versión 2.4.1 (Build 824) • SLA 99.9% Floor Ready",
            fontSize = 11.sp,
            color = TextoSecundario
        )
    }
}
