package com.example.robogyan.view

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.robogyan.R
import com.example.robogyan.ui.theme.AccentColor
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.Black

import com.example.robogyan.ui.theme.CharcoalBlack
import com.example.robogyan.ui.theme.Cyan
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.ThemeBlue
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.viewmodel.GateLogsViewModel
import com.example.robogyan.viewmodel.MemberViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun convertToIST(utcDateTime: String): Pair<String, String> {
    return try {
        // Parse the UTC time
        val utcFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX")
            .withZone(ZoneId.of("UTC"))
        val instant = Instant.from(utcFormatter.parse(utcDateTime))

        // Convert to IST
        val istZone = ZoneId.of("Asia/Kolkata")
        val istFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(istZone)
        val istDateTime = istFormatter.format(instant)

        // Split Date and Time
        val (date, time) = istDateTime.split(" ")
        date to time
    } catch (e: Exception) {
        e.printStackTrace()
        "Invalid Date" to "Invalid Time"
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var door by remember { mutableStateOf(false) }
    val memberViewModel: MemberViewModel = viewModel()
    val members by memberViewModel.members.observeAsState(emptyList())
    val gateLogViewModel: GateLogsViewModel = viewModel()
    val gateLogs by gateLogViewModel.gateLogs.observeAsState(emptyList())
    val count = members.size
    var gateStatus by remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }
    
    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
                        .padding(
                            horizontal = 0.04 * screenWidth
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
                                Icon(
                                    painter = painterResource(R.drawable.notification),
                                    contentDescription = "notification",
                                    Modifier.size(32.dp),
                                    tint = AccentColor
                                )
                                Text(
                                    text = "Home",
                                    color = TextColor,
                                    fontSize = 20.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    painter = painterResource(R.drawable.user),
                                    contentDescription = "account",
                                    Modifier.size(32.dp),
                                    tint = AccentColor
                                )
                            }
                            Spacer(modifier = Modifier.size(0.015 * screenHeight))
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(25.dp))
                                    .border(
                                        width = 1.2.dp,
                                        color = AccentColor,
                                        shape = RoundedCornerShape(25.dp)
                                    )
                                    .background(SecondaryColor)
                                    .padding(
                                        horizontal = 0.07 * screenWidth,
                                        vertical = 0.035 * screenWidth
                                    )
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
                                            color = TextColor,
                                            fontSize = 22.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Experience Engineering",
                                            color = TextColor,
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
                            Spacer(modifier = Modifier.size(0.022 * screenHeight))
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(SecondaryColor)
                                    .padding(
                                        horizontal = 0.05 * screenWidth,
                                        vertical = 0.05 * screenWidth
                                    )
                                    .clickable { door = !door }
                            ) {
                                Row{
                                    VerticalDivider(
                                        modifier = Modifier
                                            .height(30.dp)
                                            .padding(end = 8.dp)
                                            .clip(RoundedCornerShape(30)),
                                        color = AccentColor,
                                        thickness = 5.dp
                                    )
                                    Text(
                                        text = "Live Updates",
                                        color = TextColor,
                                        fontSize = 22.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.size(0.005 * screenHeight))
                                Text(
                                    text = "Society Meeting at 1:00 PM",
                                    color = TextColor,
                                    fontSize = 18.sp,
                                    fontFamily = latoFontFamily,
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.size(0.022 * screenHeight))
                            Text(
                                text = "Upcoming Events",
                                color = TextColor,
                                fontSize = 20.sp,
                                fontFamily = latoFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.035 * screenWidth
                                        )
                                        .weight(1f)
                                        .clickable {
                                            navController.navigate("member") {
                                                popUpTo("member") {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                ){
                                    Column(){
                                        Text(
                                            text = " Hackathon",
                                            color = Color.White,
                                            fontSize = 22.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(
                                            text = " August 21, 2025",
                                            color = Color(0xFFDADADA),
                                            fontSize = 17.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.03 * screenHeight))
                                    Text(
                                        modifier = Modifier.padding(bottom = 5.dp),
                                        text = "View Details",
                                        color = Color(0xFFDADADA),
                                        fontSize = 14.sp,
                                        fontFamily = latoFontFamily,
                                    )
                                }
                                Spacer(modifier = Modifier.size(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.035 * screenWidth
                                        )
                                        .weight(1f)
                                        .clickable {
                                            navController.navigate("member") {
                                                popUpTo("member") {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                ){
                                    Column(){
                                        Text(
                                            text = " Workshop",
                                            color = Color.White,
                                            fontSize = 22.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(
                                            text = " August 26, 2025",
                                            color = Color(0xFFDADADA),
                                            fontSize = 17.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.03 * screenHeight))
                                    Text(
                                        modifier = Modifier.padding(bottom = 5.dp),
                                        text = "View Details",
                                        color = Color(0xFFDADADA),
                                        fontSize = 16.sp,
                                        fontFamily = latoFontFamily,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.022 * screenHeight))
                        }
                        item {
                            Text(
                                text = "Lab Access Status",
                                color = TextColor,
                                fontSize = 20.sp,
                                fontFamily = latoFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(SecondaryColor)
                                    .padding(
                                        horizontal = 0.05 * screenWidth,
                                        vertical = 0.03 * screenWidth
                                    )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Switch(
                                        checked = gateStatus,
                                        onCheckedChange = {
                                            focusManager.clearFocus() // Hide the keyboard
                                            gateStatus = it
                                        },
                                        colors = SwitchColors(
                                            checkedThumbColor = Color.Black,
                                            uncheckedThumbColor = Color(0xFFB2B2B2),
                                            checkedTrackColor = AccentColor,
                                            uncheckedTrackColor = Color(0xFFB2B2B2).copy(alpha = 0.5f),
                                            checkedBorderColor = Color.Transparent,
                                            checkedIconColor = Color.Transparent,
                                            uncheckedBorderColor = Color.Transparent,
                                            uncheckedIconColor = Color.Transparent,
                                            disabledCheckedThumbColor = Color.Transparent,
                                            disabledCheckedTrackColor = Color.Transparent,
                                            disabledCheckedBorderColor = Color.Transparent,
                                            disabledCheckedIconColor = Color.Transparent,
                                            disabledUncheckedThumbColor = Color.Transparent,
                                            disabledUncheckedTrackColor = Color.Transparent,
                                            disabledUncheckedBorderColor = Color.Transparent,
                                            disabledUncheckedIconColor = Color.Transparent,
                                        )
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                    Text(
                                        text = if (gateStatus) "Gate Open" else "Gate Closed",
                                        color = TextColor,
                                        fontSize = 22.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "Last opened by Macle at 10:46 AM",
                                    color = TextColor,
                                    fontSize = 16.sp,
                                    fontFamily = latoFontFamily,
                                )
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.05 * screenWidth
                                        )
                                        .weight(1f)
                                        .clickable {
                                            navController.navigate("member") {
                                                popUpTo("member") {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                ){
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.member_d),
                                            contentDescription = "members",
                                            modifier = Modifier.size(40.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Society Members",
                                            color = PrimaryColor,
                                            fontSize = 20.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.size(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.05 * screenWidth
                                        )
                                        .weight(1f)
                                        .clickable {
//                                            navController.navigate("member") {
//                                                popUpTo("member") {
//                                                    inclusive = true
//                                                }
//                                            }
                                        }
                                ){
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.projects_d),
                                            contentDescription = "projects",
                                            modifier = Modifier.size(40.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Projects",
                                            color = PrimaryColor,
                                            fontSize = 20.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.05 * screenWidth
                                        )
                                        .weight(1f)
                                        .clickable {
                                            navController.navigate("logs") {
                                                popUpTo("member") {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                ){
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.security_d),
                                            contentDescription = "security",
                                            modifier = Modifier.size(40.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Assets and Lab",
                                            color = PrimaryColor,
                                            fontSize = 20.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.size(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.046 * screenWidth
                                        )
                                        .weight(1f)
                                        .clickable {
//                                            navController.navigate("member") {
//                                                popUpTo("member") {
//                                                    inclusive = true
//                                                }
//                                            }
                                        }
                                ){
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.res_d),
                                            contentDescription = "projects",
                                            modifier = Modifier.size(44.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Docs & Resources",
                                            color = PrimaryColor,
                                            fontSize = 20.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(0.12 * screenHeight))
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
                                Modifier.size(36.dp),
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
fun HomePagePreview() {
    HomePage(rememberNavController())
}