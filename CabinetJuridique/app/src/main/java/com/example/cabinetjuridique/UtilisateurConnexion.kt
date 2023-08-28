package com.example.cabinetjuridique

data class UtilisateurConnexion(
    val accessToken: String,
    val is_admin: Boolean,
    val maxAge: Long
)