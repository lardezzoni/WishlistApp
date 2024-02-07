package com.example.wishlistapp.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDAO: WishDAO) {

    //suspend makes the function async
    suspend fun addAWish(wish: Wish){
        wishDAO.addWish(wish)
    }

    //In here the Flow takes care of async
    fun getWishes(): Flow<List<Wish>> =wishDAO.getAllWishes()

    fun getAWishById(id:Long) : Flow<Wish>{
        return wishDAO.getAWishById(id)
    }

    suspend fun updateAWish(wish: Wish){
        wishDAO.updateAWish(wish)
    }

    suspend fun deleteAWish(wish: Wish){
        wishDAO.deleteAWish(wish)
    }
}