package com.example.robogyan.view.secpages

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.ui.theme.PinkOne
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.PrimaryText
import com.example.robogyan.ui.theme.PurpleOne
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectViewPage(navController: NavController){

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val context = LocalView.current.context

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
                                    color = PinkOne,
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
                                tint = PinkOne
                            )
                        }
                        Text(
                            text = "View Projects",
                            color = TextColor,
                            fontSize = 20.sp,
                            fontFamily = latoFontFamily,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.height(0.01 * screenHeight))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(0.015 * screenHeight))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(
                                        width = 0.5.dp,
                                        color = Color(0xFF2D2D2D),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .background(SecondaryColor),
                            ) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ){
                                    Text(
                                        text = "Project Reboot",
                                        color = PinkOne,
                                        fontSize = 32.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(Color(0xFF5C9DE5))
                                            .padding(horizontal = 6.dp, vertical = 2.dp),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            text = "In Progress",
                                            color = Color(0xFFC9E3FF),
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                    ){
                                        Text(
                                            text = "Project Description: ",
                                            color = SecondaryText,
                                            fontSize = 15.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                        Text(
                                            text = "Project Reboot is a revamp of Robogyan's website and development of a new mobile application. The project aims to enhance website's outreach and add proper management capabilities of various society issues via the application. ",
                                            color = TextColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                            textAlign = TextAlign.Justify,
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ){
                                        Column(
                                            modifier = Modifier.weight(0.5f),
                                            horizontalAlignment = Alignment.Start
                                        ){
                                            Text(
                                                text = "Project Head: ",
                                                color = SecondaryText,
                                                fontSize = 15.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                            Text(
                                                text = "Amogh Saxena",
                                                color = PrimaryText,
                                                fontSize = 18.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(80.dp))
                                        Column(
                                            modifier = Modifier.weight(0.5f),
                                            horizontalAlignment = Alignment.Start
                                        ){
                                            Text(
                                                text = "Category: ",
                                                color = SecondaryText,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                            Text(
                                                text = "Software",
                                                color = PrimaryText,
                                                fontSize = 19.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(0.5f),
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                text = "Start Date: ",
                                                color = SecondaryText,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier
                                            )
                                            Text(
                                                text = "27-06-2025",
                                                color = PurpleOne,
                                                fontSize = 19.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(80.dp))
                                        Column(
                                            modifier = Modifier.weight(0.5f),
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                text = "End Date: ",
                                                color = SecondaryText,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                            Text(
                                                text = "TBD",
                                                color = PurpleOne,
                                                fontSize = 19.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Money Spent: ",
                                            color = SecondaryText,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "0.00 INR",
                                            color = PinkOne,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Github Repo: ",
                                            color = SecondaryText,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Github Link",
                                            color = Color(0xFF5C9DE5),
                                            fontSize = 19.sp,
                                            fontFamily = latoFontFamily,
                                            modifier = Modifier
                                                .clickable {
                                                    val intent = Intent(Intent.ACTION_VIEW,"https://github.com/robogyan-adgitm/robogyan-website".toUri())
                                                    context.startActivity(intent)
                                                }
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Project Doc/Report: ",
                                            color = SecondaryText,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "PDF Link",
                                            color = Color(0xFF5C9DE5),
                                            fontSize = 19.sp,
                                            fontFamily = latoFontFamily,
                                            modifier = Modifier
                                                .clickable {
                                                    val intent = Intent(Intent.ACTION_VIEW,"https://drive.google.com/drive/folders/16nGAgqs1SowMsRSaIZeMIdvoNpfJm5AM".toUri())
                                                    context.startActivity(intent)
                                                }
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ){
                                        Text(
                                            text = "Project Team: ",
                                            color = SecondaryText,
                                            fontSize = 15.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                        Text(
                                            text = "Amogh, Nikunj, Dev, Krish, Achintya, Avanya, Nikhil, Nirmaan, Shristi, Shourya, Vishv, Manavi",
                                            color = TextColor,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Justify,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ){
                                        Text(
                                            text = "Hardware Used: ",
                                            color = SecondaryText,
                                            fontSize = 15.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                        Text(
                                            text = "NA",
                                            color = TextColor,
                                            fontSize = 18.sp,
                                            textAlign = TextAlign.Justify,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                            Spacer(modifier = Modifier.height(0.02 * screenHeight))
                        }
                        item {
                            FloatingActionButton(
                                onClick = {
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 0.035 * screenWidth),
                                containerColor = PinkOne,
                                elevation = FloatingActionButtonDefaults.elevation(
                                    defaultElevation = 0.dp,
                                    pressedElevation = 0.dp,
                                    focusedElevation = 0.dp,
                                    hoveredElevation = 0.dp
                                )
                            ) {
                                Text(
                                    text = "Update Project",
                                    fontFamily = latoFontFamily,
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
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
fun ProjectViewPagePreview() {
    ProjectViewPage(rememberNavController())
}