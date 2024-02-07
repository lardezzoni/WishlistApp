package com.example.wishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//abstract class means that we can create methods withouth any body to execute
@Dao
abstract class WishDAO {

    //This is the function that is going to INSERT on the SQL
    //is a better practice to call the wish a wishEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addWish(wishEntity: Wish)

    //This is the select all query SQL
    //For query operations, it uses the Flow reactive stream which is like
    //LiveData. It is part of the kotlin coroutines libary and is desighned
    //to provide a somple and efficient way to handle asynchronous data streams
    //in a reactive way.
    @Query("Select * from 'wish-table'")
    abstract fun getAllWishes(): Flow<List<Wish>>

    //it needs to be suspend fun because it happens in the background
    @Update
    abstract suspend fun updateAWish(wishEntity: Wish)

    @Delete
    abstract suspend fun deleteAWish(wishEntity: Wish)

    @Query("Select * from 'wish-table' where id = :id")
    abstract fun getAWishById(id:Long): Flow<Wish>
}