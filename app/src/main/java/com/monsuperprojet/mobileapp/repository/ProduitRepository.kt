package com.monsuperprojet.mobileapp.repository

import com.monsuperprojet.mobileapp.api.ProduitApiService
import com.monsuperprojet.mobileapp.utils.RetrofitClient

object ProduitRepository {
    val apiService: ProduitApiService = RetrofitClient.instance
}
