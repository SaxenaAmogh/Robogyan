package com.example.robogyan.view.secpages

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowLeft
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.PurpleOne
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.utils.SharedPrefManager
import com.example.robogyan.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChangePasswordPage(navController: NavController) {

    val context = LocalContext.current
    val isLoggedIn = SharedPrefManager.isLoggedIn(context)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var agree by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val authViewModel: AuthViewModel = viewModel()

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
                        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
                        .padding(
                            horizontal = 0.035 * screenWidth
                        )
                        .pointerInput(Unit){
                            detectTapGestures {
                                focusManager.clearFocus()
                            }
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .border(
                                    width = 3.dp,
                                    color = Color(0xFF3872D9),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                Icons.AutoMirrored.TwoTone.KeyboardArrowLeft,
                                contentDescription = "Arrow Icon",
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(32.dp)
                                    .align(Alignment.CenterStart)
                                    .clickable {
                                        navController.popBackStack()
                                    },
                                tint = Color(0xFF3872D9)
                            )
                        }
                        Text(
                            text = "Change Password",
                            color = TextColor,
                            fontSize = 22.sp,
                            fontFamily = latoFontFamily,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.height(0.022 * screenHeight))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "Old Password",
                            fontFamily = latoFontFamily,
                            color = PurpleOne,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.063 * screenHeight),
                            shape = RoundedCornerShape(size = 16.dp),
                            placeholder = {
                                Text(
                                    "xxxxxxxx",
                                    fontFamily = latoFontFamily,
                                    color = Color(0xFFB2B2B2),
                                )
                            },
                            value = oldPassword,
                            onValueChange = { oldPassword = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PurpleOne,
                                unfocusedBorderColor = Color(0x66ABABAB),
                                focusedTextColor = Color(0xFFFFFFFF),
                                unfocusedTextColor = Color(0xFFFFFFFF),
                                unfocusedContainerColor = Color(0x14ABABAB),
                                focusedContainerColor = Color(0x14ABABAB)
                            )
                        )
                        Spacer(modifier = Modifier.height(0.02 * screenHeight))
                        Text(
                            text = "New Password",
                            fontFamily = latoFontFamily,
                            color = PurpleOne,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.063 * screenHeight),
                            shape = RoundedCornerShape(size = 16.dp),
                            placeholder = {
                                Text(
                                    "xxxxxxxx",
                                    fontFamily = latoFontFamily,
                                    color = Color(0xFFB2B2B2),
                                )
                            },
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PurpleOne,
                                unfocusedBorderColor = Color(0x66ABABAB),
                                focusedTextColor = Color(0xFFFFFFFF),
                                unfocusedTextColor = Color(0xFFFFFFFF),
                                unfocusedContainerColor = Color(0x14ABABAB),
                                focusedContainerColor = Color(0x14ABABAB)
                            )
                        )
                        Spacer(modifier = Modifier.height(0.02 * screenHeight))
                        Text(
                            text = "Confirm New Password",
                            fontFamily = latoFontFamily,
                            color = PurpleOne,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.063 * screenHeight),
                            shape = RoundedCornerShape(size = 16.dp),
                            placeholder = {
                                Text(
                                    "xxxxxxxx",
                                    fontFamily = latoFontFamily,
                                    color = Color(0xFFB2B2B2),
                                )
                            },
                            value = confirmNewPassword,
                            onValueChange = { confirmNewPassword = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PurpleOne,
                                unfocusedBorderColor = Color(0x66ABABAB),
                                focusedTextColor = Color(0xFFFFFFFF),
                                unfocusedTextColor = Color(0xFFFFFFFF),
                                unfocusedContainerColor = Color(0x14ABABAB),
                                focusedContainerColor = Color(0x14ABABAB)
                            )
                        )
                        Spacer(modifier = Modifier.height(0.02 * screenHeight))
                        Text(
                            text = "* You are changing your password, remember it or you won't be able to login.",
                            fontFamily = latoFontFamily,
                            color = SecondaryText,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = agree,
                                onCheckedChange = { agree = it }
                            )
                            Text(
                                text = "I agree to the terms and conditions",
                                fontFamily = latoFontFamily,
                                color = TextColor,
                                fontSize = 16.sp,
                            )
                        }
                        Spacer(modifier = Modifier.height(0.02 * screenHeight))
                        FloatingActionButton(
                            onClick = {
                                if (agree && oldPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmNewPassword.isNotEmpty() && isLoggedIn) {
                                    if (newPassword == confirmNewPassword) {
                                        authViewModel.logout()
                                        scope.launch {
                                            authViewModel.changePassword(newPassword)
                                        }
                                        SharedPrefManager.clear(context)
                                        navController.navigate("start") {
                                            popUpTo(0)
                                            launchSingleTop = true
                                        }
                                    } else {
                                         Toast.makeText(context, "New passwords do not match", Toast.LENGTH_SHORT).show()
                                    }
                                } else if (!agree) {
                                     Toast.makeText(context, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show()
                                }else{
                                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            containerColor = PurpleOne,
                            elevation = FloatingActionButtonDefaults.elevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 0.dp,
                                focusedElevation = 0.dp,
                                hoveredElevation = 0.dp
                            )
                        ) {
                            Text(
                                text = "Update",
                                fontFamily = latoFontFamily,
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordPagePreview() {
    ChangePasswordPage(rememberNavController())
}