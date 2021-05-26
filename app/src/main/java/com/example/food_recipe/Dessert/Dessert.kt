package com.example.food_recipe.Dessert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView;
import com.example.food_recipe.MainDish.Constant_d
import com.example.food_recipe.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_dessert.*


class Dessert : AppCompatActivity() {

    lateinit var dbHelper: MyDbHelper_d
    private lateinit var fab: FloatingActionButton
    private val NEWEST_FIRST = Constant_d.C_ADDED_TIMESTAMP_D

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dessert)

        var actionBar = supportActionBar

        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = MyDbHelper_d(this)

        loadRecords()

        fab = findViewById(R.id.floatingbutton1)
        fab.setOnClickListener{
            val intent = Intent(this, AddUpdateRecordActivity_d::class.java)
            intent.putExtra("isEditMode",false)
            startActivity(intent)

        }

    }
    private fun loadRecords() {

        val dessertadapter = dessertAdapter(this, dbHelper.getAllData(NEWEST_FIRST))
        dessert.adapter = dessertadapter
    }

    private fun searchRecords(query:String) {
        val dessertadapter = dessertAdapter(this, dbHelper.searchRecords(query))
        dessert.adapter = dessertadapter
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












