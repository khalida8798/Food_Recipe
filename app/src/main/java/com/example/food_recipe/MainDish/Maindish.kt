package com.example.food_recipe.MainDish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView;
import com.example.food_recipe.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_maindish.*


class Maindish : AppCompatActivity() {

    lateinit var dbHelper: MyDbHelper
    private lateinit var fab: FloatingActionButton
    private val NEWEST_FIRST = Constant.C_ADDED_TIMESTAMP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maindish)

        var actionBar = supportActionBar

        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = MyDbHelper(this)

        loadRecords()

        fab = findViewById(R.id.floatingbutton)
        fab.setOnClickListener{
            val intent = Intent(this, AddUpdateRecordActivity::class.java)
            intent.putExtra("isEditMode",false)
            startActivity(intent)

        }

    }
    private fun loadRecords() {

        val maindishadapter = maindishAdapter(this, dbHelper.getAllData(NEWEST_FIRST))
        rvmain_dish.adapter = maindishadapter
    }

    private fun searchRecords(query:String) {
        val maindishadapter = maindishAdapter(this, dbHelper.searchRecords(query))
        rvmain_dish.adapter = maindishadapter
    }
    public override fun onResume() {
        super.onResume()
        loadRecords()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu!!.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener{

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText !=null) {
                    searchRecords(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query !=null){
                    searchRecords(query)
                }
                return true
            }
            })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
    }












