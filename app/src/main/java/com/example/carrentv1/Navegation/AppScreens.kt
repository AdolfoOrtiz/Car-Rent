package com.example.carrentv1.Navegation

sealed class AppScreens (val route: String){
    object Inicio: AppScreens("inicio_screen")
    object Registro: AppScreens("registro_screen")
    object Login: AppScreens("login_screen")
    object Terminos: AppScreens("terminos_screen")
    object dashboard: AppScreens("dashboard_screen")
    object detalleAuto: AppScreens("detalleAuto_screen")
    object RentaAuto: AppScreens("RentaAuto_screen")
    object AlquilarAuto: AppScreens("AlquilarAuto_screen")
    object ResumenPago: AppScreens("ResumenPago_screen")
    object Ajustes: AppScreens("Ajustes_screen")
    object Contacto: AppScreens("Contacto_screen")
    object Soporte: AppScreens("Soporte_screen")
    object Perfil: AppScreens("Perfil_screen")
    object MasInfo: AppScreens("MasInfo_screen")
    object PreguntasFre: AppScreens("PreguntasFre_screen")
    object RecuperarContra: AppScreens("RestablecerContra_screen")





}