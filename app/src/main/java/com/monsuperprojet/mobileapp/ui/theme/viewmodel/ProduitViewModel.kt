package com.monsuperprojet.mobileapp.ui.theme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monsuperprojet.mobileapp.data.NouveauProduit
import com.monsuperprojet.mobileapp.data.Produit
import com.monsuperprojet.mobileapp.data.Statistiques
//import com.monsuperprojet.mobileapp.data.newsProduit
import com.monsuperprojet.mobileapp.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProduitViewModel : ViewModel() {

    private val _produits = MutableLiveData<List<Produit>>()
    val produits: LiveData<List<Produit>> get() = _produits

    private val _stats = MutableLiveData<Statistiques>()
    val stats: LiveData<Statistiques> get() = _stats

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> get() = _successMessage


    fun fetchProduits() {
        RetrofitClient.instance.getProduits().enqueue(object : Callback<List<Produit>> {
            override fun onResponse(call: Call<List<Produit>>, response: Response<List<Produit>>) {
                if (response.isSuccessful) {
                    _produits.value = response.body()
                    fetchStats()
                } else {
                    _errorMessage.value = "Erreur: ${response.code()} - ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<Produit>>, t: Throwable) {
                _errorMessage.value = "Échec de la connexion: ${t.message}"
            }
        })
    }

    fun fetchStats() {
        RetrofitClient.instance.getStats().enqueue(object : Callback<Map<String, Double>> {
            override fun onResponse(call: Call<Map<String, Double>>, response: Response<Map<String, Double>>) {
                if (response.isSuccessful) {
                    val stats = response.body()
                    stats?.let {
                        _stats.value = Statistiques(
                            min = it["min"] ?: 0.0,
                            max = it["max"] ?: 0.0,
                            total = it["total"] ?: 0.0
                        )
                    }
                } else {
                    _errorMessage.value = "Erreur stats: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Map<String, Double>>, t: Throwable) {
                _errorMessage.value = "Échec stats: ${t.message}"
            }
        })
    }

    fun addProduit(produit:NouveauProduit) {
        RetrofitClient.instance.ajouterProduit(produit).enqueue(object : Callback<Produit> {
            override fun onResponse(call: Call<Produit>, response: Response<Produit>) {
                if (response.isSuccessful) {
                    fetchProduits()
                    fetchStats()
                    _successMessage.value = "Produit ajouté avec succès"

                } else {
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = "Erreur ${response.code()} : $errorBody"
                }
            }

            override fun onFailure(call: Call<Produit>, t: Throwable) {
                _errorMessage.value = "Échec de l'ajout: ${t.message}"
            }
        })
    }

    fun updateProduit(produit: Produit) {
        produit.numProduit?.let { numProduit ->
            RetrofitClient.instance.updateProduit(numProduit, produit).enqueue(object : Callback<Produit> {
                override fun onResponse(call: Call<Produit>, response: Response<Produit>) {
                    if (response.isSuccessful) {
                        fetchProduits()
                        fetchStats()
                        _successMessage.value = "Produit modifié avec succès"
                    }
                    else {
                        _errorMessage.value = "Erreur lors de la modification"
                    }
                }
                override fun onFailure(call: Call<Produit>, t: Throwable) {
                    _errorMessage.value = "Échec de la modification: ${t.message}"
                }
            })
        } ?: run {
            _errorMessage.value = "Erreur: numProduit est null"
        }
    }

    fun deleteProduit(numProduit: Long?) {
        numProduit?.let { id ->
            RetrofitClient.instance.supprimerProduit(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        fetchProduits()
                        fetchStats()
                        _successMessage.value = "Produit supprimé avec succès"
                    }
                    else {
                        _errorMessage.value = "Erreur lors de la suppression"
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    _errorMessage.value = "Échec de la suppression: ${t.message}"
                }
            })
        } ?: run {
            _errorMessage.value = "Erreur: numProduit est null"
        }
    }
}
//package com.monsuperprojet.mobileapp.ui.theme.viewmodel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.monsuperprojet.mobileapp.data.Produit
//import com.monsuperprojet.mobileapp.data.Statistiques
//import com.monsuperprojet.mobileapp.utils.RetrofitClient
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class ProduitViewModel : ViewModel() {
//
//    private val _produits = MutableLiveData<List<Produit>>()
//    val produits: LiveData<List<Produit>> get() = _produits
//
//    private val _stats = MutableLiveData<Statistiques>()
//    val stats: LiveData<Statistiques> get() = _stats
//
//    fun fetchProduits() {
//        RetrofitClient.instance.getProduits().enqueue(object : Callback<List<Produit>> {
//            override fun onResponse(call: Call<List<Produit>>, response: Response<List<Produit>>) {
//                if (response.isSuccessful) {
//                    _produits.value = response.body()
//                }
//            }
//
//            override fun onFailure(call: Call<List<Produit>>, t: Throwable) {
//                // Gérer l'échec de la requête
//            }
//        })
//    }
//
//    fun fetchStats() {
//        RetrofitClient.instance.getStats().enqueue(object : Callback<Map<String, Double>> {
//            override fun onResponse(call: Call<Map<String, Double>>, response: Response<Map<String, Double>>) {
//                if (response.isSuccessful) {
//                    val stats = response.body()
//                    stats?.let {
//                        _stats.value = Statistiques(it["min"] ?: 0.0, it["max"] ?: 0.0, it["total"] ?: 0.0)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<Map<String, Double>>, t: Throwable) {
//                // Gérer l'échec de la requête
//            }
//        })
//    }
//}


//package com.monsuperprojet.mobileapp.ui.theme.viewmodel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.monsuperprojet.mobileapp.data.Produit
//import com.monsuperprojet.mobileapp.data.Statistiques
//import com.monsuperprojet.mobileapp.utils.RetrofitClient
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class ProduitViewModel : ViewModel() {
//
//    // LiveData pour les produits
//    private val _produits = MutableLiveData<List<Produit>>()
//    val produits: LiveData<List<Produit>> get() = _produits
//
//    // LiveData pour les statistiques
//    private val _stats = MutableLiveData<Statistiques>()
//    val stats: LiveData<Statistiques> get() = _stats
//
//    // Fonction pour récupérer les produits
//    fun fetchProduits() {
//        RetrofitClient.instance.getProduits().enqueue(object : Callback<List<Produit>> {
//            override fun onResponse(call: Call<List<Produit>>, response: Response<List<Produit>>) {
//                if (response.isSuccessful) {
//                    _produits.value = response.body() // Met à jour la liste des produits
//                } else {
//                    // Gérer l'échec de la réponse
//                    _produits.value = emptyList() // Optionnel : afficher une liste vide en cas d'erreur
//                }
//            }
//
//            override fun onFailure(call: Call<List<Produit>>, t: Throwable) {
//                // Gérer l'échec de la requête réseau
//                _produits.value = emptyList() // Optionnel : afficher une liste vide en cas d'erreur
//            }
//        })
//    }
//
//    // Fonction pour récupérer les statistiques
//    fun fetchStats() {
//        RetrofitClient.instance.getStats().enqueue(object : Callback<Map<String, Double>> {
//            override fun onResponse(call: Call<Map<String, Double>>, response: Response<Map<String, Double>>) {
//                if (response.isSuccessful) {
//                    val stats = response.body()
//                    stats?.let {
//                        _stats.value = Statistiques(
//                            min = it["min"] ?: 0.0,
//                            max = it["max"] ?: 0.0,
//                            total = it["total"] ?: 0.0
//                        )
//                    }
//                } else {
//                    // Gérer l'échec de la réponse
//                    _stats.value = Statistiques(0.0, 0.0, 0.0) // Optionnel : valeurs par défaut en cas d'erreur
//                }
//            }
//
//            override fun onFailure(call: Call<Map<String, Double>>, t: Throwable) {
//                // Gérer l'échec de la requête réseau
//                _stats.value = Statistiques(0.0, 0.0, 0.0) // Optionnel : valeurs par défaut en cas d'erreur
//            }
//        })
//    }
//}

//package com.monsuperprojet.mobileapp.ui.theme.viewmodel
//
//class ProduitViewModel : ViewModel() {
//
//    private val _produits = MutableLiveData<List<Produit>>()
//    val produits: LiveData<List<Produit>> get() = _produits
//
//    private val _stats = MutableLiveData<Statistiques>()
//    val stats: LiveData<Statistiques> get() = _stats
//
//    fun fetchProduits() {
//        RetrofitClient.instance.getProduits().enqueue(object : Callback<List<Produit>> {
//            override fun onResponse(call: Call<List<Produit>>, response: Response<List<Produit>>) {
//                if (response.isSuccessful) {
//                    _produits.value = response.body()
//                }
//            }
//
//            override fun onFailure(call: Call<List<Produit>>, t: Throwable) {
//                // Gérer l'échec de la requête
//            }
//        })
//    }
//
//    fun fetchStats() {
//        RetrofitClient.instance.getStats().enqueue(object : Callback<Map<String, Double>> {
//            override fun onResponse(call: Call<Map<String, Double>>, response: Response<Map<String, Double>>) {
//                if (response.isSuccessful) {
//                    val stats = response.body()
//                    stats?.let {
//                        _stats.value = Statistiques(it["min"] ?: 0.0, it["max"] ?: 0.0, it["total"] ?: 0.0)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<Map<String, Double>>, t: Throwable) {
//                // Gérer l'échec de la requête
//            }
//        })
//    }
//}
