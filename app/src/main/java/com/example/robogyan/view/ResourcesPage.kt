package com.example.robogyan.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.robogyan.R
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.GunmetalGray
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.PrimaryText
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.utils.SharedPrefManager
import com.example.robogyan.viewmodel.ResourceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResourcesPage(navController: NavController) {

    val context = LocalContext.current
    val isloggedin = SharedPrefManager.isLoggedIn(context)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current

    var searchItem by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Videos") }
    var expanded by remember { mutableStateOf(false) }
    val resourceType = listOf("Videos", "Articles", "RG Docs", "Others")

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }

    val resourcesViewModel: ResourceViewModel = viewModel()
    val resourcesData by resourcesViewModel.resourcesFlow.collectAsState()

    @Composable
    fun VideoCard() {
        val vidFilter = resourcesData
            .filter {
                it.type == "Video" && it.title.contains(searchItem, ignoreCase = true)
            }

        vidFilter.forEach { it ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 0.5.dp,
                        color = Color(0xFF2D2D2D),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(SecondaryColor)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, it.resource_url.toUri())
                        context.startActivity(intent)
                    },
            ) {
                AsyncImage(
                    model = it.image,
                    contentDescription = "YouTube Thumbnail",
                    error = painterResource(R.drawable.unav),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(
                            vertical = 10.dp,
                            horizontal = 14.dp
                        )
                ) {
                    Text(
                        text = it.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        color = PrimaryText,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    it.description?.let { it1 ->
                        Text(
                            text = it1,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = SecondaryText,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(0.015 * screenHeight))
        }
    }

    @Composable
    fun ArticleCard(){
        val articleFilter = resourcesData
            .filter {
                it.type == "Article" && it.title.contains(searchItem, ignoreCase = true)
            }

        articleFilter.forEach { it ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 0.5.dp,
                        color = Color(0xFF2D2D2D),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(SecondaryColor)
                    .clickable {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            it.resource_url.toUri()
                        )
                        context.startActivity(intent)
                    },
            ) {
                AsyncImage(
                    model = it.image,
                    contentDescription = "YouTube Thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(
                            vertical = 10.dp,
                            horizontal = 14.dp
                        )
                ) {
                    Text(
                        text = it.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        maxLines = 2,
                        color = PrimaryText,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.size(0.015 * screenHeight))
        }
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
                                    text = "Resources",
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
                        item{
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(
                                    modifier = Modifier.weight(0.9f)
                                ) {
                                    OutlinedTextField(
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            focusedBorderColor = PrimaryColor,
                                            unfocusedBorderColor = GunmetalGray,
                                            cursorColor = PrimaryText,
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
                                                tint = PrimaryText
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
                                                text = "Search for $type",
                                                fontFamily = latoFontFamily,
                                            )
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.width(0.01 * screenWidth))
                                Column(
                                    modifier = Modifier.weight(0.50f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(0.063 * screenHeight)
                                    ) {
                                        ExposedDropdownMenuBox(
                                            expanded = expanded,
                                            onExpandedChange = { expanded = it }
                                        ) {
                                            OutlinedTextField(
                                                value = type,
                                                onValueChange = { type = it },
                                                readOnly = true,
                                                modifier = Modifier
                                                    .menuAnchor()
                                                    .fillMaxWidth(),
                                                trailingIcon = {
                                                    Icon(
                                                        Icons.Default.ArrowDropDown,
                                                        contentDescription = "Dropdown"
                                                    )
                                                },
                                                shape = RoundedCornerShape(35),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedBorderColor = PrimaryText,
                                                    unfocusedBorderColor = GunmetalGray,
                                                    unfocusedTextColor = Color(0xFFFFFFFF),
                                                    focusedTextColor = Color(0xFFFFFFFF),
                                                    unfocusedContainerColor = Color(0x14ABABAB),
                                                    focusedContainerColor = Color(0x14ABABAB)
                                                )
                                            )

                                            ExposedDropdownMenu(
                                                expanded = expanded,
                                                onDismissRequest = { expanded = false },
                                                modifier = Modifier
                                                    .background(Color(0xFFFFFFFF)),
                                            ) {
                                                resourceType.forEach { item ->
                                                    DropdownMenuItem(
                                                        colors = MenuItemColors(
                                                            textColor = Color.Black,
                                                            leadingIconColor = Color.Transparent,
                                                            trailingIconColor = Color.Transparent,
                                                            disabledTextColor = Color.Transparent,
                                                            disabledLeadingIconColor = Color.Transparent,
                                                            disabledTrailingIconColor = Color.Transparent,
                                                        ),
                                                        modifier = Modifier
                                                            .background(Color.White),
                                                        text = {
                                                            Text(
                                                                item,
                                                                fontFamily = latoFontFamily,
                                                                modifier = Modifier
                                                                    .fillMaxWidth(),
                                                                textAlign = TextAlign.Center
                                                            )
                                                        },
                                                        onClick = {
                                                            type = item
                                                            focusManager.clearFocus()
                                                            expanded = false
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item{
                            if (type == "Videos"){
                                VideoCard()
                            }else if (type == "Articles") {
                                ArticleCard()
                            }
                            Spacer(modifier = Modifier.size(0.09 * screenHeight))
                        }
                    }

                    // Bottom Navigation
                    Row(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                            .align(Alignment.BottomCenter)
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
                                    popUpTo("resources") {
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
                                    popUpTo("resources") {
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
                            onClick = {
                                navController.navigate("projects") {
                                    popUpTo("resources") {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(45.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.projects),
                                contentDescription = "cart_na",
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
                                    color = Color(0xFF3872D9),
                                    shape = RoundedCornerShape(25.dp)
                                )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.res_d),
                                contentDescription = "account",
                                Modifier.size(30.dp),
                                tint = Color.Black
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
fun ResourcesPagePreview() {
    ResourcesPage(rememberNavController())
}