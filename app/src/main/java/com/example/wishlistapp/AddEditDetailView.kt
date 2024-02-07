package com.example.wishlistapp

import android.widget.NumberPicker.OnValueChangeListener
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
){

    //its the message that appears in the bottom
    val snackMessage = remember {
        mutableStateOf("")
    }

    //we use scope to run async operations on the database
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    if (id !=0L){
        val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L, "",""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    } else{
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }


    Scaffold (
        scaffoldState = scaffoldState,
        topBar = { AppBarView(title =
        //This uses the strings inside /res/values/strings.xml
                if(id != 0L ) stringResource(id = R.string.update_wish)
            else stringResource(id = R.string.add_wish)
        )
        //this makes so that when you click you go back one screen
        {navController.navigateUp()}}


    ){
        Column(modifier = Modifier
            .padding(it)
            .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){

            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Title", value = viewModel.wishTitleState,
                onValueChanged = {
                    viewModel.onWishTitleChange(it)
                })

            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Description", value = viewModel.wishDescriptionState,
                onValueChanged = {
                    viewModel.onWishDescriptionChange(it)
                })
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                if(viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()){
                    //Update wish if isnt empty
                    if(id != 0L){
                        //update wish
                        viewModel.updateWish(
                            Wish(
                                id = id,
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim()
                            )
                        )
                    }
                    else{
                        //add wish
                        viewModel.addWish(
                            Wish(title = viewModel.wishTitleState.trim(), description = viewModel.wishDescriptionState.trim())
                        )
                        snackMessage.value = "Wish has been created"
                    }
                }else{
                    //we want to display here Enter fields to wish be created
                    snackMessage.value = "Enter fields to create a wish"
                }
                scope.launch{
                    //this will happen after the CRUD operation has happened
                    //this will show the snackmessage
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    //this will navigate to the other page after the operation has ended
                    navController.navigateUp()
                }
            }){
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_wish)
                    else stringResource(id = R.string.add_wish),
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String)-> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color= Color.Black)},
        modifier = Modifier.fillMaxWidth(),
        //you can change the keyboard that appears to be only numbers
        //you can also change how it appears when isnt focused
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = colorResource(id = R.color.app_bar_color),
            cursorColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.app_bar_color)
        )
    )
}