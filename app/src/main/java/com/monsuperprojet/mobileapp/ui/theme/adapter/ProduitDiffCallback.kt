package com.monsuperprojet.mobileapp.ui.theme.adapter

import androidx.recyclerview.widget.DiffUtil
import com.monsuperprojet.mobileapp.data.Produit

class ProduitDiffCallback : DiffUtil.ItemCallback<Produit>() {
    override fun areItemsTheSame(oldItem: Produit, newItem: Produit): Boolean {
        return oldItem.numProduit == newItem.numProduit
    }

    override fun areContentsTheSame(oldItem: Produit, newItem: Produit): Boolean {
        return oldItem == newItem
    }
}
