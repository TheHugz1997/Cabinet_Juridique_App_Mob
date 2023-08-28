package com.example.cabinetjuridique

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @GET(value = "domaines")
    fun getDomaines(): Call<List<DomainesItem>>

    @GET(value = "avocats")
    fun getAvocats(): Call<List<AvocatsItem>>

    @POST(value = "connexion")
    fun postConnexion(@Body user: Utilisateur): Call<UtilisateurConnexion>

    @POST(value = "inscription")
    fun postInscription(@Body user: UtilisateurEnregistrement): Call<UtilisateurEnregistrement>

    @POST("/avocat/horaire")
    fun postRendezVous(@Body data: RendezVous): Call<String>

}