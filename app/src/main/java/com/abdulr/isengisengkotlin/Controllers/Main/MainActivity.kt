package com.abdulr.isengisengkotlin.Controllers.Main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abdulr.isengisengkotlin.Listeners.RecyclerViewItemClickListener
import com.abdulr.isengisengkotlin.Models.M_MainMenu
import com.abdulr.isengisengkotlin.R
import com.abdulr.isengisengkotlin.Utils.Custom
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), RecyclerViewItemClickListener {
    private lateinit var custom: Custom
    private lateinit var model: M_MainMenu
    private lateinit var adapter: MainAdapter
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    val data: ArrayList<M_MainMenu> = arrayListOf()
    var dataFilter: ArrayList<M_MainMenu> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        custom = Custom(this)
        adapter = MainAdapter(this)

        init()
        getData()
    }

    private fun getData() {
        for (i in 0..4) {
            when (i) {
                0 -> data.add(M_MainMenu("Text OCR Recognize", true))
                1 -> data.add(M_MainMenu("Biometric Authentication", true))
                2 -> data.add(M_MainMenu("Generate", true))
                3 -> data.add(M_MainMenu("Scan", true))
                4 -> data.add(M_MainMenu("Face Detection", true))
            }
        }

        data.sortWith(Comparator { o1, o2 ->
            o1.getBody().compareTo(o2.getBody())
        })
        rvContent.adapter = adapter
    }

    private fun init() {
        checkprsn()
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Main Menu"
        adapter.notifyDataSetChanged()
    }

    private fun checkprsn() {
        if(!custom.checkPermission(0)) {
            custom.allowPermission(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }

        super.onBackPressed()
    }

    override fun onItemClickListener(v: View, position: Int) {
        model = adapter.getModel(position)

        when(model.getBody()) {
            "Text OCR Recognize" -> {}
            "Biometric Authentication" -> {}
            "Generate" -> {}
            "Scan" -> {}
            "Face Detection" -> {}
        }
    }
}
