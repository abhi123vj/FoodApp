package com.internshala.foodapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.internshala.foodapp.model.Food

@Dao
interface FoodDao {

    @Insert
    fun insertBook(bookEntity: FoodEntity)

    fun deleteBook(bookEntity: FoodEntity)
    @Delete

    @Query("SELECT * FROM foods")
    fun getAllBooks(): List<FoodEntity>

    @Query("SELECT * FROM foods WHERE food_id =:foodId")
    fun getBookById(foodId: String): FoodEntity
}