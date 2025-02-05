package com.example.robogyan.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.R
import com.example.robogyan.ui.theme.CharcoalBlack
import com.example.robogyan.ui.theme.latoFontFamily

@Composable
fun LoginPage(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var login by remember { mutableStateOf(true) }

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding()
                    .fillMaxSize()
                    .background(CharcoalBlack)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = innerPadding.calculateTopPadding() + 0.04 * screenHeight,
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                            start = 0.05 * screenWidth,
                            end = 0.05 * screenWidth,
                        )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "logo",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(0.01 * screenHeight))
                        Text(
                            text = "Welcome Back",
                            color = Color.White,
                            fontSize = 40.sp,
                            fontFamily = latoFontFamily,
                            lineHeight = (0.09 * screenWidth).value.sp
                        )
                        Spacer(modifier = Modifier.height(0.015 * screenHeight))
                        Text(
                            text = "Experience Engineering together. Sign in to your community.",
                            color = Color(0xA9FFFFFF),
                            fontSize = 18.sp,
                            fontFamily = latoFontFamily,
                        )
                    }
                    Spacer(modifier = Modifier.height(0.05 * screenHeight))
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(
                                color = Color(0xD2F3F3F3),
                                shape = RoundedCornerShape(
                                    topStart = 50.dp,
                                    topEnd = 50.dp
                                )
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    start = 0.05 * screenWidth,
                                    end = 0.05 * screenWidth,
                                    top = 0.035 * screenHeight
                                )
                                .height(0.07 * screenHeight)
                                .fillMaxWidth()
                                .background(
                                    color = Color(0x2D000000),
                                    shape = RoundedCornerShape(50)
                                ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Login",
                                color = Color(0xFF000000),
                                fontSize = 22.sp,
                                fontFamily = latoFontFamily,
                            )
                        }
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
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.MailOutline,
                                        tint = Color(0xFF000000),
                                        contentDescription = "email"
                                    )
                                },
                                placeholder = {
                                    Text(
                                        "Email Address",
                                        color = Color.Black
                                    )
                                },
                                value = email,
                                onValueChange = { email = it },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF000000),
                                    unfocusedBorderColor = Color(0x41000000),
                                    focusedTextColor = Color(0xFF000000),
                                )
                            )
                            Spacer(modifier = Modifier.height(0.015 * screenHeight))
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.07 * screenHeight),
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Lock,
                                        tint = Color(0xFF000000),
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
                                placeholder = {
                                    Text(
                                        "Password",
                                        color = Color.Black
                                    )
                                },
                                value = password,
                                onValueChange = { password = it },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF000000),
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
                                },
                                containerColor = Color(0xFF1B1B1B),
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