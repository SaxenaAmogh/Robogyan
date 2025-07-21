package com.example.robogyan.view

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.GunmetalGray
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.PrimaryText
import com.example.robogyan.ui.theme.PurpleOne
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.viewmodel.AuthState
import com.example.robogyan.viewmodel.AuthViewModel
import com.example.robogyan.viewmodel.MemberViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UseOfNonLambdaOffsetOverload")
@Composable
fun LoginPage(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authViewModel: AuthViewModel = viewModel()
    val memberViewModel: MemberViewModel = viewModel()
    val onLoginSuccess : () -> Unit = {
        memberViewModel.fetchMembers()
    }
    val onNavigateToHome : () -> Unit = {
        navController.navigate("home"){
            popUpTo("login"){
                inclusive = true
            }
        }
    }
    val authState by authViewModel.authState.collectAsState()

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.LoggedIn -> {
                onLoginSuccess()
                onNavigateToHome()
                authViewModel.resetAuthState()
            }
            is AuthState.Error -> {
                println("Login Error: ${(authState as AuthState.Error).message}")
            }
            AuthState.Idle, AuthState.Loading -> { /* Do nothing */ }
        }
    }

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
                        .pointerInput(Unit){
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                            })
                        }
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
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                    {
                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 0.05 * screenWidth,
                                    end = 0.05 * screenWidth
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = "Welcome Back!",
                                fontFamily = latoFontFamily,
                                color = PrimaryText,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(0.025 * screenHeight))
                            Column {
                                Text(
                                    text = "Email Address",
                                    color = PrimaryText,
                                    fontSize = 16.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                                OutlinedTextField(
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = PrimaryColor,
                                        unfocusedBorderColor = GunmetalGray,
                                        cursorColor = PurpleOne,
                                        containerColor = SecondaryColor,
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(25),
                                    leadingIcon = {
                                        Icon(
                                            Icons.Rounded.MailOutline,
                                            modifier = Modifier.size(28.dp),
                                            contentDescription = "search",
                                            tint = PurpleOne
                                        )
                                    },
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            focusManager.clearFocus()
                                        }
                                    ),
                                    singleLine = true,
                                    value = email,
                                    onValueChange = { email = it },
                                    placeholder = {
                                        Text(
                                            color = Color.Gray,
                                            text = "xyz@gmail.com",
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(0.01 * screenHeight))
                            Column {
                                Text(
                                    text = "Password",
                                    color = PrimaryText,
                                    fontSize = 16.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                                OutlinedTextField(
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = PrimaryColor,
                                        unfocusedBorderColor = GunmetalGray,
                                        cursorColor = PurpleOne,
                                        containerColor = SecondaryColor,
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(25),
                                    leadingIcon = {
                                        Icon(
                                            Icons.Rounded.Lock,
                                            modifier = Modifier.size(28.dp),
                                            contentDescription = "search",
                                            tint = PurpleOne
                                        )
                                    },
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            focusManager.clearFocus()
                                        }
                                    ),
                                    singleLine = true,
                                    value = password,
                                    onValueChange = { password = it },
                                    placeholder = {
                                        Text(
                                            color = Color.Gray,
                                            text = "XXXXXXX",
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(0.04 * screenHeight))
                            FloatingActionButton(
                                onClick = {
                                    if (email.isBlank() || password.isBlank()){
                                        Toast.makeText(context, "Enter all values.", Toast.LENGTH_SHORT).show()
                                    }
                                    authViewModel.loginWithEmailAndPassword(email, password)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                containerColor =PurpleOne,
                            ) {
                                if (authState is AuthState.Loading) {
                                    CircularProgressIndicator(color = PrimaryColor, modifier = Modifier.size(24.dp))
                                } else {
                                    Text(
                                        text = "Login",
                                        fontFamily = latoFontFamily,
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.W500,
                                    )
                                }
                            }
                            LaunchedEffect(authState) {
                                if (authState is AuthState.Error) {
                                    Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
                                }
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