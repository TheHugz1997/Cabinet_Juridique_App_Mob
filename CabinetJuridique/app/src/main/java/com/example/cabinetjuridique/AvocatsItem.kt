package com.example.cabinetjuridique

data class AvocatsItem(
    val Domaines: List<DomainesItem>,
    val coordonnees: String,
    val honoraires: Int,
    val id_avocat: Int,
    val nom_avocat: String,
    val photo: String
)