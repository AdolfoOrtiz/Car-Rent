package com.example.carrentv1.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.carrentv1.Navegation.AppScreens
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

// Data class para representar un Coche con su ID de documento
data class Car(
    val id: String,
    val data: Map<String, Any?>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAutos(navController: NavController) {

    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var expanded by remember { mutableStateOf(false) }

    val opcionesMenu = listOf("Añadir Auto", "Soporte", "Contacto", "Perfil", "Más Información", "Cerrar Sesión")

    val context = LocalContext.current
    val db = Firebase.firestore

    val carList = remember { mutableStateListOf<Car>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        db.collection("coches")
            .get()
            .addOnSuccessListener { result ->
                carList.clear()
                for (document in result) {
                    carList.add(Car(id = document.id, data = document.data))
                }
                isLoading = false
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error al cargar vehículos: ${exception.message}", Toast.LENGTH_LONG).show()
                isLoading = false
            }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                opcionesMenu.forEach { item ->
                    Button(
                        onClick = {
                            scope.launch { drawerState.close() }
                            when (item) {
                                "Añadir Auto" -> navController.navigate(AppScreens.RentaAuto.route)
                                "Perfil" -> navController.navigate(route = AppScreens.Perfil.route)
                                "Cerrar Sesión" -> navController.navigate(route = AppScreens.Inicio.route)
                                "Contacto" -> navController.navigate(route = AppScreens.Contacto.route)
                                "Soporte" -> navController.navigate(route = AppScreens.Soporte.route)
                                "Más Información" -> navController.navigate(route = AppScreens.MasInfo.route)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Text(item, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Car-Rent",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.onPrimary)
                        }
                    },
                    actions = {
                        Box {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "Más", tint = MaterialTheme.colorScheme.onPrimary)
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Compartir", color = MaterialTheme.colorScheme.onSurface) },
                                    onClick = { expanded = false }
                                )
                                DropdownMenuItem(
                                    text = { Text("Ajustes", color = MaterialTheme.colorScheme.onSurface) },
                                    onClick = { navController.navigate(route = AppScreens.Ajustes.route) }
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
                    .padding(horizontal = 12.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Busca tu Auto Favorito", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors( // Usar colores del tema
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Autos en Renta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                } else if (carList.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay autos disponibles", color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp)
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(carList) { car ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(car.data["imageUrl"]?.toString()),
                                    contentDescription = "Auto ${car.data["nombre"]?.toString()}",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(4.dp)
                                        .clickable {
                                            navController.navigate(AppScreens.buildDetalleAutoRoute(car.id))
                                        },
                                    contentScale = ContentScale.Fit
                                )
                                Text("${car.data["marca"]} ${car.data["modelo"]}", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
                                Text(car.data["ubicacion"]?.toString() ?: "N/A", fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground)
                                Text("${car.data["precio"]} ${car.data["tipoPrecio"] ?: "por hora"}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground)
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(16.dp))
                                    Text(text = car.data["calif"]?.toString() ?: "0.0", fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
