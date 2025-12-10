package com.example.carrentv1.Screens

import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
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
import androidx.compose.ui.draw.clip // <<--- IMPORTACIÓN AÑADIDA
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.example.carrentv1.R

// Data classes
data class Car(val id: String, val data: Map<String, Any?>)
data class MenuItem(@StringRes val textId: Int, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAutos(navController: NavController) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val opcionesMenu = listOf(
        MenuItem(R.string.menu_add_car, AppScreens.RentaAuto.route),
        MenuItem(R.string.menu_support, AppScreens.Soporte.route),
        MenuItem(R.string.menu_contact, AppScreens.Contacto.route),
        MenuItem(R.string.menu_profile, AppScreens.Perfil.route),
        MenuItem(R.string.menu_more_info, AppScreens.MasInfo.route),
        MenuItem(R.string.menu_logout, AppScreens.Inicio.route)
    )

    val db = Firebase.firestore
    val carList = remember { mutableStateListOf<Car>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        db.collection("coches").get()
            .addOnSuccessListener {
                carList.clear()
                for (document in it) { carList.add(Car(id = document.id, data = document.data)) }
                isLoading = false
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al cargar vehículos: ${it.message}", Toast.LENGTH_LONG).show()
                isLoading = false
            }
    }

    // --- Función para compartir ---
    val onShareClicked = {
        val shareText = context.getString(R.string.share_text)
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, context.getString(R.string.share_title))
        context.startActivity(shareIntent)
        expanded = false
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                opcionesMenu.forEach { item ->
                    NavigationDrawerItem(
                        icon = { /* Icono opcional */ },
                        label = { Text(stringResource(id = item.textId)) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.route)
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.dashboard_title)) },
                    navigationIcon = { IconButton(onClick = { scope.launch { drawerState.open() } }) { Icon(Icons.Default.Menu, "Menu") } },
                    actions = {
                        Box {
                            IconButton(onClick = { expanded = !expanded }) { Icon(Icons.Default.MoreVert, "Más") }
                            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                DropdownMenuItem(text = { Text(stringResource(id = R.string.share_option)) }, onClick = onShareClicked)
                                DropdownMenuItem(text = { Text(stringResource(id = R.string.settings_option)) }, onClick = { navController.navigate(AppScreens.Ajustes.route) ; expanded = false })
                            }
                        }
                    }
                )
            },
            content = { padding ->
                Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 12.dp)) {
                    if (isLoading) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                    } else if (carList.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(stringResource(id = R.string.no_cars_available)) }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
                        ) {
                            items(carList) { car ->
                                CarItem(car = car, navController = navController)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun CarItem(car: Car, navController: NavController) {
    Card(
        modifier = Modifier.clickable { navController.navigate(AppScreens.buildDetalleAutoRoute(car.id)) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(car.data["imageUrl"]?.toString()),
                contentDescription = null,
                modifier = Modifier.height(100.dp).fillMaxWidth().clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
            Text("${car.data["marca"]} ${car.data["modelo"]}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(car.data["ubicacion"]?.toString() ?: "N/A", fontSize = 14.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Star, contentDescription = null, tint = Color.Yellow, modifier = Modifier.size(16.dp))
                Text(car.data["calif"]?.toString() ?: "0.0", fontSize = 14.sp)
            }
            Text("$${car.data["precio"]}", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
        }
    }
}