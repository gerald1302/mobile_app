package com.monsuperprojet.mobileapp.api

import com.monsuperprojet.mobileapp.data.NouveauProduit
import com.monsuperprojet.mobileapp.data.Produit
//import com.monsuperprojet.mobileapp.data.newsProduit

import retrofit2.Call

import retrofit2.http.*

interface ProduitApiService {

    @GET("api/produits")
    fun getProduits(): Call<List<Produit>>

    @POST("api/produits")
    fun ajouterProduit(@Body produit:NouveauProduit): Call<Produit>

    @PUT("api/produits/{numProduit}")
    fun updateProduit(@Path("numProduit") numProduit: Long, @Body produit: Produit): Call<Produit>

    @DELETE("api/produits/{numProduit}")
    fun supprimerProduit(@Path("numProduit") numProduit: Long): Call<Void>

    @GET("api/produits/stats")
    fun getStats(): Call<Map<String, Double>>
}
