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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.AllMembers
import com.example.robogyan.data.local.entities.Inventory
import com.example.robogyan.data.local.entities.Projects
import com.example.robogyan.ui.theme.PinkOne
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.PrimaryText
import com.example.robogyan.ui.theme.PurpleOne
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily
import kotlinx.coroutines.flow.Flow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectViewPage(navController: NavController, projectId: Int){

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

    val projectFlow: Flow<Projects?> =
        AppDatabase.getDatabase(context).projectsDao().getProjectById(projectId)
    val project by projectFlow.collectAsState(initial = null)

    val allMemberFlow: Flow<List<AllMembers>> =
        AppDatabase.getDatabase(context).allMembersDao().getAllMembers()
    val members by allMemberFlow.collectAsState(initial = emptyList())

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
                            project?.let { it ->
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
                                    ) {
                                        Text(
                                            text = it.name,
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
                                                .background(
                                                    if (it.status == "In Progress") Color(0xFF5C9DE5)
                                                    else if (it.status == "Completed") Color(0xFF009688)
                                                    else if (it.status == "On Hold") Color(0xFF5D5D5D)
                                                    else Color(0xFFC5453E)
                                                )
                                                .padding(horizontal = 6.dp, vertical = 2.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = it.status,
                                                color =
                                                    if (it.status == "In Progress") Color(0xFFC9E3FF)
                                                    else if (it.status == "Completed") Color(0xFFC2FFF6)
                                                    else if (it.status == "On Hold") Color(0xFFCECECE)
                                                    else Color(0xFFFFDFDE),
                                                fontSize = 15.sp,
                                                fontFamily = latoFontFamily,
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
                                        ) {
                                            Text(
                                                text = "Project Description: ",
                                                color = SecondaryText,
                                                fontSize = 15.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                            Text(
                                                text = it.description,
                                                color = TextColor,
                                                fontSize = 18.sp,
                                                fontFamily = latoFontFamily,
                                                textAlign = TextAlign.Justify,
                                            )
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
                                                    text = "Project Head: ",
                                                    color = SecondaryText,
                                                    fontSize = 15.sp,
                                                    fontFamily = latoFontFamily,
                                                )
                                                for (member in members) {
                                                    if (member.id == it.project_head) {
                                                        Text(
                                                            text = member.name,
                                                            color = PrimaryText,
                                                            fontSize = 18.sp,
                                                            fontFamily = latoFontFamily,
                                                        )
                                                    }
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(80.dp))
                                            Column(
                                                modifier = Modifier.weight(0.5f),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                Text(
                                                    text = "Category: ",
                                                    color = SecondaryText,
                                                    fontSize = 16.sp,
                                                    fontFamily = latoFontFamily,
                                                )
                                                Text(
                                                    text = it.category,
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
                                                    text = it.start_date,
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
                                                    text = if (it.completion_date == null) "TBD" else it.completion_date,
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
                                                text = "${it.money_spent} INR",
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
                                                        val intent = Intent(
                                                            Intent.ACTION_VIEW,
                                                            it.github_link?.toUri()
                                                        )
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
                                                        val intent = Intent(
                                                            Intent.ACTION_VIEW,
                                                            it.pdf_link?.toUri()
                                                        )
                                                        context.startActivity(intent)
                                                    }
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Project Team: ",
                                                color = SecondaryText,
                                                fontSize = 15.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                            Text(
                                                text = it.team,
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
                                        ) {
                                            Text(
                                                text = "Hardware Used: ",
                                                color = SecondaryText,
                                                fontSize = 15.sp,
                                                fontFamily = latoFontFamily,
                                            )
                                            Text(
                                                text = if (it.components == null) "NA" else it.components,
                                                color = TextColor,
                                                fontSize = 18.sp,
                                                textAlign = TextAlign.Justify,
                                                fontFamily = latoFontFamily,
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
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
    ProjectViewPage(rememberNavController(), 0)
}