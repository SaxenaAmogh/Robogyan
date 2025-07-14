package com.example.robogyan.view

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.robogyan.R
import com.example.robogyan.ui.theme.AccentColor
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.Black
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily
import kotlinx.coroutines.delay

//@RequiresApi(Build.VERSION_CODES.O)
//fun convertToIST(utcDateTime: String): Pair<String, String> {
//    return try {
//        // Parse the UTC time
//        val utcFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSX")
//            .withZone(ZoneId.of("UTC"))
//        val instant = Instant.from(utcFormatter.parse(utcDateTime))
//
//        // Convert to IST
//        val istZone = ZoneId.of("Asia/Kolkata")
//        val istFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//            .withZone(istZone)
//        val istDateTime = istFormatter.format(instant)
//
//        // Split Date and Time
//        val (date, time) = istDateTime.split(" ")
//        date to time
//    } catch (e: Exception) {
//        e.printStackTrace()
//        "Invalid Date" to "Invalid Time"
//    }
//}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var door by remember { mutableStateOf(false) }
//    val memberViewModel: MemberViewModel = viewModel()
//    val members by memberViewModel.members.observeAsState(emptyList())
//    val gateLogViewModel: GateLogsViewModel = viewModel()
//    val gateLogs by gateLogViewModel.gateLogs.observeAsState(emptyList())
//    val count = members.size
    val gateStatus by remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current

    val pagerState = rememberPagerState(pageCount = {8})

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }

    val imageUrls = listOf(
        R.drawable.pic1,
        R.drawable.pic2,
        R.drawable.pic3,
        R.drawable.pic4,
        R.drawable.pic5,
        R.drawable.pic6,
        R.drawable.pic7,
        R.drawable.pic8,
    )

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
                                    painter = painterResource(R.drawable.update),
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
                            Row{
                                VerticalDivider(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .padding(horizontal = 8.dp)
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
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(SecondaryColor)
                                    .border(
                                        width = 0.5.dp,
                                        color = Color(0xFF2D2D2D),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(
                                        horizontal = 0.05 * screenWidth,
                                        vertical = 0.05 * screenWidth
                                    )
                                    .clickable { door = !door }
                            ) {
                                Spacer(modifier = Modifier.size(0.005 * screenHeight))
                                Text(
                                    text = "- Lab is currently open",
                                    color = TextColor,
                                    fontSize = 18.sp,
                                    fontFamily = latoFontFamily,
                                )
                                Spacer(modifier = Modifier.size(0.005 * screenHeight))
                                Text(
                                    text = "- Society Meeting at 1:00 PM",
                                    color = TextColor,
                                    fontSize = 18.sp,
                                    fontFamily = latoFontFamily,
                                )
                                Spacer(modifier = Modifier.size(0.01 * screenHeight))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Switch(
                                        checked = gateStatus,
                                        onCheckedChange = {
                                            focusManager.clearFocus() // Hide the keyboard
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
                        }
                        item {
                            Spacer(modifier = Modifier.size(0.022 * screenHeight))
                            Row{
                                VerticalDivider(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .padding(start = 8.dp, end = 8.dp)
                                        .clip(RoundedCornerShape(30)),
                                    color = AccentColor,
                                    thickness = 5.dp
                                )
                                Text(
                                    text = "RG Glimpses",
                                    color = TextColor,
                                    fontSize = 22.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
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
                            ) {
                                LaunchedEffect(Unit) {
                                    while (true) {
                                        delay(1500)
                                        val nextPage = (pagerState.currentPage + 1) % imageUrls.size
                                        pagerState.animateScrollToPage(nextPage)
                                        if (pagerState.currentPage==8){
                                            delay(1500)
                                            pagerState.scrollToPage(0)
                                        }
                                    }
                                }

                                HorizontalPager(
                                    state = pagerState,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(240.dp)
                                ) { page ->
                                    AsyncImage(
                                        model = imageUrls[page],
                                        contentDescription = "Image $page",
                                        error = painterResource(R.drawable.hide),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.022 * screenHeight))
                        }

                        item {
                            Row{
                                VerticalDivider(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .padding(start = 8.dp, end = 8.dp)
                                        .clip(RoundedCornerShape(30)),
                                    color = AccentColor,
                                    thickness = 5.dp
                                )
                                Text(
                                    text = "Upcoming Events",
                                    color = TextColor,
                                    fontSize = 22.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
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
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(SecondaryColor)
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
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
                                    Column{
                                        Text(
                                            text = " Hackathon",
                                            color = Color.White,
                                            fontSize = 22.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(
                                            text = " August 21, 2025",
                                            color = SecondaryText,
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
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(SecondaryColor)
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
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
                                    Column{
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
                            Row{
                                VerticalDivider(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .padding(start = 8.dp, end = 8.dp)
                                        .clip(RoundedCornerShape(30)),
                                    color = AccentColor,
                                    thickness = 5.dp
                                )
                                Text(
                                    text = "Quick Actions",
                                    color = TextColor,
                                    fontSize = 22.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
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
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.05 * screenWidth
                                        )
                                        .weight(1f)
                                        .clickable {
                                            navController.navigate("member")
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
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.05 * screenWidth
                                        )
                                        .weight(1f)
                                        .clickable {
                                            navController.navigate("projects")
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
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.05 * screenWidth
                                        )
                                        .weight(1f)
                                        .clickable {
                                            navController.navigate("security")
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
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.046 * screenWidth
                                        )
                                        .weight(1f)
                                        .clickable {
                                            navController.navigate("resources")
                                        }
                                ){
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.res_d),
                                            contentDescription = "resources",
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
                            onClick = {},
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
                                navController.navigate("member")
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
                                navController.navigate("security")
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
                                navController.navigate("projects")
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
                                navController.navigate("resources")
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.res),
                                contentDescription = "resources",
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