package com.example.carrentv1

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.carrentv1.Navegation.AppScreens
import com.example.carrentv1.Screens.*
import com.example.carrentv1.Screens.PantallaAutos // <-- CORRECCIÓN AQUÍ
import com.example.carrentv1.ui.screens.TerminosYCondicionesScreenResponsive
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.delay
import androidx.navigation.navArgument
import androidx.navigation.NavType
import java.util.Locale
import android.content.res.Configuration
import com.example.carrentv1.ui.theme.CarRentV1Theme
import com.example.carrentv1.data.ThemePreference
import com.example.carrentv1.data.LanguagePreference
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class) // <-- ANOTACIÓN AÑADIDA
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val themePreference = remember { ThemePreference(context) }
            val darkTheme by themePreference.themePreference.collectAsState(initial = false)

            val languagePreference = remember { LanguagePreference(context) }
            val selectedLanguage by languagePreference.languagePreference.collectAsState(initial = LanguagePreference.DEFAULT_LANGUAGE)

            // --- Lógica para el idioma ---
            LaunchedEffect(selectedLanguage) {
                val newLocale = Locale(selectedLanguage)
                Locale.setDefault(newLocale)
                val configuration = Configuration(context.resources.configuration)
                configuration.setLocale(newLocale)
                context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
            }

            // --- Lógica para la orientación ---
            val navController = rememberAnimatedNavController()
            LockScreenOrientation(navController)

            CarRentV1Theme(darkTheme = darkTheme) {
                AppNavigation(navController) // Pasar el navController
            }
        }
    }
}

@Composable
fun LockScreenOrientation(navController: NavHostController) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return // Obtener la actividad

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        val canRotate = currentRoute?.startsWith(AppScreens.Terminos.route) == true ||
                        currentRoute?.startsWith(AppScreens.detalleAuto.route.substringBefore("/")) == true // Comprobar la base de la ruta

        activity.requestedOrientation = if (canRotate) {
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(navController: NavHostController) { // Aceptar el NavController

    AnimatedNavHost(
        navController = navController,
        startDestination = "splash",
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() }
    ) {
        composable("splash") {
            SplashScreen(navController)
        }

        composable(route = AppScreens.Inicio.route){
            InicioScreen(navController)
        }
        composable(route = AppScreens.Registro.route){
            RegistroScreen(navController)
        }
        composable(route = AppScreens.Login.route){
            PantallaLoginScreen(navController)
        }
        composable(route = AppScreens.dashboard.route){
            PantallaAutos(navController)
        }
        composable(route = AppScreens.RentaAuto.route){
            RentCarForm(navController)
        }
        composable(
            route = AppScreens.detalleAuto.route, 
            arguments = listOf(navArgument("carId") { type = NavType.StringType })
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")
            CarDetailScreen(navController, carId = carId)
        }
        composable(route = AppScreens.Ajustes.route){
            ConfiguracionScreen(navController)
        }
        composable(route = AppScreens.Contacto.route){
            ContactoScreen(navController)
        }
        composable(route = AppScreens.Soporte.route){
            SoporteTecnicoScreen(navController)
        }
        composable(route = AppScreens.Perfil.route){
            PerfilUsuarioScreen(navController)
        }
        composable(route = AppScreens.MasInfo.route){
            MasInformacionScreen(navController)
        }
        composable(route = AppScreens.PreguntasFre.route){
            PreguntasFrecuentesScreen(navController)
        }
        composable(route = AppScreens.RecuperarContra.route){
            RestablecerContrasenaScreen(navController)
        }
        composable(route = AppScreens.Terminos.route){
            TerminosYCondicionesScreenResponsive(navController)
        }
        composable(
            route = AppScreens.AlquilarAuto.route,
            arguments = listOf(navArgument("carId") { type = NavType.StringType })
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")
            NuevoAlquilerScreen(navController, carId = carId)
        }
        composable(
            route = AppScreens.ResumenPago.route,
            arguments = listOf(
                navArgument("carId") { type = NavType.StringType },
                navArgument("totalPrice") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")
            val totalPrice = backStackEntry.arguments?.getString("totalPrice")
            ResumenPagoScreen(navController, carId = carId, totalPrice = totalPrice)
        }
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(3000) 
        navController.navigate(AppScreens.Inicio.route) {
            popUpTo("splash") { inclusive = true } 
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Cargando...")
    }
}