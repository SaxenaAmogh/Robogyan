package com.example.robogyan.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.R
import com.example.robogyan.ui.theme.Black
import com.example.robogyan.ui.theme.CharcoalBlack
import com.example.robogyan.ui.theme.Cyan
import com.example.robogyan.ui.theme.latoFontFamily

@Composable
fun HomePage(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var door by remember { mutableStateOf(false) }

    val colorList2 = listOf(
        Color(0xFFffca47),
        Color(0xFF4acfff),
        Color(0xFF8156ff),
        Color(0xFF272428),
    )

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
                        .padding(
                            horizontal = 0.035 * screenWidth
                        )
                ){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter)
                    ){
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    text = "Hi Vidhi!",
                                    color = Color.White,
                                    fontSize = 32.sp,
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
                                            painter = painterResource(R.drawable.notification),
                                            contentDescription = "cart",
                                            Modifier.size(32.dp),
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
                            Spacer(modifier = Modifier.size(0.027 * screenHeight))
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
                                Text(
                                    text = "Opened by Amogh at 10:00 AM",
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
                                        text = "Open Door",
                                        color = Color.Black,
                                        fontFamily = latoFontFamily
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFFE0E0E0),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 0.03 * screenWidth,
                                            end = 0.03 * screenWidth,
                                            top = 0.03 * screenWidth
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Members",
                                        fontFamily = latoFontFamily,
                                        color = Black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.W500
                                    )
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Text(
                                        text = "See All",
                                        fontFamily = latoFontFamily,
                                        color = Cyan,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W500,
                                        modifier = Modifier.clickable {
                                            navController.navigate("member"){
                                                popUpTo("member"){
                                                    inclusive = true
                                                }
                                            }
                                        }
                                    )
                                }
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 0.015 * screenWidth,
                                            end = 0.015 * screenWidth,
                                            bottom = 0.03 * screenWidth
                                        )
                                ) {
                                    items(6) {
                                        Column(
                                            modifier = Modifier
                                                .padding(
                                                    start = 0.015 * screenWidth,
                                                    end = 0.015 * screenWidth,
                                                    top = 0.03 * screenWidth
                                                )
                                                .background(
                                                    color = Cyan,
                                                    shape = RoundedCornerShape(15.dp)
                                                ),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.me),
                                                contentDescription = "Profile",
                                                modifier = Modifier
                                                    .padding(0.01 * screenWidth)
                                                    .clip(RoundedCornerShape(15.dp))
                                                    .size(0.25 * screenWidth),
                                                contentScale = ContentScale.Crop
                                            )
                                            Spacer(modifier = Modifier.size(0.0035 * screenHeight))
                                            Text(
                                                text = "Vidhi",
                                                color = Color.Black,
                                                fontFamily = latoFontFamily,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.W500,
                                            )
                                            Text(
                                                text = "President",
                                                fontFamily = latoFontFamily,
                                                color = Color(0xFFE0E0E0),
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.W500,
                                            )
                                            Spacer(modifier = Modifier.size(0.002 * screenHeight))
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFFE0E0E0),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 0.03 * screenWidth,
                                            end = 0.03 * screenWidth,
                                            top = 0.03 * screenWidth
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "RG Lab Logs",
                                        fontFamily = latoFontFamily,
                                        color = Black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.W500
                                    )
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Text(
                                        text = "See All",
                                        fontFamily = latoFontFamily,
                                        color = Cyan,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W500,
                                        modifier = Modifier.clickable {
                                            navController.navigate("logs"){
                                                popUpTo("logs"){
                                                    inclusive = true
                                                }
                                            }
                                        }
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            bottom = 0.03 * screenWidth
                                        )
                                ) {
                                    repeat(6){
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
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
                                                text = "Opened by Amogh at 10:00 AM",
                                                color = Black,
                                                fontFamily = latoFontFamily,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.W500
                                            )
                                            IconButton(
                                                onClick = { },
                                            ) {
                                                Icon(
                                                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                                    contentDescription = "enter",
                                                    tint = Color.Gray,
                                                    modifier = Modifier.size(34.dp),
                                                )
                                            }
                                        }
                                        HorizontalDivider(
                                            modifier = Modifier.padding(
                                                start = 0.03 * screenWidth,
                                                end = 0.03 * screenWidth,
                                            )
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item{
                            Column(
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFFE0E0E0),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 0.03 * screenWidth,
                                            end = 0.03 * screenWidth,
                                            top = 0.03 * screenWidth
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Projects & Events",
                                        fontFamily = latoFontFamily,
                                        color = Black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.W500
                                    )
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Text(
                                        text = "See All",
                                        fontFamily = latoFontFamily,
                                        color = Cyan,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W500,
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            bottom = 0.03 * screenWidth
                                        )
                                ) {
                                    repeat(4){
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            IconButton(
                                                onClick = { },
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.event_d),
                                                    contentDescription = "Order",
                                                    tint = Color.Gray,
                                                    modifier = Modifier.size(28.dp),
                                                )
                                            }
                                            Text(
                                                text = "LivPol - Started: 10th Oct 2021",
                                                fontFamily = latoFontFamily,
                                                color = Black,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.W500
                                            )
                                            IconButton(
                                                onClick = { },
                                            ) {
                                                Icon(
                                                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                                    contentDescription = "enter",
                                                    tint = Color.Gray,
                                                    modifier = Modifier.size(34.dp),
                                                )
                                            }
                                        }
                                        HorizontalDivider(
                                            modifier = Modifier.padding(
                                                start = 0.03 * screenWidth,
                                                end = 0.03 * screenWidth,
                                            )
                                        )
                                    }
                                }
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

                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.home_d),
                                contentDescription = "home",
                                Modifier.size(32.dp),
                                tint = Black
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("logs"){
                                    popUpTo("logs"){
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.log),
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
fun HomePagePreview() {
    HomePage(rememberNavController())
}