package com.example.robogyan.view.secpages

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowLeft
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.ui.theme.AccentColor
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddAssetPage(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current

    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var availableQuantity by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

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
                                    width = 0.7.dp,
                                    color = AccentColor,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                Icons.AutoMirrored.TwoTone.KeyboardArrowLeft,
                                contentDescription = "Arrow Icon",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(32.dp)
                                    .align(Alignment.CenterStart)
                                    .clickable {
                                        navController.popBackStack()
                                    },
                                tint = AccentColor
                            )
                        }
                        Text(
                            text = "Add Component",
                            color = TextColor,
                            fontSize = 20.sp,
                            fontFamily = latoFontFamily,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.height(0.01 * screenHeight))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "Component Name *",
                            fontFamily = latoFontFamily,
                            color = AccentColor,
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
                                    "Name",
                                    fontFamily = latoFontFamily,
                                    color = Color(0xFFB2B2B2),
                                )
                            },
                            value = name,
                            onValueChange = { name = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AccentColor,
                                unfocusedBorderColor = Color(0x66ABABAB),
                                focusedTextColor = Color(0xFFFFFFFF),
                                unfocusedTextColor = Color(0xFFFFFFFF),
                                unfocusedContainerColor = Color(0x14ABABAB),
                                focusedContainerColor = Color(0x14ABABAB)
                            )
                        )
                        Spacer(modifier = Modifier.height(0.02 * screenHeight))
                        Text(
                            text = "Component Type *",
                            fontFamily = latoFontFamily,
                            color = AccentColor,
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
                                    "Type",
                                    fontFamily = latoFontFamily,
                                    color = Color(0xFFB2B2B2),
                                )
                            },
                            value = type,
                            onValueChange = { type = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AccentColor,
                                unfocusedBorderColor = Color(0x66ABABAB),
                                focusedTextColor = Color(0xFFFFFFFF),
                                unfocusedTextColor = Color(0xFFFFFFFF),
                                unfocusedContainerColor = Color(0x14ABABAB),
                                focusedContainerColor = Color(0x14ABABAB)
                            )
                        )
                        Spacer(modifier = Modifier.height(0.02 * screenHeight))
                        Text(
                            text = "Component Desctiption *",
                            fontFamily = latoFontFamily,
                            color = AccentColor,
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
                                    "Description",
                                    fontFamily = latoFontFamily,
                                    color = Color(0xFFB2B2B2),
                                )
                            },
                            value = description,
                            onValueChange = { description = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AccentColor,
                                unfocusedBorderColor = Color(0x66ABABAB),
                                focusedTextColor = Color(0xFFFFFFFF),
                                unfocusedTextColor = Color(0xFFFFFFFF),
                                unfocusedContainerColor = Color(0x14ABABAB),
                                focusedContainerColor = Color(0x14ABABAB)
                            )
                        )
                        Spacer(modifier = Modifier.height(0.02 * screenHeight))
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Column(
                                modifier = Modifier
                                    .weight(0.5f)
                            ) {
                                Text(
                                    text = "Quantity *",
                                    fontFamily = latoFontFamily,
                                    color = AccentColor,
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
                                            "Enter Quantity",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    ),
                                    value = quantity,
                                    onValueChange = { quantity = it },
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = AccentColor,
                                        unfocusedBorderColor = Color(0x66ABABAB),
                                        focusedTextColor = Color(0xFFFFFFFF),
                                        unfocusedTextColor = Color(0xFFFFFFFF),
                                        unfocusedContainerColor = Color(0x14ABABAB),
                                        focusedContainerColor = Color(0x14ABABAB)
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(
                                modifier = Modifier
                                    .weight(0.5f)
                            ) {
                                Text(
                                    text = "Availabe *",
                                    fontFamily = latoFontFamily,
                                    color = AccentColor,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .height(0.063 * screenHeight),
                                    shape = RoundedCornerShape(size = 16.dp),
                                    placeholder = {
                                        Text(
                                            "Enter Available",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    ),
                                    value = availableQuantity,
                                    onValueChange = { availableQuantity = it },
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = AccentColor,
                                        unfocusedBorderColor = Color(0x66ABABAB),
                                        focusedTextColor = Color(0xFFFFFFFF),
                                        unfocusedTextColor = Color(0xFFFFFFFF),
                                        unfocusedContainerColor = Color(0x14ABABAB),
                                        focusedContainerColor = Color(0x14ABABAB)
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(0.02 * screenHeight))
                        Text(
                            text = "Notes *",
                            fontFamily = latoFontFamily,
                            color = AccentColor,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.15 * screenHeight),
                            shape = RoundedCornerShape(size = 16.dp),
                            placeholder = {
                                Text(
                                    "Additional Notes",
                                    fontFamily = latoFontFamily,
                                    color = Color(0xFFB2B2B2),
                                )
                            },
                            value = notes,
                            onValueChange = { notes = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AccentColor,
                                unfocusedBorderColor = Color(0x66ABABAB),
                                focusedTextColor = Color(0xFFFFFFFF),
                                unfocusedTextColor = Color(0xFFFFFFFF),
                                unfocusedContainerColor = Color(0x14ABABAB),
                                focusedContainerColor = Color(0x14ABABAB)
                            )
                        )
                        Spacer(modifier = Modifier.height(0.02 * screenHeight))
                        FloatingActionButton(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth(),
                            containerColor = AccentColor,
                            elevation = FloatingActionButtonDefaults.elevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 0.dp,
                                focusedElevation = 0.dp,
                                hoveredElevation = 0.dp
                            )
                        ) {
                            Text(
                                text = "Save",
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
fun AddAssetPagePreview() {
    AddAssetPage(rememberNavController())
}