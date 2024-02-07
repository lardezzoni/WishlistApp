package com.example.wishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.data.Graph
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository
): ViewModel() {

    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChange(newString: String){
        wishTitleState = newString
    }

    fun onWishDescriptionChange(newString: String){
        wishDescriptionState = newString
    }

    //When you want to create a variable that takes some time to be created
    //like a async var with flow, you need to make sure to add lateinit that is
    // is a promise that the variable will be initialized with some time
    lateinit var getAllWishes: Flow<List<Wish>>

    //basically the constructor, the initializer
    //this makes sure that getAllWishes is not null when we initialize
    init{
        viewModelScope.launch {
            getAllWishes = wishRepository.getWishes()
        }
    }

    //Dispatchers on kotlin decides what thread the coroutine will run on
    //There are several kinds of dispatchers, this one is input and output
    //This allow to manage the coroutines threads. This ensures that the main thread
    //is not blocked.
    fun addWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO){
            wishRepository.addAWish(wish = wish)
        }
    }

    fun updateWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO){
            wishRepository.updateAWish(wish = wish)
        }
    }
    fun deleteWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO){
            wishRepository.deleteAWish(wish = wish)
        }
    }
    //in here is not IO operation
    fun getAWishById(id:Long):Flow<Wish>{
        return wishRepository.getAWishById(id)
    }
}