package com.example.robogyan.view

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.R
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.AllMembers
import com.example.robogyan.data.local.entities.Inventory
import com.example.robogyan.ui.theme.AccentColor
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.Black
import com.example.robogyan.ui.theme.GunmetalGray
import com.example.robogyan.ui.theme.PeachOne
import com.example.robogyan.ui.theme.PinkOne
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.PrimaryText
import com.example.robogyan.ui.theme.PurpleOne
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.utils.SharedPrefManager
import com.example.robogyan.viewmodel.InventoryViewModel
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SecurityPage(navController: NavController) {

    val context = LocalContext.current
    val isloggedin = SharedPrefManager.isLoggedIn(context)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current

    var searchItem by remember { mutableStateOf("") }
    val haveAccess by remember { mutableStateOf(true) }
    var gateStatus by remember { mutableStateOf(true) }

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }

    val inventoryViewModel: InventoryViewModel = viewModel()
    val assetData by inventoryViewModel.inventoryFlow.collectAsState()
    var showAssets by remember { mutableStateOf(false) }
    var assetId by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        delay(400)
        showAssets = true
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
                        .pointerInput(Unit){
                            detectTapGestures {
                                focusManager.clearFocus()
                            }
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
                                    text = "Security & Assets",
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
                        if (isloggedin){
                            item {
                                Spacer(modifier = Modifier.size(0.01 * screenHeight))
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
                                        .padding(
                                            horizontal = 0.05 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        ),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Text(
                                            text = "Close Lab",
                                            color = TextColor,
                                            fontSize = 24.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                        if (haveAccess){
                                            Switch(
                                                checked = gateStatus,
                                                onCheckedChange = {
                                                    focusManager.clearFocus()
                                                    gateStatus = it
                                                },
                                                colors = SwitchColors(
                                                    checkedThumbColor = Color.Black,
                                                    uncheckedThumbColor = Color(0xFFB2B2B2),
                                                    checkedTrackColor = PurpleOne,
                                                    uncheckedTrackColor = Color(0xFFB2B2B2).copy(alpha = 0.5f),
                                                    checkedBorderColor = Color.Transparent,
                                                    checkedIconColor = Color.Transparent,
                                                    uncheckedBorderColor = Color.Transparent,
                                                    uncheckedIconColor = Color.Transparent,
                                                    disabledCheckedThumbColor = Color.Transparent,
                                                    disabledCheckedTrackColor = Color.Transparent,
                                                    disabledCheckedBorderColor = Color.Transparent,
                                                    disabledCheckedIconColor = Color.Transparent,
                                                    disabledUncheckedThumbColor = Color.Transparent,
                                                    disabledUncheckedTrackColor = Color.Transparent,
                                                    disabledUncheckedBorderColor = Color.Transparent,
                                                    disabledUncheckedIconColor = Color.Transparent,
                                                )
                                            )
                                        }else{
                                            Switch(
                                                checked = gateStatus,
                                                onCheckedChange = {
                                                    focusManager.clearFocus()
                                                },
                                                colors = SwitchColors(
                                                    checkedThumbColor = Color.Black,
                                                    uncheckedThumbColor = Color(0xFFB2B2B2),
                                                    checkedTrackColor = Color.Gray,
                                                    uncheckedTrackColor = Color(0xFFB2B2B2).copy(alpha = 0.5f),
                                                    checkedBorderColor = Color.Transparent,
                                                    checkedIconColor = Color.Transparent,
                                                    uncheckedBorderColor = Color.Transparent,
                                                    uncheckedIconColor = Color.Transparent,
                                                    disabledCheckedThumbColor = Color.Transparent,
                                                    disabledCheckedTrackColor = Color.Transparent,
                                                    disabledCheckedBorderColor = Color.Transparent,
                                                    disabledCheckedIconColor = Color.Transparent,
                                                    disabledUncheckedThumbColor = Color.Transparent,
                                                    disabledUncheckedTrackColor = Color.Transparent,
                                                    disabledUncheckedBorderColor = Color.Transparent,
                                                    disabledUncheckedIconColor = Color.Transparent,
                                                )
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.size(0.01 * screenHeight))
                                    Text(
                                        text = "No data to show...",
                                        color = PrimaryText,
                                        fontSize = 18.sp,
                                        fontFamily = latoFontFamily,
                                    )
                                }
                                Spacer(modifier = Modifier.size(0.01 * screenHeight))
                            }
                            //Logs data
                            item{
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
                                        .padding(
                                            horizontal = 0.05 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                ){
                                    Text(
                                        text = "Lab Access Logs",
                                        color = TextColor,
                                        fontSize = 20.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.size(0.01 * screenHeight))
                                    Text(
                                        text = "No logs currently available.",
                                        color = PrimaryText,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center,
                                        fontFamily = latoFontFamily,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                }
                                Spacer(modifier = Modifier.size(0.03 * screenHeight))
                            }
                            item {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    color = Color(0xFF2D2D2D)
                                )
                                Spacer(modifier = Modifier.size(0.02 * screenHeight))
                                Text(
                                    text = "Assets Inventory",
                                    color = TextColor,
                                    fontSize = 26.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                )
                                Spacer(modifier = Modifier.size(0.015 * screenHeight))
                                FloatingActionButton(
                                    onClick = {
                                        Toast.makeText(context, "Use Supa for new assets!", Toast.LENGTH_SHORT).show()
                                    },
                                    containerColor = PurpleOne,
                                    contentColor = Black,
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ){
                                        Icon(
                                            Icons.Rounded.Add,
                                            contentDescription = "Add Asset",
                                            modifier = Modifier.size(24.dp),
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.size(4.dp))
                                        Text(
                                            text = "Add Component",
                                            color = Color.Black,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.size(0.02 * screenHeight))
                            }
                            item{
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
                                            tint = PurpleOne
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
                                            text = "Search for an asset",
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.size(0.015 * screenHeight))
                                if (showAssets){
                                    val filteredAssetData = assetData
                                        .filter { asset ->
                                            asset.name.contains(searchItem, ignoreCase = true)
                                        }

                                    filteredAssetData.forEach{
                                        Box(
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 10.dp
                                                )
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(SecondaryColor)
                                                .clickable(
                                                    onClick ={
                                                        assetId = it.id
                                                        navController.navigate("assetview/$assetId")
                                                    }
                                                )
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        12.dp
                                                    )
                                                    .clickable(
                                                        onClick ={
                                                            assetId = it.id
                                                            navController.navigate("assetview/$assetId")
                                                        }
                                                    ),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .weight(0.9f)
                                                        .clickable(
                                                            onClick ={
                                                                assetId = it.id
                                                                navController.navigate("assetview/$assetId")
                                                            }
                                                        )
                                                ) {
                                                    Text(
                                                        text = it.available.toString(),
                                                        color = PrimaryText,
                                                        fontSize = 18.sp,
                                                        fontFamily = latoFontFamily,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier
                                                            .weight(0.1f)
                                                    )
                                                    Spacer(modifier = Modifier.size(20.dp))
                                                    Text(
                                                        text = it.name,
                                                        color = PrimaryText,
                                                        fontSize = 18.sp,
                                                        fontFamily = latoFontFamily,
                                                        modifier = Modifier
                                                            .weight(0.8f)
                                                    )
                                                }
                                                Icon(
                                                    Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                                    contentDescription = "View Details",
                                                    tint = Color.Gray,
                                                    modifier = Modifier
                                                        .size(28.dp)
                                                        .weight(0.1f)
                                                        .clickable(
                                                            onClick ={
                                                                assetId = it.id
                                                                navController.navigate("assetview/$assetId")
                                                            }
                                                        ),
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(0.01 * screenHeight))
                                    }
                                }
                                else{
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .size(50.dp),
                                        color = PurpleOne
                                    )
                                }
                                Spacer(modifier = Modifier.size(0.12 * screenHeight))
                            }
                        }else{
                            item {
                                Spacer(modifier = Modifier.height(0.1 * screenHeight))
                                Text(
                                    text = "To view this page, login first...",
                                    color = PrimaryText,
                                    fontSize = 22.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
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
                                navController.navigate("member"){
                                    popUpTo("security"){
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
                                painter = painterResource(R.drawable.security_d),
                                contentDescription = "explore",
                                Modifier.size(26.dp),
                                tint = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("projects"){
                                    popUpTo("security"){
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
                                    popUpTo("security"){
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun LogPagePreview() {
    SecurityPage(rememberNavController())
}