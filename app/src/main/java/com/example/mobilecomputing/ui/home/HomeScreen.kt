package com.example.mobilecomputing.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilecomputing.Paska



@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavController,
    paskaList: List<Paska>
) {
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        topBar = {
                TopAppBar(
                    title = { Text(text = "Reminders") },
                    actions = {
                        IconButton(
                            onClick = { navController.navigate("profile") }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Open Navigation Drawer"
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Open Navigation Drawer"
                            )
                        }
                    }
                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "createReminder") },
                //contentColor = Color.Blue,
                modifier = Modifier.padding(all = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(25.dp) ,
            //contentPadding = PaddingValues(32.dp),
        ) {

            items(paskaList) { paska ->
                OutlinedButton(
                    onClick = {  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.Transparent,
                    )
                ) {
                    Column  {
                        Text(
                            paska.name + " at " + paska.time,
                        )
                        Text(
                            paska.emoji,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
