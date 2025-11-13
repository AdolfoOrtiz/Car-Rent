package com.example.carrentv1.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carrentv1.R

@Composable
fun ConfiguracionScreen(navController: NavController) {
    var temaSeleccionado by remember { mutableStateOf("Dark") }
    var idiomaSeleccionado by remember { mutableStateOf("Español") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Logo en la parte superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.car_rent),
                contentDescription = "Car Rent Logo",
                modifier = Modifier
                    .size(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de Tema
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F6FA)),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Cambiar Tema",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    modifier = Modifier.selectableGroup()
                ) {
                    // Opción Dark
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (temaSeleccionado == "Dark"),
                                onClick = { temaSeleccionado = "Dark" },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (temaSeleccionado == "Dark"),
                            onClick = { temaSeleccionado = "Dark" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF1565C0)
                            )
                        )
                        Text(
                            text = "Dark",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }

                    // Separador
                    Divider(
                        color = Color(0xFFE0E0E0),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Opción White
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (temaSeleccionado == "White"),
                                onClick = { temaSeleccionado = "White" },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (temaSeleccionado == "White"),
                            onClick = { temaSeleccionado = "White" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF1565C0)
                            )
                        )
                        Text(
                            text = "White",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Sección de Idioma
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F6FA)),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Cambiar Idioma",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    modifier = Modifier.selectableGroup()
                ) {
                    // Opción Español
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (idiomaSeleccionado == "Español"),
                                onClick = { idiomaSeleccionado = "Español" },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (idiomaSeleccionado == "Español"),
                            onClick = { idiomaSeleccionado = "Español" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF1565C0)
                            )
                        )
                        Text(
                            text = "Español",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }

                    // Separador
                    Divider(
                        color = Color(0xFFE0E0E0),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Opción Ingles
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (idiomaSeleccionado == "Ingles"),
                                onClick = { idiomaSeleccionado = "Ingles" },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (idiomaSeleccionado == "Ingles"),
                            onClick = { idiomaSeleccionado = "Ingles" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF1565C0)
                            )
                        )
                        Text(
                            text = "Ingles",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botón Aplicar
        Button(
            onClick = {
                // Acción para aplicar los cambios
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
        ) {
            Text(
                text = "Aplicar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

