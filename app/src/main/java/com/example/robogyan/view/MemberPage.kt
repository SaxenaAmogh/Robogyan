package com.example.robogyan.view

import android.app.Activity
import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.robogyan.R
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.AllMembers
import com.example.robogyan.data.local.entities.MemberData
import com.example.robogyan.ui.theme.AccentColor
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.Black
import com.example.robogyan.ui.theme.GunmetalGray
import com.example.robogyan.ui.theme.NavBar
import com.example.robogyan.ui.theme.PeachOne
import com.example.robogyan.ui.theme.PinkOne
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.PrimaryText
import com.example.robogyan.ui.theme.PurpleOne
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.utils.SharedPrefManager
import com.example.robogyan.viewmodel.MemberViewModel
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberPage(navController: NavController) {

    val context = LocalContext.current
    val isloggedin = SharedPrefManager.isLoggedIn(context)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var member by remember { mutableStateOf(AllMembers(
        id = "",
        name = "",
        email = "",
        mobile = "0L",
        image = "",
        current_pos = "",
        pos_period = "",
        enrollment = "",
        lab_access = false,
        batch = "",
        clearance = "",
        is_alumni = false
    )) }

    val allMemberFlow: Flow<List<AllMembers>> =
        AppDatabase.getDatabase(context).allMembersDao().getAllMembers()
    val members by allMemberFlow.collectAsState(initial = emptyList())

    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var searchItem by remember { mutableStateOf("") }
    var currentMember by remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }
    val userId: String = SupabaseClientProvider.client.auth.currentUserOrNull()!!.id
    val memberFlow: Flow<MemberData?> =
        AppDatabase.getDatabase(context).memberDao().getMemberById(userId)
    val loggedMember by memberFlow.collectAsState(initial = null)

    Scaffold(
        content = { innerPadding ->
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
                        .pointerInput(Unit){
                            detectTapGestures {
                                focusManager.clearFocus()
                            }
                        }
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                            ){
                                Text(
                                    text = "Members",
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
                            Spacer(modifier = Modifier.size(0.015 * screenHeight))
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        shape = RoundedCornerShape(22.dp),
                                        color = Color(0xFF232325)
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ){
                                Button(
                                    onClick = {
                                        currentMember = true
                                    },
                                    modifier = Modifier
                                        .padding(
                                            vertical = 8.dp,
                                            horizontal = 8.dp
                                        )
                                        .weight(1f)
                                        .height(0.05 * screenHeight),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = if (currentMember) {
                                        ButtonDefaults.buttonColors(
                                            containerColor = PurpleOne
                                        )
                                    } else {
                                        ButtonDefaults.buttonColors(
                                            containerColor = Color.Transparent
                                        )
                                    }
                                ) {
                                    Text(
                                        text = "Current",
                                        color = if (currentMember) Color.Black else Color.White,
                                        fontSize = 16.sp,
                                        fontFamily = latoFontFamily,
                                    )
                                }
                                Button(
                                    onClick = {
                                        currentMember = false
                                    },
                                    modifier = Modifier
                                        .padding(
                                            vertical = 8.dp,
                                            horizontal = 8.dp
                                        )
                                        .weight(1f)
                                        .height(0.05 * screenHeight),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = if (!currentMember) {
                                        ButtonDefaults.buttonColors(
                                            containerColor = PinkOne
                                        )
                                    } else {
                                        ButtonDefaults.buttonColors(
                                            containerColor = Color.Transparent
                                        )
                                    }
                                ) {
                                    Text(
                                        text = "Alumni",
                                        color = if (!currentMember) Color.Black else Color.White,
                                        fontSize = 16.sp,
                                        fontFamily = latoFontFamily,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(0.015 * screenHeight))
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
                                        tint = if (!currentMember) PinkOne else PurpleOne
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
                                        text = "Search for a member",
                                        fontFamily = latoFontFamily,
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.size(0.015 * screenHeight))
                        }
                        item{
                            if (members.isEmpty()){
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .size(50.dp),
                                    color = if (!currentMember) PinkOne else PurpleOne
                                )
                            }
                            else {
                                val filteredMembers = members
                                    .filter { memberx ->
                                        val isAlumni = memberx.is_alumni == true

                                        if (currentMember) {
                                            // Show current members only (not alumni)
                                            !isAlumni && memberx.name.contains(searchItem, ignoreCase = true)
                                        } else {
                                            // Show alumni only
                                            isAlumni && memberx.name.contains(searchItem, ignoreCase = true)
                                        }
                                    }
                                    .sortedWith(compareBy { m ->
                                        when {
                                            currentMember && m.current_pos.equals("President", ignoreCase = true) -> 0
                                            currentMember && m.current_pos.equals("Vice President", ignoreCase = true) -> 1
                                            else -> 2
                                        }
                                    })

                                filteredMembers.forEach() {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(25.dp))
//                                            .border(
//                                                width = 2.dp,
//                                                color = if (it.is_alumni == true) PinkOne else PurpleOne,
//                                                shape = RoundedCornerShape(25.dp)
//                                            )
                                            .padding(
                                                vertical = 0.01 * screenHeight,
                                            )
                                            .clickable {
                                                member = it
                                                showSheet = true
                                            }
                                            .background(
                                                shape = RoundedCornerShape(25.dp),
                                                color = SecondaryColor)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    vertical = 0.02 * screenHeight,
                                                    horizontal = 0.035 * screenWidth
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            AsyncImage(
                                                model = R.drawable.mee,//it.image
                                                contentDescription = "Profile",
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(25.dp))
                                                    .size(130.dp)
                                                    .weight(0.3f),
                                                contentScale = ContentScale.Crop
                                            )
                                            Spacer(modifier = Modifier.size(0.04 * screenWidth))
                                            Box(
                                                modifier = Modifier.height(130.dp)
                                                    .weight(0.36f)
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .align(Alignment.CenterStart)
                                                ) {
                                                    Text(
                                                        text = it.name,
                                                        fontFamily = latoFontFamily,
                                                        color = if (it.is_alumni == true) PinkOne else PurpleOne,
                                                        fontSize = 22.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        overflow = TextOverflow.Ellipsis,
                                                        maxLines = 1
                                                    )
                                                    Text(
                                                        text = it.current_pos,
                                                        color = PrimaryColor,
                                                        fontFamily = latoFontFamily,
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.W500,
                                                    )
                                                    Text(
                                                        text = it.pos_period,
                                                        fontFamily = latoFontFamily,
                                                        color = Color.Gray,
                                                        fontSize = 16.sp,
                                                        fontWeight = FontWeight.W500,
                                                        modifier = Modifier
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.size(0.04 * screenWidth))
                                            Icon(
                                                painter = painterResource(id = R.drawable.edit),
                                                contentDescription = "arrow",
                                                modifier = Modifier
                                                    .weight(0.1f)
                                                    .size(26.dp)
                                                    .align(Alignment.Top)
                                                    .clickable(
                                                        onClick = {
                                                            if (loggedMember?.clearance == "President" || loggedMember?.clearance == "Vice President") {
                                                                navController.navigate("updateMember/${it.id}")
//                                                                Log.e("&&&IDeRROR", it.id)
                                                            } else {
                                                                Toast.makeText(context, "You don't have permission to edit members", Toast.LENGTH_SHORT).show()
                                                            }
                                                        }
                                                    ),
                                                tint = if (loggedMember?.clearance == "President" || loggedMember?.clearance == "Vice President") {
                                                    Color.Gray
                                                } else{
                                                    Color.Transparent
                                                }
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.size(0.00 * screenHeight))
                                }
                            }
                            Spacer(modifier = Modifier.size(innerPadding.calculateBottomPadding() + 0.13 * screenHeight))
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                if (showSheet) Color(0xFF000000).copy(alpha = 0.85f) else Color.Transparent
                            )
                    ){}

                    if (showSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showSheet = false },
                            sheetState = sheetState,
                            scrimColor = Color.Transparent,
                            containerColor = SecondaryColor,
                            dragHandle = null,
                            windowInsets = WindowInsets(
                                top = innerPadding.calculateTopPadding() + 0.27 * screenHeight,
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(SecondaryColor),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .width(50.dp)
                                        .height(5.dp)
                                        .background(if (member.is_alumni == true) PinkOne else PurpleOne, shape = RoundedCornerShape(50))
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 0.035 * screenWidth,
                                            end = 0.035 * screenWidth,
                                            bottom = 0.02 * screenHeight
                                        ),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AsyncImage(
                                        model = member.image,//member.image
                                        contentDescription = "Profile",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(30.dp))
                                            .width(250.dp)
                                            .border(
                                                width = 1.dp,
                                                color = if (member.is_alumni == true) PinkOne else PurpleOne,
                                                shape = RoundedCornerShape(30.dp)
                                            )
                                    )
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
                                            text = "Name",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : ${member.name}",
                                            color = PrimaryColor,
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
                                            text = "Email id",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : ${member.email}",
                                            color = PrimaryColor,
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
                                            text = " : +91 ${member.mobile}",
                                            color = PrimaryColor,
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
                                            text = "Enrollment No",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : ${member.enrollment}",
                                            color = PrimaryColor,
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
                                            text = " : ${if (member.lab_access == true) "Yes" else "No"}",
                                            color = PrimaryColor,
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
                                            text = "Btech Batch",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : ${member.batch}",
                                            color = PrimaryColor,
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
                                            text = "Clearance Lvl",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : ${member.clearance}",
                                            color = PrimaryColor,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            modifier = Modifier
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.02 * screenHeight))
                                }
                            }
                        }
                    }

                    // Bottom Navigation & Search
                    if (!showSheet){
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                        ){
                            Row(
                                modifier = Modifier
                                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                                    .fillMaxWidth()
                                    .height(0.07 * screenHeight)
                                    .padding(
                                        horizontal = 0.035 * screenWidth
                                    )
                                    .background(
                                        shape = RoundedCornerShape(40.dp),
                                        color = NavBar
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
                                    onClick = {},
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(25.dp))
                                        .size(45.dp)
                                        .background(
                                            color = Color(0xFF3872D9),
                                            shape = RoundedCornerShape(50)
                                        )
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.member_d),
                                        contentDescription = "cart_na",
                                        Modifier.size(24.dp),
                                        tint = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.size(8.dp))
                                IconButton(
                                    onClick = {
                                        navController.navigate("security"){
                                            popUpTo("member"){
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
                                        navController.navigate("projects"){
                                            popUpTo("member"){
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
                                    onClick = {
                                        navController.navigate("resources"){
                                            popUpTo("member"){
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