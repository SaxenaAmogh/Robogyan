package com.example.robogyan.view

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.ui.theme.AccentColor
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.viewmodel.AuthState
import com.example.robogyan.viewmodel.AuthViewModel
import com.example.robogyan.viewmodel.UserViewModel
import io.github.jan.supabase.auth.auth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfilePage(navController: NavController){

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }
//    val authViewModel: AuthViewModel = viewModel()
//    val userViewModel: UserViewModel = viewModel()
//    val currentUserId = remember { SupabaseClientProvider.client.auth.currentUserOrNull()?.id ?: "" }
//    val authState by authViewModel.authState.collectAsState()
//    val userData by userViewModel.getMemberData(currentUserId).collectAsState(initial = null)

//    LaunchedEffect(authState) {
//        when (authState) {
//            is AuthState.Idle -> {
//                // User is logged out, navigate back to login screen
//                // You'll need NavController here, as per our previous discussion
//                // val navController = (LocalContext.current as Activity).findNavController(R.id.nav_host_fragment_container)
////                navController.navigate("login") { popUpTo("home") { inclusive = true } }
//                Log.d("@@logout", "User logged out, navigate to login.")
//            }
//            is AuthState.Error -> {
//                println("Logout Error: ${(authState as AuthState.Error).message}")
//            }
//            AuthState.LoggedIn, AuthState.Loading -> {
//                Log.d("@@Login", userData.toString())
//            }
//        }
//    }


    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures {
                                focusManager.clearFocus()
                            }
                        }
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        item {
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = 50.dp,
                                            bottomEnd = 50.dp
                                        )
                                    )
                                    .background(BackgroundColor)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.me),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .alpha(0.65f)
                                        .fillMaxWidth()
                                        .height(0.38 * screenHeight)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 0.035 * screenWidth)
                                        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .border(
                                                width = 3.dp,
                                                color = AccentColor,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.TwoTone.KeyboardArrowLeft,
                                            contentDescription = "Arrow Icon",
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .size(34.dp)
                                                .align(Alignment.CenterStart)
                                                .clickable {
                                                    navController.popBackStack()
                                                },
                                            tint = AccentColor
                                        )
                                    }
//                                    Text(
//                                        text = "User Profile",
//                                        color = Color.Black,
//                                        fontSize = 20.sp,
//                                        fontFamily = latoFontFamily,
//                                        fontWeight = FontWeight.Bold,
//                                        modifier = Modifier
//                                            .align(Alignment.Center)
//                                    )
                                }
                                Spacer(modifier = Modifier.height(0.04 * screenHeight))
                                Column(
                                    modifier = Modifier
                                        .padding(bottom = 20.dp)
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Text(
                                        text = "Amogh Saxena",
                                        color = Color.White,
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = latoFontFamily,
                                        modifier = Modifier
                                    )
                                    Text(
                                        text = "Software Lead",
                                        color = Color.White,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = latoFontFamily,
                                    )
                                    Text(
                                        text = "2024-2025",
                                        color = Color(0xFF9F9F9F),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = latoFontFamily,
                                        modifier = Modifier
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.05 * screenHeight))
                            }
                            Spacer(modifier = Modifier.height(0.04 * screenHeight))
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 0.035 * screenWidth)
                            )  {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .weight(1f)
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable {
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
                                            modifier = Modifier.size(38.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Edit Members",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .weight(1f)
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable {
                                        }
                                ){
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.update),
                                            contentDescription = "members",
                                            modifier = Modifier.size(38.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Add New Update",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(0.01 * screenHeight))
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 0.035 * screenWidth)
                            )  {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .weight(1f)
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable {
                                        }
                                ){
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.open),
                                            contentDescription = "members",
                                            modifier = Modifier.size(38.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Open Lab",
                                            color = PrimaryColor,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .weight(1f)
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable {
                                        }
                                ){
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.component),
                                            contentDescription = "members",
                                            modifier = Modifier.size(38.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Edit Assets",
                                            color = PrimaryColor,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .weight(1f)
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable {
                                        }
                                ){
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.projects_d),
                                            contentDescription = "members",
                                            modifier = Modifier.size(38.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Add Project",
                                            color = PrimaryColor,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(0.04 * screenHeight))
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 0.035 * screenWidth)
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(
                                        width = 0.5.dp,
                                        color = Color(0xFF2D2D2D),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .background(SecondaryColor)
                                    .padding(
                                        horizontal = 0.02 * screenWidth,
                                        vertical = 0.03 * screenWidth
                                    )
                            ){
                                Spacer(modifier = Modifier.height(0.01 * screenHeight))
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 0.025 * screenWidth)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.edit),
                                            contentDescription = "members",
                                            modifier = Modifier.size(34.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Edit Profile",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                                        contentDescription = "Arrow Icon",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable {},
                                        tint = SecondaryText
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.012 * screenHeight))
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 0.025 * screenWidth)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.password),
                                            contentDescription = "members",
                                            modifier = Modifier.size(34.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Change Password",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                                        contentDescription = "Arrow Icon",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable {},
                                        tint = SecondaryText
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.012 * screenHeight))
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 0.025 * screenWidth)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.res_d),
                                            contentDescription = "members",
                                            modifier = Modifier.size(34.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Add Resources",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                                        contentDescription = "Arrow Icon",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable {},
                                        tint = SecondaryText
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.012 * screenHeight))
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 0.025 * screenWidth)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.review),
                                            contentDescription = "members",
                                            modifier = Modifier.size(34.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Send Feedback",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                                        contentDescription = "Arrow Icon",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable {},
                                        tint = SecondaryText
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.012 * screenHeight))
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 0.025 * screenWidth)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .clickable {
//                                                authViewModel.logout()
                                                navController.navigate("login")
                                                navController.popBackStack()
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            painter = painterResource(R.drawable.logout),
                                            contentDescription = "members",
                                            modifier = Modifier.size(34.dp),
                                            tint = AccentColor
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Logout",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                                        contentDescription = "Arrow Icon",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable {},
                                        tint = SecondaryText
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.01 * screenHeight))
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview(){
    ProfilePage(rememberNavController())
}