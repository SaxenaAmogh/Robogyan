package com.example.robogyan.view

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.R
import com.example.robogyan.ui.theme.AccentColor
import com.example.robogyan.ui.theme.Black
import com.example.robogyan.ui.theme.Cyan
import com.example.robogyan.ui.theme.ThemeBlue
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.viewmodel.GateLogsViewModel
import com.example.robogyan.viewmodel.MemberViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogPage(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var searchItem by remember { mutableStateOf("") }
    var door by remember { mutableStateOf(false) }
    val memberViewModel: MemberViewModel = viewModel()
    val members by memberViewModel.members.observeAsState(emptyList())
    val gateLogViewModel: GateLogsViewModel = viewModel()
    val gateLogs by gateLogViewModel.gateLogs.observeAsState(emptyList())

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
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
                            .align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(
                                    painter = painterResource(R.drawable.notification),
                                    contentDescription = "notification",
                                    Modifier.size(32.dp),
                                    tint = ThemeBlue
                                )
                                Text(
                                    text = "Gate Logs",
                                    color = ThemeBlue,
                                    fontSize = 20.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "notification",
                                    Modifier.size(35.dp),
                                    tint = ThemeBlue
                                )
                            }
                            Spacer(modifier = Modifier.size(0.015 * screenHeight))
                        }
                        item{
                            OutlinedTextField(
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFFECECEC),
                                    unfocusedBorderColor = Color(0xFFECECEC),
                                    cursorColor = Color(0xFF000000),
                                    containerColor = Color(0xFFf7f7f7),
                                ),
                                modifier = Modifier
                                    .padding(
                                        start = 0.005 * screenWidth,
                                        end = 0.005 * screenWidth,
                                    )
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(35),
                                leadingIcon = {
                                    Icon(
                                        modifier = Modifier.size(22.dp),
                                        painter = painterResource(id = R.drawable.search),
                                        contentDescription = "search",
                                        tint = ThemeBlue
                                    )
                                },
                                value = searchItem,
                                onValueChange = { searchItem = it },
                                placeholder = {
                                    Text(
                                        color = ThemeBlue,
                                        text = "Search for a member",
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
                                    .clip(RoundedCornerShape(25.dp))
                                    .border(
                                        width = 2.dp,
                                        color = ThemeBlue,
                                        shape = RoundedCornerShape(25.dp)
                                    )
                                    .padding(
                                        horizontal = 0.07 * screenWidth,
                                        vertical = 0.035 * screenWidth
                                    )
                                    .clickable { door = !door }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "Welcome!",
                                            color = ThemeBlue,
                                            fontSize = 22.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Experience Engineering",
                                            color = ThemeBlue,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Image(
                                        painter = painterResource(R.drawable.rg),
                                        contentDescription = "rg",
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(ThemeBlue)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.035 * screenWidth
                                        )
                                        .size(0.4 * screenWidth)
                                        .clickable {
                                        }
                                ){
                                    Text(
                                        text = " Last Opened by",
                                        color = Color(0xFFDADADA),
                                        fontSize = 14.sp,
                                        fontFamily = latoFontFamily,
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.Center),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.unlocked),
                                            contentDescription = "notification",
                                            tint = Color.White,
                                            modifier = Modifier.size(38.dp)
                                        )
                                        Text(
                                            text = "Macle",
                                            color = Color.White,
                                            fontSize = 24.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                    Text(
                                        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 5.dp),
                                        text = "View All",
                                        color = Color(0xFFDADADA),
                                        fontSize = 18.sp,
                                        fontFamily = latoFontFamily,
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color(0xFFC3E1FF))
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.035 * screenWidth
                                        )
                                        .size(0.4 * screenWidth)
                                        .clickable {
                                        }
                                ){
                                    Text(
                                        text = " 5 People have access",
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = latoFontFamily,
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.Center),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.security),
                                            contentDescription = "notification",
                                            tint = ThemeBlue,
                                            modifier = Modifier.size(38.dp)
                                        )
                                        Text(
                                            text = "Gate Access",
                                            color = Color.Black,
                                            fontSize = 22.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                    Text(
                                        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 5.dp),
                                        text = "Edit Access",
                                        color = Color.Gray,
                                        fontSize = 18.sp,
                                        fontFamily = latoFontFamily,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item{
                            if(gateLogs.isEmpty()){
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(80.dp),
                                    color = Color.White
                                )
                            }else {
                                repeat(gateLogs.size) {
                                    val x = (gateLogs.size-1) - it
                                    var name by remember { mutableStateOf("") }
                                    for (i in members){
                                        if (i.uid == gateLogs[x].uid){
                                            name = i.name
                                            break
                                        }
                                    }
                                    val created = gateLogs[x].created ?: "2021-09-01 00:00:00"
                                    val (createdDate, createdTime) = convertToIST(created)
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
                                            text = "Opened by $name: $createdDate at $createdTime",
                                            color = Black,
                                            fontSize = 15.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.W500
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.01 * screenHeight))
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
                                color = AccentColor
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
                                painter = painterResource(R.drawable.security),
                                contentDescription = "explore",
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
                                painter = painterResource(R.drawable.projects),
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
                                painter = painterResource(R.drawable.res),
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun LogPagePreview() {
    LogPage(rememberNavController())
}