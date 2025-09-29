package api.base

sealed class ApiClientSettings(val baseUrl: String) {
    data object Server: ApiClientSettings("http://0.0.0.0:1337")
    data object Telegram: ApiClientSettings(
        baseUrl = "https://api.telegram.org/bot8359890378:AAEcNGb_Zy5e7ehh1SOBnilzThn5xOcMaqY"
    )
}