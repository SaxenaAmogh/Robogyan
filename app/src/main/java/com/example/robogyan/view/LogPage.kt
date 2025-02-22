package com.example.robogyan.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.R
import com.example.robogyan.ui.theme.Black
import com.example.robogyan.ui.theme.CharcoalBlack
import com.example.robogyan.ui.theme.Cyan
import com.example.robogyan.ui.theme.latoFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogPage(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var searchItem by remember { mutableStateOf("") }
    var door by remember { mutableStateOf(false) }

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CharcoalBlack)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                horizontal = 0.035 * screenWidth
                            )
                            .fillMaxSize()
                            .align(Alignment.TopCenter)
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Lab Access Logs",
                                    color = Color.White,
                                    fontSize = 30.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.W500
                                )
                                Row {
                                    FloatingActionButton(
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(50))
                                            .size(46.dp),
                                        onClick = { },
                                        containerColor = Color(0xFFE0E0E0),
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.edit),
                                            contentDescription = "cart",
                                            Modifier.size(30.dp),
                                            tint = Black
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.me),
                                        contentDescription = "Profile",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(50.dp))
                                            .size(46.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item {
                            OutlinedTextField(
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF2a2a2a),
                                    unfocusedBorderColor = Color(0xFF2a2a2a),
                                    cursorColor = Color(0xFFFFFFFF),
                                    containerColor = Color(0xFF2a2a2a),
                                ),
                                modifier = Modifier
                                    .padding(
                                        start = 0.005 * screenWidth,
                                        end = 0.005 * screenWidth,
                                    )
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(35)),
                                leadingIcon = {
                                    Icon(
                                        modifier = Modifier.size(22.dp),
                                        painter = painterResource(id = R.drawable.search),
                                        contentDescription = "search"
                                    )
                                },
                                value = searchItem,
                                onValueChange = { searchItem = it },
                                placeholder = {
                                    Text(
                                        color = Color(0x9EFFFFFF),
                                        text = "Search for lab access logs",
                                        fontFamily = latoFontFamily,
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        if (door) Color(0xFF4CAF50) else Color(0xFFCC443A)
                                    )
                                    .padding(
                                        horizontal = 0.03 * screenWidth,
                                        vertical = 0.05 * screenWidth
                                    )
                                    .clickable { door = !door }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    FloatingActionButton(
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(50))
                                            .size(36.dp),
                                        onClick = { },
                                        containerColor = Color(0xFFFFFFFF),
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.lock),
                                            contentDescription = "cart",
                                            Modifier.size(24.dp),
                                            tint = Black
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Text(
                                        text = "Door Status",
                                        color = Black,
                                        fontSize = 20.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.W500
                                    )
                                }
                                Spacer(modifier = Modifier.size(0.015 * screenHeight))
                                Text(
                                    text = if (door) "Door is Open" else "Door is Closed",
                                    color = Black,
                                    fontSize = 26.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.W500
                                )
                                Spacer(modifier = Modifier.size(0.015 * screenHeight))
                                FloatingActionButton(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(50))
                                        .size(width = 120.dp, height = 35.dp),
                                    containerColor = Color.White,
                                    onClick = { door = !door },
                                ){
                                    Text(
                                        text = "Open Door",
                                        color = Color.Black,
                                        fontFamily = latoFontFamily
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item{
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ){
                                Column(
                                    modifier = Modifier
                                        .width(0.45 * screenWidth)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(
                                            Color(0xFFE8B225)
                                        )
                                        .padding(
                                            horizontal = 0.03 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable { door = !door }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        FloatingActionButton(
                                            modifier = Modifier
                                                .clip(shape = RoundedCornerShape(50))
                                                .size(36.dp),
                                            onClick = { },
                                            containerColor = Color(0xFFFFFFFF),
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.lock),
                                                contentDescription = "cart",
                                                Modifier.size(24.dp),
                                                tint = Black
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(5.dp))
                                        Text(
                                            text = "Last Opened",
                                            color = Black,
                                            fontSize = 20.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.W500
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.015 * screenHeight))
                                    Text(
                                        text = "By Amogh",
                                        color = Black,
                                        fontSize = 26.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.W500
                                    )
                                    Text(
                                        text = "At 12:00 PM",
                                        color = Color(0xFFE0E0E0),
                                        fontSize = 16.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.W500
                                    )
                                    Spacer(modifier = Modifier.size(0.015 * screenHeight))
                                    FloatingActionButton(
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(50))
                                            .size(width = 120.dp, height = 35.dp),
                                        containerColor = Color.White,
                                        onClick = { door = !door },
                                    ){
                                        Text(
                                            text = "View Member",
                                            color = Color.Black,
                                            fontFamily = latoFontFamily
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .width(0.45 * screenWidth)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(
                                            Color(0xFF4CAF50)
                                        )
                                        .padding(
                                            horizontal = 0.03 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable { door = !door }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        FloatingActionButton(
                                            modifier = Modifier
                                                .clip(shape = RoundedCornerShape(50))
                                                .size(36.dp),
                                            onClick = { },
                                            containerColor = Color(0xFFFFFFFF),
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.lock),
                                                contentDescription = "cart",
                                                Modifier.size(24.dp),
                                                tint = Black
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(5.dp))
                                        Text(
                                            text = "Lab Access",
                                            color = Black,
                                            fontSize = 20.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.W500
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.015 * screenHeight))
                                    Text(
                                        text = "12 People",
                                        color = Black,
                                        fontSize = 26.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.W500
                                    )
                                    Text(
                                        text = "can unlock the lab",
                                        color = Color(0xFFE0E0E0),
                                        fontSize = 16.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.W500
                                    )
                                    Spacer(modifier = Modifier.size(0.015 * screenHeight))
                                    FloatingActionButton(
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(50))
                                            .size(width = 120.dp, height = 35.dp),
                                        containerColor = Color.White,
                                        onClick = { door = !door },
                                    ){
                                        Text(
                                            text = "View Access",
                                            color = Color.Black,
                                            fontFamily = latoFontFamily
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item{
                            repeat(12){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = Color(0xFFE0E0E0),
                                            shape = RoundedCornerShape(18.dp)
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    IconButton(
                                        onClick = { },
                                    ) {
                                        Icon(
                                            Icons.Default.Info,
                                            contentDescription = "Order",
                                            tint = Color.Gray,
                                            modifier = Modifier.size(28.dp),
                                        )
                                    }
                                    Text(
                                        text = "Opened by Amogh: 08-02-25 at 10:00 AM",
                                        color = Black,
                                        fontSize = 15.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.W500
                                    )
                                }
                                Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            }
                            Spacer(modifier = Modifier.size(innerPadding.calculateBottomPadding() + 0.07 * screenHeight))
                        }
                    }

                    // Bottom Navigation
                    Row(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(
                                horizontal = 0.035 * screenWidth
                            )
                            .background(
                                shape = RoundedCornerShape(40),
                                color = Cyan
                            ),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                navController.navigate("home"){
                                    popUpTo("home"){
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.home),
                                contentDescription = "home",
                                Modifier.size(32.dp),
                                tint = Black
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.log_d),
                                contentDescription = "explore",
                                Modifier.size(32.dp),
                                tint = Black
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("member"){
                                    popUpTo("member"){
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.member),
                                contentDescription = "cart_na",
                                Modifier.size(32.dp),
                                tint = Black
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.event),
                                contentDescription = "cart_na",
                                Modifier.size(32.dp),
                                tint = Black
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("profile"){
                                    popUpTo("profile"){
                                        inclusive = true
                                    }
                                }
                                      },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.user),
                                contentDescription = "account",
                                Modifier.size(32.dp),
                                tint = Black
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LogPagePreview() {
    LogPage(rememberNavController())
}