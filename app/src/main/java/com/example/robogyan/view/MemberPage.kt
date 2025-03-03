package com.example.robogyan.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.robogyan.R
import com.example.robogyan.ui.theme.Black
import com.example.robogyan.ui.theme.CharcoalBlack
import com.example.robogyan.ui.theme.Cyan
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.viewmodel.MemberViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberPage(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val memberViewModel: MemberViewModel = viewModel()
    val members by memberViewModel.members.observeAsState(emptyList())
    val count = members.size
    var id by remember { mutableIntStateOf(0)}

    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var searchItem by remember { mutableStateOf("") }

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CharcoalBlack)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                horizontal = 0.035 * screenWidth
                            )
                            .fillMaxSize()
                    ) {
                        item{
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Member Details",
                                    color = Color.White,
                                    fontSize = 30.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.W500
                                )
                                Row {
                                    FloatingActionButton(
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(50))
                                            .size(46.dp),
                                        onClick = { },
                                        containerColor = Color(0xFFE0E0E0),
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.edit),
                                            contentDescription = "cart",
                                            Modifier.size(30.dp),
                                            tint = Black
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.me),
                                        contentDescription = "Profile",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(50.dp))
                                            .size(46.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item{
                            OutlinedTextField(
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF2a2a2a),
                                    unfocusedBorderColor = Color(0xFF2a2a2a),
                                    cursorColor = Color(0xFFFFFFFF),
                                    containerColor = Color(0xFF2a2a2a),
                                ),
                                modifier = Modifier
                                    .padding(
                                        start = 0.005 * screenWidth,
                                        end = 0.005 * screenWidth,
                                    )
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(35)),
                                leadingIcon = {
                                    Icon(
                                        modifier = Modifier.size(22.dp),
                                        painter = painterResource(id = R.drawable.search),
                                        contentDescription = "search"
                                    )
                                },
                                value = searchItem,
                                onValueChange = { searchItem = it },
                                placeholder = {
                                    Text(
                                        color = Color(0x9EFFFFFF),
                                        text = "Search for a member",
                                        fontFamily = latoFontFamily,
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        }
                        item{
                            if (members.isEmpty()){
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .size(50.dp),
                                    color = Color(0xFFE0E0E0)
                                )
                            }
                            else {
                                repeat(count) {
                                    Log.d("ImageData", "https://meets.pockethost.io/api/files/pbc_3572739349/${members[it].id}/${members[it].image}")
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                color = Color(0xFFE0E0E0),
                                                shape = RoundedCornerShape(25.dp)
                                            )
                                            .padding(
                                                vertical = 0.01 * screenHeight,
                                                horizontal = 0.035 * screenWidth
                                            )
                                            .clickable {
                                                id = it
                                                showSheet = true
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            AsyncImage(
                                                model = "https://meets.pockethost.io/api/files/pbc_3572739349/${members[it].id}/${members[it].image}",
                                                contentDescription = "Profile",
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(25.dp))
                                                    .size(130.dp)
                                                    .border(
                                                        width = 3.dp,
                                                        color = Color(0xFFFFFFFF),
                                                        shape = RoundedCornerShape(25.dp)
                                                    )
                                            )
                                            Spacer(modifier = Modifier.size(0.04 * screenWidth))
                                            Box(
                                                modifier = Modifier.height(130.dp)
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .align(Alignment.CenterStart)
                                                        .fillMaxWidth()
                                                ) {
                                                    Text(
                                                        text = members[it].name,
                                                        fontFamily = latoFontFamily,
                                                        color = Color.Black,
                                                        fontSize = 22.sp,
                                                        fontWeight = FontWeight.W500,
                                                        modifier = Modifier
                                                    )
                                                    Text(
                                                        text = members[it].pos,
                                                        color = Color.Black,
                                                        fontFamily = latoFontFamily,
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.W500,
                                                    )
                                                    Text(
                                                        text = members[it].batch,
                                                        fontFamily = latoFontFamily,
                                                        color = Color.Gray,
                                                        fontSize = 16.sp,
                                                        fontWeight = FontWeight.W500,
                                                        modifier = Modifier
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.size(0.02 * screenHeight))
                                }
                            }
                            Spacer(modifier = Modifier.size(innerPadding.calculateBottomPadding() + 0.07 * screenHeight))
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                if (showSheet) Color(0xFF000000).copy(alpha = 0.75f) else Color.Transparent
                            )
                    ){}

                    if (showSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showSheet = false },
                            sheetState = sheetState,
                            scrimColor = Color.Transparent,
                            containerColor = Color.White,
                            dragHandle = null,
                            windowInsets = WindowInsets(
                                top = innerPadding.calculateTopPadding() + 0.27 * screenHeight,
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .width(50.dp)
                                        .height(5.dp)
                                        .background(Color.Black, shape = RoundedCornerShape(50))
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 0.035 * screenWidth,
                                            end = 0.035 * screenWidth,
                                            bottom = 0.02 * screenHeight
                                        )
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        AsyncImage(
                                            model = "https://meets.pockethost.io/api/files/pbc_3572739349/${members[id].id}/${members[id].image}",
                                            contentDescription = "Profile",
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(50.dp))
                                                .size(130.dp)
                                                .border(
                                                    width = 3.dp,
                                                    color = Color(0xFFE0E0E0),
                                                    shape = RoundedCornerShape(50.dp)
                                                )
                                        )
                                        Spacer(modifier = Modifier.size(0.05 * screenWidth))
                                        Column{
                                            Text(
                                                text = members[id].name,
                                                color = Color.Black,
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.W500,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier
                                            )
                                            Text(
                                                text = members[id].pos,
                                                color = Color.Black,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.W500,
                                                fontFamily = latoFontFamily,
                                            )
                                            Text(
                                                text = members[id].batch,
                                                color = Color.Gray,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.W500,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.size(0.015 * screenHeight))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal = 0.035 * screenWidth
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "Email id",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : ${members[id].email}",
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            fontFamily = latoFontFamily,
                                            modifier = Modifier
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.005 * screenHeight))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal = 0.035 * screenWidth
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "Mob. No",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : +91 ${members[id].mobileNum}",
                                            color = Color.Black,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            modifier = Modifier
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.005 * screenHeight))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal = 0.035 * screenWidth
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "Enrll. No",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : ${members[id].enrollment}",
                                            color = Color.Black,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            modifier = Modifier
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.005 * screenHeight))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal = 0.035 * screenWidth
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "Lab Access",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : ${if (members[id].labAccess) "Yes" else "No"}",
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.W500,
                                            modifier = Modifier
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.005 * screenHeight))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal = 0.035 * screenWidth
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "Last Accessed",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : --",
                                            color = Color.Black,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            modifier = Modifier
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.005 * screenHeight))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal = 0.035 * screenWidth
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "Tech Stack",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : ${members[id].techStack}",
                                            color = Color.Black,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            modifier = Modifier
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.005 * screenHeight))
                                }
                            }
                        }
                    }

                    // Bottom Navigation
                    Row(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(
                                horizontal = 0.035 * screenWidth
                            )
                            .background(
                                shape = RoundedCornerShape(40),
                                color = Cyan
                            ),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                navController.navigate("home"){
                                    popUpTo("home"){
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
                                tint = Black
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("logs"){
                                    popUpTo("logs"){
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.log),
                                contentDescription = "explore",
                                Modifier.size(32.dp),
                                tint = Black
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.member_d),
                                contentDescription = "cart_na",
                                Modifier.size(32.dp),
                                tint = Black
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.event),
                                contentDescription = "cart_na",
                                Modifier.size(32.dp),
                                tint = Black
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("profile"){
                                    popUpTo("profile"){
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .size(55.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.user),
                                contentDescription = "account",
                                Modifier.size(32.dp),
                                tint = Black
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
fun MemberPagePreview() {
    MemberPage(rememberNavController())
    //Test()
}