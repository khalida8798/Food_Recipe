package com.example.food_recipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipe.Dessert.Dessert
import com.example.food_recipe.MainDish.RecipeAdapter
import com.example.food_recipe.modal.itemmodal
import com.example.food_recipe.MainDish.Maindish
import kotlinx.android.synthetic.main.recipetypes.*


class foodtype : AppCompatActivity(), RecipeAdapter.ClickListener {

    val itemlistmodel = arrayOf(
        itemmodal(R.drawable.main_dish, "Main Dish"),
        itemmodal(R.drawable.dessert, "Dessert"),
    )

    val itemmodalList = ArrayList<itemmodal>()

    var recipeadapter: RecipeAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipetypes)

        var actionBar = supportActionBar

        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        for (item in itemlistmodel) {
            itemmodalList.add(item)


        }


        recipeadapter = RecipeAdapter(this)
        recipeadapter!!.setData(itemmodalList)
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)
        rv.adapter = recipeadapter








    }

    override fun clickeditem(itemmodal: itemmodal) {

        var itemmodal1 = itemmodal
        var title = itemmodal.title

        when (title) {
            "Main Dish" ->
                startActivity(
                    Intent(this@foodtype, Maindish::class.java))

            "Dessert" ->
                startActivity(
                    Intent(this@foodtype, Dessert::class.java))

            else -> {
                Toast.makeText(this@foodtype, "Not Available", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val menuItem = menu!!.findItem(R.id.search)
        val search = menuItem.actionView as SearchView
        search.maxWidth = Int.MAX_VALUE
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                recipeadapter!!.filter.filter((p0))
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                recipeadapter!!.filter.filter((p0))
                return true
            }


        })


        return true
    }



}



