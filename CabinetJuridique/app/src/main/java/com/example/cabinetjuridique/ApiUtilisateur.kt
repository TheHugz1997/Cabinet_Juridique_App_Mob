package com.example.cabinetjuridique
import android.content.Context
import android.util.Log
import com.example.cabinetjuridique.databinding.ActivityAvocatsBinding

import com.example.cabinetjuridique.databinding.ActivityConnexionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiUtilisateur(private val apiInterface: ApiInterface,  private val context: Context) {

    private lateinit var binding: ActivityConnexionBinding

    fun postConnexion(user: Utilisateur) {
        val call = apiInterface.postConnexion(user)
        call.enqueue(object : Callback<UtilisateurConnexion> {
            override fun onResponse(call: Call<UtilisateurConnexion>, response: Response<UtilisateurConnexion>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let {
                        storeData(data, user)
                    }
                } else {
                    Log.d("Utilisateur", "onFailure: ")
                }
            }

            override fun onFailure(call: Call<UtilisateurConnexion>, t: Throwable) {
                Log.d("Utilisateur", "onFailure: " + t.message)
            }
        })
    }

    private fun storeData(data: UtilisateurConnexion, user: Utilisateur) {
        val expiresAt = System.currentTimeMillis() + (data.maxAge * 1000L)
        val prefs = context.getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putString("currentUser", user.mail_utilisateur)
        editor.putLong("expires_at", expiresAt)
        editor.putBoolean("is_admin", data.is_admin)

        editor.apply()
    }

    fun isUserConnected(): Boolean {
        val prefs = context.getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
        val currentUser = prefs.getString("currentUser", null)
        val expiresAt = prefs.getLong("expires_at", 0)
        val currentTime = System.currentTimeMillis()

        return currentUser != null && currentTime <= expiresAt
    }
}