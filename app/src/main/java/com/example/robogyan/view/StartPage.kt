package com.example.robogyan.view

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.R
import com.example.robogyan.ui.theme.AccentColor
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.latoFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UseOfNonLambdaOffsetOverload")
@Composable
fun StartPage(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var animate by remember { mutableStateOf(false)}
    val coroutineScope = rememberCoroutineScope()

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }

    var scale by remember { mutableFloatStateOf(1f) } // Start at 50% scale
    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = 1000) // 1 second animation
    )

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .padding()
                    .fillMaxSize()
                    .background(BackgroundColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                ){
                    Box(
                        modifier = Modifier
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bgtry),
                            contentDescription = "Landing Page Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 0.25 * screenHeight),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Welcome to Robogyan",
                                fontFamily = latoFontFamily,
                                color = AccentColor,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "Experience Engineering!",
                                fontFamily = latoFontFamily,
                                color = SecondaryText,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.W500,
                            )
                        }
                        Spacer(modifier = Modifier.height(0.015 * screenHeight))
                        Image(
                            painter = painterResource(id = R.drawable.plane),
                            contentDescription = "Login Image",
                            modifier = Modifier
                                .scale(animatedScale)
                                .fillMaxWidth()
                                .padding(horizontal = 0.1 * screenWidth)
                                .height(0.2 * screenHeight),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.height(0.015 * screenHeight))
                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 0.05 * screenWidth,
                                    end = 0.05 * screenWidth
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            FloatingActionButton(
                                onClick = {
                                    animate = true
                                    coroutineScope.launch {
                                        scale = 10f
                                        delay(1000)
                                        navController.navigate("login") {
                                            popUpTo("start") { inclusive = true }
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().size(if (animate) 0.dp else 60.dp),
                                containerColor =AccentColor,
                            ) {
                                Text(
                                    text = "Continue as Member",
                                    fontFamily = latoFontFamily,
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W500,
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            FloatingActionButton(
                                onClick = {
                                },
                                modifier = Modifier.fillMaxWidth().size(if (animate) 0.dp else 60.dp),
                                containerColor = Color(0xFFe9e5da),
                            ) {
                                Text(
                                    text = "Explore as Guest",
                                    fontFamily = latoFontFamily,
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W500,
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun StartPagePreview() {
    StartPage(rememberNavController())
}