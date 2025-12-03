package com.example.carrentv1.Navegation

sealed class AppScreens (val route: String){
    object Inicio: AppScreens("inicio_screen")
    object Registro: AppScreens("registro_screen")
    object Login: AppScreens("login_screen")
    object Terminos: AppScreens("terminos_screen")
    object dashboard: AppScreens("dashboard_screen")
    object detalleAuto: AppScreens("detalleAuto_screen/{carId}")
    object RentaAuto: AppScreens("RentaAuto_screen")
    object AlquilarAuto: AppScreens("AlquilarAuto_screen/{carId}") // Ruta con placeholder para carId
    object ResumenPago: AppScreens("ResumenPago_screen/{carId}/{totalPrice}") // Ruta con placeholders para carId y totalPrice
    object Ajustes: AppScreens("Ajustes_screen")
    object Contacto: AppScreens("Contacto_screen")
    object Soporte: AppScreens("Soporte_screen")
    object Perfil: AppScreens("Perfil_screen")
    object MasInfo: AppScreens("MasInfo_screen")
    object PreguntasFre: AppScreens("PreguntasFre_screen")
    object RecuperarContra: AppScreens("RestablecerContra_screen")

    companion object {
        fun buildDetalleAutoRoute(carId: String): String {
            return "detalleAuto_screen/$carId"
        }

        fun buildAlquilarAutoRoute(carId: String): String {
            return "AlquilarAuto_screen/$carId"
        }

        // Nueva función auxiliar para construir la ruta de ResumenPago con carId y totalPrice
        fun buildResumenPagoRoute(carId: String, totalPrice: String): String {
            return "ResumenPago_screen/$carId/$totalPrice"
        }
    }
}
