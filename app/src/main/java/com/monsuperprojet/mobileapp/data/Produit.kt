package com.monsuperprojet.mobileapp.data

data class Produit(
    val numProduit: Long?,
    val design: String,
    val prix: Double,
    val quantite: Int
) {
    val montant: Double
        get() = prix * quantite
}
data class NouveauProduit(
    val design: String,
    val prix: Double,
    val quantite: Int
)