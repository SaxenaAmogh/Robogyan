package com.example.robogyan.view

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.R
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.ui.theme.PinkOne
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.GunmetalGray
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.PrimaryText
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily
import io.github.jan.supabase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProjectPage(navController: NavController){

    val isloggedin = SupabaseClientProvider.client.auth.currentSessionOrNull() != null
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current

    var searchItem by remember { mutableStateOf("") }
    var selectedOption by remember { mutableIntStateOf(0) }

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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
                        .padding(
                            horizontal = 0.035 * screenWidth
                        )
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                            })
                        }
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                            ){
                                Text(
                                    text = "Projects",
                                    color = Color.White,
                                    fontSize = 25.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 0.035 * screenWidth)
                                )
                                Icon(
                                    painter = painterResource(R.drawable.user),
                                    contentDescription = "account",
                                    modifier = Modifier
                                        .padding(end = 0.035 * screenWidth)
                                        .align(Alignment.CenterEnd)
                                        .clickable {
                                            if (isloggedin) {
                                                navController.navigate("profile")
                                            }else {
                                                Toast.makeText(context, "Login to view Profile", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        .size(32.dp),
                                    tint = if(isloggedin) Color.White else GunmetalGray
                                )
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item {

                            OutlinedTextField(
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = PrimaryText,
                                    unfocusedBorderColor = GunmetalGray,
                                    cursorColor = PinkOne,
                                    containerColor = SecondaryColor,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(35),
                                leadingIcon = {
                                    Icon(
                                        modifier = Modifier.size(22.dp),
                                        painter = painterResource(id = R.drawable.search),
                                        contentDescription = "search",
                                        tint = PinkOne
                                    )
                                },
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                ),
                                singleLine = true,
                                value = searchItem,
                                onValueChange = { searchItem = it },
                                placeholder = {
                                    Text(
                                        color = TextColor,
                                        text = "Search for projects",
                                        fontFamily = latoFontFamily,
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item {
                            if (!isloggedin){
                                Text(
                                    text = "To view full details, login first.",
                                    color = PrimaryText,
                                    fontSize = 20.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.size(0.02 * screenHeight))
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.05 * screenWidth
                                        )
                                        .weight(1f)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.ongoing),
                                                contentDescription = "members",
                                                modifier = Modifier.size(40.dp),
                                                tint = PinkOne
                                            )
                                            Spacer(modifier = Modifier.size(10.dp))
                                            Text(
                                                text = if(isloggedin) "5" else "?",
                                                color = PrimaryColor,
                                                fontSize = 30.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = latoFontFamily,
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Ongoing",
                                            color = PrimaryText,
                                            fontSize = 24.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.size(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = Color(0xFF2D2D2D),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .background(SecondaryColor)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.05 * screenWidth
                                        )
                                        .weight(1f)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.completed),
                                                contentDescription = "members",
                                                modifier = Modifier.size(40.dp),
                                                tint = PinkOne
                                            )
                                            Spacer(modifier = Modifier.size(10.dp))
                                            Text(
                                                text = "12",
                                                color = PrimaryColor,
                                                fontSize = 30.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = latoFontFamily,
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Completed",
                                            color = PrimaryText,
                                            fontSize = 24.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(
                                        width = 0.5.dp,
                                        color = Color(0xFF2D2D2D),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .background(SecondaryColor)
                                    .padding(
                                        horizontal = 0.012 * screenWidth,
                                        vertical = 0.05 * screenWidth
                                    )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                ) {
                                    Row {
                                        VerticalDivider(
                                            modifier = Modifier
                                                .height(28.dp)
                                                .padding(end = 8.dp)
                                                .clip(RoundedCornerShape(30)),
                                            color = PinkOne,
                                            thickness = 5.dp
                                        )
                                        Text(
                                            text = "Project Overview",
                                            color = PrimaryText,
                                            fontSize = 22.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = "Current status of projects. Click to view details.",
                                        color = SecondaryText,
                                        fontSize = 15.sp,
                                        fontFamily = latoFontFamily,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                    Column {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Text(
                                                text = "Project",
                                                color = SecondaryText,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.weight(0.45f)
                                            )
                                            Text(
                                                text = "Head",
                                                color = SecondaryText,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.weight(0.2f)
                                            )
                                            Text(
                                                text = "Status",
                                                color = SecondaryText,
                                                fontSize = 16.sp,
                                                textAlign = TextAlign.Center,
                                                fontFamily = latoFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.weight(0.3f)
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(5.dp))
                                        HorizontalDivider(
                                            color = Color(0xFF2D2D2D),
                                            thickness = 1.dp,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.size(6.dp))
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .pointerInput(Unit) {
                                                    detectTapGestures(onTap = {
                                                        if(isloggedin) {
                                                            navController.navigate("projectview")
                                                        }else{
                                                            Toast.makeText(context, "Login to view full details", Toast.LENGTH_SHORT).show()
                                                        }
                                                    })
                                                },
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
//                                            val originalText = "Website Redesign"
//                                            val displayText = if (originalText.length > 16) {
//                                                originalText.take(16) + "..."
//                                            } else {
//                                                originalText
//                                            }
                                            Text(
                                                text = "Website Redesign",
                                                color = PrimaryColor,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.weight(0.45f)
                                            )
                                            Text(
                                                text = "Amogh",
                                                color = SecondaryText,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier.weight(0.2f)
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .weight(0.3f)
                                                    .clip(RoundedCornerShape(16.dp))
                                                    .background(Color(0xFF5C9DE5))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "In Progress",
                                                    color = Color(0xFFC9E3FF),
                                                    fontSize = 15.sp,
                                                    fontFamily = latoFontFamily,
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(6.dp))
                                        HorizontalDivider(
                                            color = Color(0xFF2D2D2D),
                                            thickness = 1.dp,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.size(6.dp))
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Text(
                                                text = "Farewell 2025",
                                                color = PrimaryColor,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier.weight(0.45f)
                                            )
                                            Text(
                                                text = "Amogh",
                                                color = SecondaryText,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier.weight(0.2f)
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .weight(0.3f)
                                                    .clip(RoundedCornerShape(16.dp))
                                                    .background(Color(0xFF009688))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "Complete",
                                                    color = Color(0xFFC2FFF6),
                                                    fontSize = 15.sp,
                                                    fontFamily = latoFontFamily,
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(6.dp))
                                        HorizontalDivider(
                                            color = Color(0xFF2D2D2D),
                                            thickness = 1.dp,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.size(6.dp))
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Text(
                                                text = "Farewell Reel",
                                                color = PrimaryColor,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier.weight(0.45f)
                                            )
                                            Text(
                                                text = "Nikunj",
                                                color = SecondaryText,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier.weight(0.2f)
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .weight(0.3f)
                                                    .clip(RoundedCornerShape(16.dp))
                                                    .background(Color(0xFF5D5D5D))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "On Hold",
                                                    color = Color(0xFFCECECE),
                                                    fontSize = 15.sp,
                                                    fontFamily = latoFontFamily,
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(6.dp))
                                        HorizontalDivider(
                                            color = Color(0xFF2D2D2D),
                                            thickness = 1.dp,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.size(6.dp))
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Text(
                                                text = "Techkriti 2025",
                                                color = PrimaryColor,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier.weight(0.45f)
                                            )
                                            Text(
                                                text = "Macle",
                                                color = SecondaryText,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier.weight(0.2f)
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .weight(0.3f)
                                                    .clip(RoundedCornerShape(16.dp))
                                                    .background(Color(0xFF009688))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "Complete",
                                                    color = Color(0xFFC2FFF6),
                                                    fontSize = 15.sp,
                                                    fontFamily = latoFontFamily,
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(6.dp))
                                        HorizontalDivider(
                                            color = Color(0xFF2D2D2D),
                                            thickness = 1.dp,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.size(6.dp))
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Text(
                                                text = "Android Webinar",
                                                color = PrimaryColor,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier.weight(0.45f)
                                            )
                                            Text(
                                                text = "Dev",
                                                color = SecondaryText,
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier.weight(0.2f)
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .weight(0.3f)
                                                    .clip(RoundedCornerShape(16.dp))
                                                    .background(Color(0xFFC5453E))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "Abandoned",
                                                    color = Color(0xFFFFDFDE),
                                                    fontSize = 15.sp,
                                                    fontFamily = latoFontFamily,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                    }

                    // Bottom Navigation
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                            .fillMaxWidth()
                            .height(0.07 * screenHeight)
                            .padding(
                                horizontal = 0.035 * screenWidth
                            )
                            .background(
                                shape = RoundedCornerShape(40.dp),
                                color = SecondaryColor
                            ),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                navController.navigate("home") {
                                    popUpTo("home") {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.home),
                                contentDescription = "home",
                                Modifier.size(26.dp),
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("member") {
                                    popUpTo("projects") {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.member),
                                contentDescription = "cart_na",
                                Modifier.size(26.dp),
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("security") {
                                    popUpTo("projects") {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.security),
                                contentDescription = "explore",
                                Modifier.size(26.dp),
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
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
                                painter = painterResource(R.drawable.projects_d),
                                contentDescription = "cart_na",
                                Modifier.size(26.dp),
                                tint = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("resources") {
                                    popUpTo("projects") {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.res),
                                contentDescription = "resources",
                                Modifier.size(30.dp),
                                tint = Color.White
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
fun ProjectPagePreview() {
    ProjectPage(rememberNavController())
}
