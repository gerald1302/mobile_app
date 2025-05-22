package com.monsuperprojet.mobileapp.ui.theme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.monsuperprojet.mobileapp.data.Produit
import com.monsuperprojet.mobileapp.databinding.ItemProduitBinding

class ProduitAdapter(
    private val onEditClick: (Produit) -> Unit,
    private val onDeleteClick: (Produit) -> Unit
) : ListAdapter<Produit, ProduitAdapter.ProduitViewHolder>(ProduitDiffCallback()) {

    // 1. Constructeur modifié pour recevoir les callbacks
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduitViewHolder {
        val binding = ItemProduitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProduitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProduitViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // 2. ViewHolder avec gestion des clics
    inner class ProduitViewHolder(private val binding: ItemProduitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(produit: Produit) {
            binding.apply {
                this.produit = produit

                // 3. Gestion des clics sur les boutons
                btnEdit.setOnClickListener { onEditClick(produit) }
                btnDelete.setOnClickListener { onDeleteClick(produit) }

                executePendingBindings()
            }
        }
    }
}
//class ProduitAdapter : ListAdapter<Produit, ProduitAdapter.ProduitViewHolder>(ProduitDiffCallback()) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProduitViewHolder {
//        val binding = ItemProduitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ProduitViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ProduitViewHolder, position: Int) {
//        val produit = getItem(position)
//        holder.bind(produit)
//    }
//
//    inner class ProduitViewHolder(private val binding: ItemProduitBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(produit: Produit) {
//            binding.produit = produit
//            binding.executePendingBindings()
//        }
//    }
//}
