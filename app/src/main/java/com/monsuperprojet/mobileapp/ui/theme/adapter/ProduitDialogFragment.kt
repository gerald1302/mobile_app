// Chemin : app/src/main/java/com/monsuperprojet/mobileapp/ui/dialog/ProduitDialogFragment.kt
package com.monsuperprojet.mobileapp.ui.theme.adapter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.monsuperprojet.mobileapp.data.Produit
import com.monsuperprojet.mobileapp.databinding.DialogProduitBinding

class ProduitDialogFragment(
    private val produit: Produit? = null,
    private val onValidate: (Produit) -> Unit
) : DialogFragment() {

    private var _binding: DialogProduitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogProduitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        binding.dialogTitle.text = if (produit == null) "Ajouter un produit" else "Modifier le produit"
        produit?.let {
            binding.editDesign.setText(it.design)
            binding.editPrix.setText(it.prix.toString())
            binding.editQuantite.setText(it.quantite.toString())
        }
    }

    private fun setupListeners() {
        binding.btnValidate.setOnClickListener { validateAndSubmit() }
        binding.btnCancel.setOnClickListener { dismiss() }
    }

    private fun validateAndSubmit() {
        val design = binding.editDesign.text.toString().trim()
        val prix = binding.editPrix.text.toString().toDoubleOrNull()
        val quantite = binding.editQuantite.text.toString().toIntOrNull()

        when {
            design.isEmpty() -> binding.editDesign.error = "Champ requis"
            prix == null -> binding.editPrix.error = "Prix invalide"
            quantite == null -> binding.editQuantite.error = "Quantité invalide"
            else -> {
                val newProduit = Produit(
                    numProduit = produit?.numProduit ?: 0,
                    design = design,
                    prix = prix,
                    quantite = quantite
                )
                onValidate(newProduit)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newAddInstance(onValidate: (Produit) -> Unit): ProduitDialogFragment {
            return ProduitDialogFragment(onValidate = onValidate)
        }

        fun newEditInstance(produit: Produit, onValidate: (Produit) -> Unit): ProduitDialogFragment {
            return ProduitDialogFragment(produit = produit, onValidate = onValidate)
        }
    }
}
//class ProduitDialogFragment(
//    private val produit: Produit? = null,
//    private val onValidate: (Produit) -> Unit
//) : DialogFragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val binding = DialogProduitBinding.inflate(inflater, container, false)
//
//        produit?.let {
//            binding.editDesign.setText(it.design)
//            binding.editPrix.setText(it.prix.toString())
//            binding.editQuantite.setText(it.quantite.toString())
//        }
//
//        binding.btnValidate.setOnClickListener {
//            val newProduit = Produit(
//                numProduit = produit?.numProduit ?: 0,
//                design = binding.editDesign.text.toString(),
//                prix = binding.editPrix.text.toString().toDouble(),
//                quantite = binding.editQuantite.text.toString().toInt()
//            )
//            onValidate(newProduit)
//            dismiss()
//        }
//
//        binding.btnCancel.setOnClickListener { dismiss() }
//
//        return binding.root
//    }
//}
//package com.monsuperprojet.mobileapp.ui.dialog
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.DialogFragment
//import com.monsuperprojet.mobileapp.data.Produit
//import com.monsuperprojet.mobileapp.databinding.DialogProduitBinding
//
//class ProduitDialogFragment(
//    private val produit: Produit? = null,
//    private val onValidate: (Produit) -> Unit
//) : DialogFragment() {
//
//    private var _binding: DialogProduitBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = DialogProduitBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupViews()
//        setupListeners()
//    }
//
//    private fun setupViews() {
//        binding.apply {
//            // Configuration initiale basée sur le mode (ajout/modif)
//            dialogTitle.text = if (produit == null) {
//                "Ajouter un produit"
//            } else {
//                "Modifier le produit"
//            }
//
//            // Pré-remplissage des champs si en mode modification
//            produit?.let {
//                editDesign.setText(it.design)
//                editPrix.setText(it.prix.toString())
//                editQuantite.setText(it.quantite.toString())
//            }
//        }
//    }
//
//    private fun setupListeners() {
//        binding.apply {
//            btnValidate.setOnClickListener { validateAndSubmit() }
//            btnCancel.setOnClickListener { dismiss() }
//        }
//    }
//
//    private fun validateAndSubmit() {
//        binding.apply {
//            val design = editDesign.text.toString().trim()
//            val prixText = editPrix.text.toString().trim()
//            val quantiteText = editQuantite.text.toString().trim()
//
//            // Validation simple
//            when {
//                design.isEmpty() -> editDesign.error = "La désignation est requise"
//                prixText.isEmpty() -> editPrix.error = "Le prix est requis"
//                quantiteText.isEmpty() -> editQuantite.error = "La quantité est requise"
//                else -> {
//                    try {
//                        val newProduit = Produit(
//                            numProduit = produit?.numProduit ?: 0, // 0 pour les nouveaux produits
//                            design = design,
//                            prix = prixText.toDouble(),
//                            quantite = quantiteText.toInt()
//                        )
//                        onValidate(newProduit)
//                        dismiss()
//                    } catch (e: NumberFormatException) {
//                        editPrix.error = "Format de prix invalide"
//                        editQuantite.error = "Format de quantité invalide"
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    companion object {
//        fun newAddInstance(onValidate: (Produit) -> Unit): ProduitDialogFragment {
//            return ProduitDialogFragment(onValidate = onValidate)
//        }
//
//        fun newEditInstance(produit: Produit, onValidate: (Produit) -> Unit): ProduitDialogFragment {
//            return ProduitDialogFragment(produit = produit, onValidate = onValidate)
//        }
//    }
//}