package com.example.robogyan.view

import android.annotation.SuppressLint
import android.content.res.Resources.Theme
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.R
import com.example.robogyan.ui.theme.CharcoalBlack
import com.example.robogyan.ui.theme.NavyBlue
import com.example.robogyan.ui.theme.ThemeBlue
import com.example.robogyan.ui.theme.latoFontFamily
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UseOfNonLambdaOffsetOverload")
@Composable
fun LoginPage(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var offsetY by remember { mutableStateOf(700.dp) } // Start 300dp below screen
    val animatedOffsetY by animateDpAsState(
        targetValue = offsetY,
        animationSpec = tween(durationMillis = 800)
    )

    LaunchedEffect(Unit) {
        offsetY = 0.dp
        //delay(1000)
//        navController.navigate("home") {
//            popUpTo("splash") { inclusive = true }
//        }
    }

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .padding()
                    .fillMaxSize()
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.bg1),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.height(0.05 * screenHeight))
                    Column(
                        modifier = Modifier
                            .offset(y = animatedOffsetY)
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(
                                color = Color(0xD2F3F3F3),
                                shape = RoundedCornerShape(
                                    topStart = 50.dp,
                                    topEnd = 50.dp
                                )
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(0.03 * screenHeight))
                        Text(
                            text = "Welcome Back!!",
                            color = ThemeBlue,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = latoFontFamily,
                        )
                        var email by remember { mutableStateOf("") }
                        var password by remember { mutableStateOf("") }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 0.05 * screenWidth,
                                    end = 0.05 * screenWidth,
                                    top = 0.03 * screenHeight
                                ),
                        ) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.07 * screenHeight),
                                shape = RoundedCornerShape(16.dp),
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.MailOutline,
                                        tint = Color(0xFF9A9A9A),
                                        contentDescription = "email"
                                    )
                                },
                                label = {
                                    Text(
                                        "Email",
                                        color = Color.Black
                                    )
                                },
                                value = email,
                                onValueChange = { email = it },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ThemeBlue,
                                    unfocusedBorderColor = Color(0x41000000),
                                    focusedTextColor = Color(0xFF000000),
                                )
                            )
                            Spacer(modifier = Modifier.height(0.015 * screenHeight))
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.07 * screenHeight),
                                shape = RoundedCornerShape(16.dp),
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Lock,
                                        tint = Color(0xFF9A9A9A),
                                        contentDescription = "email"
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        painter = painterResource(id = R.drawable.hide),
                                        tint = Color(0xFF000000),
                                        contentDescription = "email"
                                    )
                                },
                                label = {
                                    Text(
                                        "Password",
                                        color = Color.Black
                                    )
                                },
                                value = password,
                                onValueChange = { password = it },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ThemeBlue,
                                    unfocusedBorderColor = Color(0x41000000),
                                    focusedTextColor = Color(0xFF000000),
                                )
                            )
                            Spacer(modifier = Modifier.height(0.005 * screenHeight))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Forgot Password?",
                                color = Color(0xFF000000),
                                textAlign = TextAlign.End,
                                fontFamily = latoFontFamily,
                            )
                            Spacer(modifier = Modifier.height(0.03 * screenHeight))
                            FloatingActionButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(50)),
                                onClick = {
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                containerColor = ThemeBlue,
                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                                    color = Color.White,
                                    text = "Login",
                                    fontSize = 20.sp,
                                    fontFamily = latoFontFamily
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(0.4 * screenHeight))
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    LoginPage(rememberNavController())
}