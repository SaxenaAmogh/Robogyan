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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.example.robogyan.ui.theme.GunmetalGray
import com.example.robogyan.ui.theme.NavBar
import com.example.robogyan.ui.theme.PeachOne
import com.example.robogyan.ui.theme.PinkOne
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.PrimaryText
import com.example.robogyan.ui.theme.PurpleOne
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.YellowOne
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
        windowInsetsController.isAppearanceLightStatusBars = true
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
                ){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter)
                    ){
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                            ){
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
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
                                            .height(0.37 * screenHeight)
                                            .clip(
                                                RoundedCornerShape(
                                                    bottomEnd = 25.dp,
                                                    bottomStart = 25.dp
                                                )
                                            )
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
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 0.035 * screenWidth
                                    )
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Welcome to Robogyan",
                                    fontFamily = latoFontFamily,
                                    color = PrimaryText,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    text = "Experience Engineering!",
                                    fontFamily = latoFontFamily,
                                    color = SecondaryText,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W500,
                                )
                            }
                            Spacer(modifier = Modifier.size(0.022 * screenHeight))

                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 0.035 * screenWidth
                                    )
                            ){
//                                VerticalDivider(
//                                    modifier = Modifier
//                                        .height(30.dp)
//                                        .padding(horizontal = 8.dp)
//                                        .clip(RoundedCornerShape(30)),
//                                    color = AccentColor,
//                                    thickness = 5.dp
//                                )
                                Text(
                                    text = "Live Updates",
                                    color = TextColor,
                                    fontSize = 24.sp,
                                    fontFamily = latoFontFamily,
                                    modifier = Modifier.padding(start = 8.dp),
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Column(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 0.035 * screenWidth
                                    )
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(20))
                                    .background(SecondaryColor)
                                    .border(
                                        width = 0.5.dp,
                                        color = Color(0xFF2D2D2D),
                                        shape = RoundedCornerShape(20)
                                    )
                                    .padding(
                                        horizontal = 0.05 * screenWidth,
                                        vertical = 0.05 * screenWidth
                                    )
                                    .clickable { door = !door }
                            ) {
                                Spacer(modifier = Modifier.size(0.005 * screenHeight))
                                Text(
                                    text = "Lab is currently open",
                                    color = TextColor,
                                    fontSize = 18.sp,
                                    fontFamily = latoFontFamily,
                                )
                                Spacer(modifier = Modifier.size(0.005 * screenHeight))
                                Text(
                                    text = "Society Meeting at 1:00 PM",
                                    color = TextColor,
                                    fontSize = 18.sp,
                                    fontFamily = latoFontFamily,
                                )
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
                                            checkedTrackColor = Color(0xFFFED7AD),
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
                            Row(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 0.035 * screenWidth
                                    )
                            ){
//                                VerticalDivider(
//                                    modifier = Modifier
//                                        .height(30.dp)
//                                        .padding(start = 8.dp, end = 8.dp)
//                                        .clip(RoundedCornerShape(30)),
//                                    color = AccentColor,
//                                    thickness = 5.dp
//                                )
                                Text(
                                    text = "Upcoming Events",
                                    color = TextColor,
                                    fontSize = 24.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Row(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 0.035 * screenWidth
                                    )
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .background(SecondaryColor)
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(20)
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
                                            color = Color(0xFFFED7AD),
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
                                Spacer(modifier = Modifier.size(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .background(SecondaryColor)
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(20)
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
                                            color = Color(0xFFFED7AD),
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
                            Row(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 0.035 * screenWidth
                                    )
                            ){
//                                VerticalDivider(
//                                    modifier = Modifier
//                                        .height(30.dp)
//                                        .padding(start = 8.dp, end = 8.dp)
//                                        .clip(RoundedCornerShape(30)),
//                                    color = AccentColor,
//                                    thickness = 5.dp
//                                )
                                Text(
                                    text = "Quick Actions",
                                    color = TextColor,
                                    fontSize = 24.sp,
                                    fontFamily = latoFontFamily,
                                    modifier = Modifier.padding(start = 8.dp),
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            Row(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 0.035 * screenWidth
                                    )
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .background(PurpleOne)
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
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Members",
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.size(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .background(PeachOne)
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
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Projects",
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(0.01 * screenHeight))
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 0.035 * screenWidth
                                    )
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .background(YellowOne)
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
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Assets and Lab",
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.size(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .background(PinkOne)
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
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Docs & Resources",
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
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
                            .height(0.07 * screenHeight)
                            .padding(
                                horizontal = 0.065 * screenWidth
                            )
                            .background(
                                shape = RoundedCornerShape(40.dp),
                                color = NavBar
                            ),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .clip(RoundedCornerShape(25.dp))
                                .size(45.dp)
                                .background(
                                    color = Color(0xFFF5D867),
                                    shape = RoundedCornerShape(50)
                                )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.home_d),
                                contentDescription = "home",
                                Modifier.size(24.dp),
                                tint = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("member")
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.member),
                                contentDescription = "cart_na",
                                Modifier.size(26.dp),
                                tint = PrimaryColor
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("security")
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.security),
                                contentDescription = "explore",
                                Modifier.size(26.dp),
                                tint = PrimaryColor
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("projects")
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.projects),
                                contentDescription = "cart_na",
                                Modifier.size(26.dp),
                                tint = PrimaryColor
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("resources")
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.res),
                                contentDescription = "resources",
                                Modifier.size(30.dp),
                                tint = PrimaryColor
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