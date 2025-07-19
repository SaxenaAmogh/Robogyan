package com.example.robogyan.view

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
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
import androidx.compose.ui.input.pointer.pointerInput
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.R
import com.example.robogyan.ui.theme.AccentColor
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.Black
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.viewmodel.GateLogsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SecurityPage(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current

//    var searchItem by remember { mutableStateOf("") }
    val haveAccess by remember { mutableStateOf(true) }
//    val memberViewModel: MemberViewModel = viewModel()
//    val members by memberViewModel.members.observeAsState(emptyList())
    val gateLogViewModel: GateLogsViewModel = viewModel()
    val gateLogs by gateLogViewModel.gateLogs.observeAsState(emptyList())
    var gateStatus by remember { mutableStateOf(true) }

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }

    Scaffold(
        content = {
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
                            horizontal = 0.035 * screenWidth
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier
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
                                    painter = painterResource(R.drawable.update),
                                    contentDescription = "notification",
                                    Modifier.size(32.dp),
                                    tint = AccentColor
                                )
                                Text(
                                    text = "Security & Assets",
                                    color = TextColor,
                                    fontSize = 20.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    painter = painterResource(R.drawable.user),
                                    contentDescription = "account",
                                    modifier = Modifier
                                        .clickable {
                                            navController.navigate("profile")
                                        }
                                        .size(32.dp),
                                    tint = AccentColor
                                )
                            }
                            Spacer(modifier = Modifier.size(0.015 * screenHeight))
                        }
                        item {
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(
                                        width = 0.5.dp,
                                        color = Color(0xFF2D2D2D),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .background(SecondaryColor)
                                    .padding(
                                        horizontal = 0.05 * screenWidth,
                                        vertical = 0.03 * screenWidth
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text(
                                        text = "Close Lab",
                                        color = TextColor,
                                        fontSize = 24.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (haveAccess){
                                        Switch(
                                            checked = gateStatus,
                                            onCheckedChange = {
                                                focusManager.clearFocus()
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
                                    }else{
                                        Switch(
                                            checked = gateStatus,
                                            onCheckedChange = {
                                                focusManager.clearFocus()
                                            },
                                            colors = SwitchColors(
                                                checkedThumbColor = Color.Black,
                                                uncheckedThumbColor = Color(0xFFB2B2B2),
                                                checkedTrackColor = Color.Gray,
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
                                    }
                                }
                                Spacer(modifier = Modifier.size(0.01 * screenHeight))
                                Text(
                                    text = "Last opened by Macle at 10:46 AM",
                                    color = TextColor,
                                    fontSize = 18.sp,
                                    fontFamily = latoFontFamily,
                                )
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                        }
                        //Logs data
                        item{
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(
                                        width = 0.5.dp,
                                        color = Color(0xFF2D2D2D),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .background(SecondaryColor)
                                    .padding(
                                        horizontal = 0.05 * screenWidth,
                                        vertical = 0.03 * screenWidth
                                    )
                            ){
                                Text(
                                    text = "Lab Access Logs",
                                    color = TextColor,
                                    fontSize = 20.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.size(0.01 * screenHeight))
                                if(gateLogs.isEmpty()){
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .size(20.dp),
                                        color = AccentColor
                                    )
                                }else {
                                    repeat(5) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    horizontal = 16.dp
                                                ),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Amogh Saxena",
                                                color = TextColor,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                            Text(
                                                text = "11:30 AM",
                                                color = SecondaryText,
                                                fontSize = 15.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(6.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(0.035 * screenHeight))
                        }
                        item {
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                color = Color(0xFF2D2D2D)
                            )
                            Spacer(modifier = Modifier.size(0.035 * screenHeight))
                            Text(
                                text = "Assets Inventory",
                                color = TextColor,
                                fontSize = 24.sp,
                                fontFamily = latoFontFamily,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate("addasset")
                                },
                                containerColor = SecondaryColor,
                                contentColor = Black,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Icon(
                                        Icons.Rounded.Add,
                                        contentDescription = "Add Asset",
                                        modifier = Modifier.size(24.dp),
                                        tint = AccentColor
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Text(
                                        text = "Add Component",
                                        color = AccentColor,
                                        fontSize = 16.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item{
                            Box(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 10.dp
                                    )
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(SecondaryColor)
                                    .pointerInput(Unit) {
                                    detectTapGestures(onTap = {
                                        navController.navigate("assetview")
                                    })
                                }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            12.dp
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row {
                                        Text(
                                            text = "05",
                                            color = AccentColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Spacer(modifier = Modifier.size(12.dp))
                                        Text(
                                            text = "LiPo Batteries",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                        contentDescription = "View Details",
                                        tint = AccentColor,
                                        modifier = Modifier
                                            .size(28.dp),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Box(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 10.dp
                                    )
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(SecondaryColor)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            12.dp
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row {
                                        Text(
                                            text = "12",
                                            color = AccentColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Spacer(modifier = Modifier.size(12.dp))
                                        Text(
                                            text = "ESP32 Boards",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                        contentDescription = "View Details",
                                        tint = AccentColor,
                                        modifier = Modifier
                                            .size(28.dp),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Box(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 10.dp
                                    )
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(SecondaryColor)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            12.dp
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row {
                                        Text(
                                            text = "15",
                                            color = AccentColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Spacer(modifier = Modifier.size(12.dp))
                                        Text(
                                            text = "Servo Motors",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                        contentDescription = "View Details",
                                        tint = AccentColor,
                                        modifier = Modifier
                                            .size(28.dp),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Box(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 10.dp
                                    )
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(SecondaryColor)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            12.dp
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row {
                                        Text(
                                            text = "08",
                                            color = AccentColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Spacer(modifier = Modifier.size(12.dp))
                                        Text(
                                            text = "Glue Gun Sticks",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                        contentDescription = "View Details",
                                        tint = AccentColor,
                                        modifier = Modifier
                                            .size(28.dp),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Box(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 10.dp
                                    )
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(SecondaryColor)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            12.dp
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row {
                                        Text(
                                            text = "28",
                                            color = AccentColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Spacer(modifier = Modifier.size(12.dp))
                                        Text(
                                            text = "Capacitors",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                        contentDescription = "View Details",
                                        tint = AccentColor,
                                        modifier = Modifier
                                            .size(28.dp),
                                    )
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
                                horizontal = 0.04 * screenWidth
                            )
                            .border(
                                width = 1.dp,
                                color = PrimaryColor,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .background(
                                shape = RoundedCornerShape(50.dp),
                                color = SecondaryColor
                            ),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = {
                                navController.navigate("home") {
                                    popUpTo("home") {
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
                                tint = AccentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("member"){
                                    popUpTo("security"){
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
                                tint = AccentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.security_d),
                                contentDescription = "explore",
                                Modifier.size(32.dp),
                                tint = AccentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("projects"){
                                    popUpTo("security"){
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.projects),
                                contentDescription = "cart_na",
                                Modifier.size(32.dp),
                                tint = AccentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("resources"){
                                    popUpTo("security"){
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
                                contentDescription = "resources",
                                Modifier.size(32.dp),
                                tint = AccentColor
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
    SecurityPage(rememberNavController())
}