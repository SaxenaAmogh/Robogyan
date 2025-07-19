package com.example.robogyan.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.robogyan.R
import com.example.robogyan.ui.theme.AccentColor
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.Black
import com.example.robogyan.ui.theme.GunmetalGray
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResourcesPage(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current
    val context = LocalView.current.context

    var searchItem by remember { mutableStateOf("") }
//    var selectedOption by remember { mutableIntStateOf(0) }
    var type by remember { mutableStateOf("Videos") }
    var expanded by remember { mutableStateOf(false) }
    val resourceType = listOf("Videos", "Articles", "RG Docs", "Others")

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }


    @Composable
    fun VideoCard(it: Int) {

        // Sample data for the Videos
        val author = listOf(
            "Robogyan",
            "Robogyan",
            "Robogyan",
            "Lucca's Lab",
            "Philipp Lackner"
        )
        val vidId = listOf(
            "pFs2D4jG9EA",
            "zhtq2LWRuQU",
            "hAqVLvpYXHw",
            "UuxBfKA3U5M",
            "_tqvevHzom0"
        )
        val vidLink = listOf(
            "https://www.youtube.com/watch?v=pFs2D4jG9EA",
            "https://www.youtube.com/watch?v=zhtq2LWRuQU",
            "https://www.youtube.com/watch?v=hAqVLvpYXHw",
            "https://www.youtube.com/watch?v=UuxBfKA3U5M",
            "https://www.youtube.com/watch?v=_tqvevHzom0"
        )
        val desc = listOf(
            "This video showcases the design process of the PCB (Printed Circuit Board) used in various robotics projects.",
            "This video demonstrates the construction and flight of a remote-controlled airplane designed by Robogyan.",
            "This video introduces the ADR project by Robogyan, highlighting its features and capabilities.",
            "This video that covers the basics of using the ESP32 microcontroller for IoT projects, including setup and programming.",
            "Android Dev Roadmap 2025 is a video that outlines the future trends and technologies in Android development, providing insights for developers."
        )
        val title = listOf(
            "RCB Designing",
            "RC plane (THE ROBOGYAN)",
            "ADR - THE ROBOGYAN (1)",
            "ESP32 101",
            "Android Dev Roadmap 2025"
        )

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
                    val intent = Intent(Intent.ACTION_VIEW, vidLink[it].toUri())
                    context.startActivity(intent)
                },
        ) {
            AsyncImage(
                model = "https://img.youtube.com/vi/${vidId[it]}/hqdefault.jpg",
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
                    text = title[it],
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    color = AccentColor,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = "By - ${author[it]}",
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp,
                    color = PrimaryColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = desc[it],
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = SecondaryText,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    @Composable
    fun ArticleCard(it: Int){

        // Sample data for Articles
        val articleNames = listOf(
            "What Is Machine Learning? Definition, Types, Applications, and Trends",
            "What Does Fabrication Mean In Engineering?",
            "What is the Future of AI in Robotics?",
            "ESP32 with MFRC522 RFID Reader/Writer",
            "Getting Started with ESP32",
        )
        val articleLinks = listOf(
            "https://www.spiceworks.com/tech/artificial-intelligence/articles/what-is-ml/",
            "https://lindstromgroup.com/uk/article/what-does-fabrication-mean-in-engineering/",
            "https://www.azorobotics.com/Article.aspx?ArticleID=700",
            "https://randomnerdtutorials.com/esp32-mfrc522-rfid-reader-arduino/",
            "https://lastminuteengineers.com/getting-started-with-esp32/"
        )
        val articleImages = listOf(
            "https://zd-brightspot.s3.us-east-1.amazonaws.com/wp-content/uploads/2022/04/04094802/4-12-1-e1715637495321.png",
            "https://lindstrom-sites.s3.eu-north-1.amazonaws.com/wp-content/uploads/sites/10/2022/05/daniel-smyth-njf81CyLZEQ-unsplash-scaled-1.jpg",
            "https://www.azorobotics.com/image-handler/ts/20240707083133/ri/950/src/images/Article_Images/ImageForArticle_700_17203986828779069.jpg",
            "https://i0.wp.com/randomnerdtutorials.com/wp-content/uploads/2024/11/ESP32-RFID-Reader-Writer-Tutorial.jpg?resize=1536%2C864&quality=100&strip=all&ssl=1",
            "https://lastminuteengineers.com/wp-content/uploads/featuredimages/Tutorial-for-Getting-Started-with-the-ESP32.webp"
        )

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
                    val intent = Intent(Intent.ACTION_VIEW,
                        articleLinks[it].toUri())
                    context.startActivity(intent)
                },
        ) {
            AsyncImage(
                model = articleImages[it],
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
                    text = articleNames[it],
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    maxLines = 2,
                    color = AccentColor,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.size(0.015 * screenHeight))
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
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.update),
                                    contentDescription = "notification",
                                    Modifier.size(32.dp),
                                    tint = AccentColor
                                )
                                Text(
                                    text = "Resources & Docs",
                                    color = TextColor,
                                    fontSize = 20.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    painter = painterResource(R.drawable.user),
                                    contentDescription = "account",
                                    modifier = Modifier
                                        .clickable {
                                            navController.navigate("profile")
                                        }
                                        .size(32.dp),
                                    tint = AccentColor
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
                                            cursorColor = AccentColor,
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
                                                tint = AccentColor
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
                                                    focusedBorderColor = AccentColor,
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
                                repeat(5){
                                    VideoCard(it)
                                    Spacer(modifier = Modifier.size(0.015 * screenHeight))
                                }
                            }else if (type == "Articles") {
                                repeat(5) {
                                    ArticleCard(it)
                                }
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
                            .padding(
                                horizontal = 0.04 * screenWidth
                            )
                            .border(
                                width = 1.dp,
                                color = PrimaryColor,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .background(
                                shape = RoundedCornerShape(50.dp),
                                color = SecondaryColor
                            ),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ){
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
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.home),
                                contentDescription = "home",
                                Modifier.size(32.dp),
                                tint = AccentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
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
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.member),
                                contentDescription = "cart_na",
                                Modifier.size(32.dp),
                                tint = AccentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
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
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.security),
                                contentDescription = "explore",
                                Modifier.size(32.dp),
                                tint = AccentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
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
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.projects),
                                contentDescription = "cart_na",
                                Modifier.size(32.dp),
                                tint = AccentColor
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.res_d),
                                contentDescription = "account",
                                Modifier.size(36.dp),
                                tint = AccentColor
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
    ResourcesPage(rememberNavController()) // Replace with actual NavController in real use
}