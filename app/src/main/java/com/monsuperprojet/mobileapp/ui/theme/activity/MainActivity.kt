package com.monsuperprojet.mobileapp.ui.theme.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.monsuperprojet.mobileapp.R
import com.monsuperprojet.mobileapp.data.NouveauProduit
import com.monsuperprojet.mobileapp.ui.theme.adapter.ProduitAdapter
import com.monsuperprojet.mobileapp.ui.theme.viewmodel.ProduitViewModel
import com.monsuperprojet.mobileapp.data.Statistiques
import com.monsuperprojet.mobileapp.ui.theme.adapter.ProduitDialogFragment

class MainActivity : AppCompatActivity() {

    // Variables membres (inchangées)
    private lateinit var produitViewModel: ProduitViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var produitAdapter: ProduitAdapter
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupPieChart()
        initViewModel()
        setupObservers()
        loadData()
    }
//code pour l ajoueeee
    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerViewProduits)
        pieChart = findViewById(R.id.pieChart)

        // Gérer le clic sur le bouton Ajouter
        val fabAdd = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAdd)
    fabAdd.setOnClickListener {
        ProduitDialogFragment.newAddInstance { produit ->
            val nouveauProduit = NouveauProduit(
                design = produit.design,
                prix = produit.prix,
                quantite = produit.quantite
            )
            produitViewModel.addProduit(nouveauProduit)
        }.show(supportFragmentManager, "AddProduitDialog")
    }
    }
// code pour modifeee
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        produitAdapter = ProduitAdapter(
            onEditClick = { produit ->
                // Ouvrir le dialog de modification
                ProduitDialogFragment.newEditInstance(produit) { updatedProduit ->
                    produitViewModel.updateProduit(updatedProduit)
                }.show(supportFragmentManager, "EditProduitDialog")
            },
            onDeleteClick = { produit ->
                // Gérer la suppression
                produitViewModel.deleteProduit(produit.numProduit)
            }
        )
        recyclerView.adapter = produitAdapter
    }

    private fun setupPieChart() {
        pieChart.apply {
            setUsePercentValues(false)
            description.isEnabled = false
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(12f)
            setHoleColor(Color.TRANSPARENT)
        }
    }

    private fun initViewModel() {
        produitViewModel = ViewModelProvider(this).get(ProduitViewModel::class.java)
    }

    private fun setupObservers() {
        // Observer pour les produits
        produitViewModel.produits.observe(this, Observer { produits ->
            produits?.let {
                produitAdapter.submitList(it)
                if (it.isEmpty()) showToast("Aucun produit trouvé")
            }
        })

        // Observer pour les statistiques
        produitViewModel.stats.observe(this, Observer { stats ->
            stats?.let { updatePieChart(it) }
        })

        // Observer pour les erreurs
        produitViewModel.errorMessage.observe(this, Observer { error ->
            error?.let { showToast(it) }
        })
        produitViewModel.successMessage.observe(this, Observer { message ->
            message?.let { showToast(it) }
        })

    }

    private fun loadData() {
        produitViewModel.fetchProduits()
        produitViewModel.fetchStats()
    }

    private fun updatePieChart(stats: Statistiques) {
        val entries = listOf(
            PieEntry(stats.min.toFloat(), "Min"),
            PieEntry(stats.max.toFloat(), "Max"),
            PieEntry(stats.total.toFloat(), "Total")
        )

        PieDataSet(entries, "Statistiques").apply {
            colors = listOf(Color.RED, Color.BLUE, Color.GREEN)
            valueTextColor = Color.WHITE
            valueTextSize = 14f
        }.let { dataSet ->
            pieChart.data = PieData(dataSet)
            pieChart.invalidate()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
//package com.monsuperprojet.mobileapp.ui.theme.activity
//
//import android.graphics.Color
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.github.mikephil.charting.charts.PieChart
//import com.github.mikephil.charting.data.PieData
//import com.github.mikephil.charting.data.PieDataSet
//import com.github.mikephil.charting.data.PieEntry
//import com.monsuperprojet.mobileapp.R
//import com.monsuperprojet.mobileapp.ui.theme.adapter.ProduitAdapter
//import com.monsuperprojet.mobileapp.ui.theme.viewmodel.ProduitViewModel
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var produitViewModel: ProduitViewModel
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var produitAdapter: ProduitAdapter
//    private lateinit var pieChart: PieChart
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Initialisation des vues
//        recyclerView = findViewById(R.id.recyclerViewProduits)
//        pieChart = findViewById(R.id.pieChart)
//
//        // Configuration du RecyclerView
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        produitAdapter = ProduitAdapter()
//        recyclerView.adapter = produitAdapter
//
//        // Configuration du PieChart
//        setupPieChart()
//
//        // Initialisation du ViewModel
//        produitViewModel = ViewModelProvider(this).get(ProduitViewModel::class.java)
//
//        // Observateurs pour les données
//        observeViewModel()
//
//        // Chargement des données
//        produitViewModel.fetchProduits()
//        produitViewModel.fetchStats()
//    }
//
//    private fun setupPieChart() {
//        pieChart.setUsePercentValues(false)
//        pieChart.description.isEnabled = false
//        pieChart.setEntryLabelColor(Color.BLACK)
//        pieChart.setEntryLabelTextSize(12f)
//        pieChart.setHoleColor(Color.TRANSPARENT)
//    }
//
//    private fun observeViewModel() {
//        // Observateur pour les produits
//        produitViewModel.produits.observe(this, Observer { produits ->
//            produits?.let {
//                produitAdapter.submitList(it)
//                if (it.isEmpty()) {
//                    Toast.makeText(this, "Aucun produit trouvé", Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
//
//        // Observateur pour les statistiques
//        produitViewModel.stats.observe(this, Observer { stats ->
//            stats?.let {
//                val entries = listOf(
//                    PieEntry(it.min.toFloat(), "Min"),
//                    PieEntry(it.max.toFloat(), "Max"),
//                    PieEntry(it.total.toFloat(), "Total")
//                )
//                val dataSet = PieDataSet(entries, "Statistiques").apply {
//                    colors = listOf(Color.RED, Color.BLUE, Color.GREEN)
//                    valueTextColor = Color.WHITE
//                    valueTextSize = 14f
//                }
//                pieChart.data = PieData(dataSet)
//                pieChart.invalidate()
//            }
//        })
//
//        // Observateur pour les erreurs
//        produitViewModel.errorMessage.observe(this, Observer { error ->
//            error?.let {
//                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
//            }
//        })
//    }
//}
//package com.monsuperprojet.mobileapp.ui.theme.activity
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.github.mikephil.charting.charts.PieChart
//import com.github.mikephil.charting.data.PieData
//import com.github.mikephil.charting.data.PieDataSet
//import com.github.mikephil.charting.data.PieEntry
//import com.monsuperprojet.mobileapp.ui.theme.adapter.ProduitAdapter
//import com.monsuperprojet.mobileapp.ui.theme.viewmodel.ProduitViewModel
//import com.monsuperprojet.mobileapp.R
//
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var produitViewModel: ProduitViewModel
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var produitAdapter: ProduitAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        produitViewModel = ViewModelProvider(this).get(ProduitViewModel::class.java)
//
//        recyclerView = findViewById(R.id.recyclerViewProduits)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        produitAdapter = ProduitAdapter()
//        recyclerView.adapter = produitAdapter
//
//        produitViewModel.produits.observe(this, Observer { produits ->
//            produits?.let {
//                produitAdapter.submitList(it)
//            }
//        })
//
//        produitViewModel.stats.observe(this, Observer { stats ->
//            stats?.let {
//                val pieChart: PieChart = findViewById(R.id.pieChart)
//                val entries = listOf  (
//                    PieEntry(it.min.toFloat(), "Min"),
//                    PieEntry(it.max.toFloat(), "Max"),
//                    PieEntry(it.total.toFloat(), "Total")
//                )
//                val dataSet = PieDataSet(entries, "Stats")
//                pieChart.data = PieData(dataSet)
//                pieChart.invalidate() // Rafraîchir le graphique
//            }
//        })
//
//        produitViewModel.fetchProduits()
//        produitViewModel.fetchStats()
//    }
//}
