package com.internshala.foodapp.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.foodapp.R
import com.internshala.foodapp.adapter.FoodREcylerAdapter
import com.internshala.foodapp.model.Food
import com.internshala.foodapp.util.ConnectionManager
import org.json.JSONException

class RestaurantMenu : AppCompatActivity() {

    lateinit var recyclerfoood: RecyclerView



    lateinit var recyclerAdapter: FoodREcylerAdapter


    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout

    val foodInfoList = arrayListOf<Food>()

    lateinit var toolbar: Toolbar

    var restaurantId: String? = "null"
    var restaurantName: String? = "null"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)

        recyclerfoood = findViewById(R.id.recylerfood)
        recyclerfoood.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        if (intent != null) {
            restaurantId = intent.getStringExtra("restaurant_id")
            restaurantName = intent.getStringExtra("restaurant_name")
            if ((restaurantId != "null") && (restaurantName != "null"))
                setUpToolbar(restaurantName)
        } else {
            finish()
            Toast.makeText(
                this@RestaurantMenu,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }
        val queue = Volley.newRequestQueue(this@RestaurantMenu)

        val url = "http://13.235.250.119/v2/restaurants/fetch_result/$restaurantId"

        if (ConnectionManager().checkConnectivity(this@RestaurantMenu)){
            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {


                try {

                    progressLayout.visibility = View.GONE

                    val success = it.getJSONObject("data").getBoolean("success")

                    if (success){



                        val data = it.getJSONObject("data").getJSONArray("data")

                        for (i in 0 until data.length()){
                            val foodJsonObject = data.getJSONObject(i)
                           val foodObject = Food(
                                foodJsonObject.getString("id"),
                               foodJsonObject.getString("name"),
                              foodJsonObject.getString("cost_for_one"),
                               foodJsonObject.getString("restaurant_id")
                           )
                            foodInfoList.add(foodObject)

                            recyclerAdapter = FoodREcylerAdapter(this,foodInfoList)

                            recyclerfoood.adapter = recyclerAdapter



                        }

                    } else {
                        Toast.makeText(this@RestaurantMenu, "Some Error Occurred!", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this@RestaurantMenu, "Some unexpected error occurred!", Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener {

                //Here we will handle the errors

                    Toast.makeText(this@RestaurantMenu, "Volley error occurred!", Toast.LENGTH_SHORT).show()


            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "9bf534118365f1"
                    return headers
                }
            }


            queue.add(jsonObjectRequest)

        } else {
            val dialog = AlertDialog.Builder(this@RestaurantMenu)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open Settings"){text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                this@RestaurantMenu.finish()
            }

            dialog.setNegativeButton("Exit") {text, listener ->
                ActivityCompat.finishAffinity(this@RestaurantMenu)
            }
            dialog.create()
            dialog.show()
        }


    }

    fun setUpToolbar(toolbarName : String?){
        setSupportActionBar(toolbar)
        supportActionBar?.title = toolbarName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}