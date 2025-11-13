package com.example.carrentv1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carrentv1.R


@Composable
fun TerminosYCondicionesScreenResponsive(navController: NavController) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    val isLandscape = orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            if (isLandscape) {
                // 🌄 Diseño en horizontal
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Columna izquierda: logo y título
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = { /* Acción de regresar */ }, modifier = Modifier.align(Alignment.Start)) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Regresar",
                                tint = Color.Black
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.car_rent),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(8.dp)
                        )

                        Text(
                            text = "Términos y Condiciones",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Car-Rent",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }

                    // Columna derecha: texto
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .verticalScroll(rememberScrollState())
                            .padding(8.dp)
                    ) {
                        Text(
                            text = """
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                                Praesent vel tincidunt nisl. Integer ut ligula id lacus placerat luctus. 
                                Nulla facilisi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. 
                                Suspendisse potenti. Nam at sapien non nulla placerat convallis. 
                                Aliquam erat volutpat. In vitae lectus sed urna facilisis fermentum.
                            """.trimIndent(),
                            fontSize = 15.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Start,
                            lineHeight = 22.sp
                        )
                    }
                }
            } else {
                // 📱 Diseño en vertical
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    IconButton(onClick = { /* Acción de regresar */ }, modifier = Modifier.align(Alignment.Start)) {

                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.car_rent),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 8.dp)
                        )

                        Column {
                            Text(
                                text = "Términos y Condiciones",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "Car-Rent",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = """
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                            Praesent vel tincidunt nisl. Integer ut ligula id lacus placerat luctus. 
                            Nulla facilisi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. 
                            Suspendisse potenti. Nam at sapien non nulla placerat convallis. 
                            Aliquam erat volutpat. In vitae lectus sed urna facilisis fermentum.
                        """.trimIndent(),
                        fontSize = 15.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Start,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}
