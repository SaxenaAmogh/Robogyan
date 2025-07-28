package com.example.robogyan.view.secpages

import android.app.Activity
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.R
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.Inventory
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.PinkOne
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.PrimaryText
import com.example.robogyan.ui.theme.PurpleOne
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.SecondaryText
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetViewPage(navController: NavController, assetId: Int){

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }
    val assetFlow: Flow<Inventory?> =
        AppDatabase.getDatabase(context).inventoryDao().getAssetById(assetId)
    val asset by assetFlow.collectAsState(initial = null)

    Scaffold(
        content = {innerPadding ->
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
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
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
                                        color = PurpleOne,
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
                                    tint = PurpleOne
                                )
                            }
                            Text(
                                text = "View Asset",
                                color = TextColor,
                                fontSize = 24.sp,
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
                                    Icon(
                                        painter = painterResource(R.drawable.unav),
                                        contentDescription = "Asset Image",
                                        tint = Color.Gray,
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(horizontal = 12.dp)
                                            .clip(RoundedCornerShape(12))
                                            .size(0.15 * screenHeight),
                                    )
//                                    if (asset?.image != null){
//                                        val request = ImageRequest.Builder(context)
//                                            .data(asset?.image)
//                                            .diskCachePolicy(CachePolicy.DISABLED) // or ENABLED for caching
//                                            .build()
//                                        AsyncImage(
//                                            model = request,
//                                            contentDescription = "Asset Image",
//                                            error = painterResource(R.drawable.unav),
//                                            contentScale = ContentScale.Crop,
//                                            modifier = Modifier
//                                                .align(Alignment.CenterHorizontally)
//                                                .padding(horizontal = 12.dp)
//                                                .clip(RoundedCornerShape(12))
//                                                .height(0.22 * screenHeight),
//                                        )
//                                    }else{
//                                        Icon(
//                                            painter = painterResource(R.drawable.unav),
//                                            contentDescription = "Asset Image",
//                                            tint = PrimaryText,
//                                            modifier = Modifier
//                                                .align(Alignment.CenterHorizontally)
//                                                .padding(horizontal = 12.dp)
//                                                .clip(RoundedCornerShape(12))
//                                                .size(0.15 * screenHeight),
//                                        )
//                                    }
                                    Spacer(modifier = Modifier.height(18.dp))
                                    asset?.let {
                                        Text(
                                            text = it.name,
                                            color = Color.White,
                                            fontSize = 28.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
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
                                                    text = "Type: ",
                                                    color = SecondaryText,
                                                    fontSize = 16.sp,
                                                    fontFamily = latoFontFamily,
                                                )
                                                Text(
                                                    text = it.type,
                                                    color = TextColor,
                                                    fontSize = 19.sp,
                                                    fontFamily = latoFontFamily,
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                            ) {
                                                Text(
                                                    text = "Description: ",
                                                    color = SecondaryText,
                                                    fontSize = 16.sp,
                                                    fontFamily = latoFontFamily,
                                                )
                                                Text(
                                                    text = it.description,
                                                    color = TextColor,
                                                    fontSize = 19.sp,
                                                    fontFamily = latoFontFamily,
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                            ) {
                                                Column {
                                                    Text(
                                                        text = "Quantity: ",
                                                        color = SecondaryText,
                                                        fontSize = 16.sp,
                                                        fontFamily = latoFontFamily,
                                                        modifier = Modifier
                                                    )
                                                    Text(
                                                        text = "${it.quantity} Units",
                                                        color = PurpleOne,
                                                        fontSize = 19.sp,
                                                        fontFamily = latoFontFamily,
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(80.dp))
                                                Column {
                                                    Text(
                                                        text = "Available: ",
                                                        color = SecondaryText,
                                                        fontSize = 16.sp,
                                                        fontFamily = latoFontFamily,
                                                    )
                                                    Text(
                                                        text = "${it.available.toString()} Units",
                                                        color = PurpleOne,
                                                        fontSize = 19.sp,
                                                        fontFamily = latoFontFamily,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                            }
                            item {
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
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(
                                            text = "No",
                                            color = SecondaryText,
                                            textAlign = TextAlign.Center,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                            modifier = Modifier.weight(0.15f)
                                        )
                                        Text(
                                            text = "Lent to",
                                            color = SecondaryText,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                            modifier = Modifier.weight(0.48f)
                                        )
                                        Text(
                                            text = "Quantity",
                                            color = SecondaryText,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            fontFamily = latoFontFamily,
                                            modifier = Modifier.weight(0.25f)
                                        )
                                        Text(
                                            text = "Use Case",
                                            color = SecondaryText,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            fontFamily = latoFontFamily,
                                            modifier = Modifier.weight(0.3f)
                                        )
                                        Box(
                                            modifier = Modifier.size(28.dp)
                                        ) {}
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    HorizontalDivider(
                                        color = Color(0xFF2D2D2D),
                                        thickness = 1.dp,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.size(6.dp))
                                    Text(
                                        text = "No data available currently.",
                                        color = PurpleOne,
                                        fontSize = 16.sp,
                                        fontFamily = latoFontFamily,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
//                                    repeat(5) {
//                                        Row(
//                                            modifier = Modifier
//                                                .fillMaxWidth()
//                                                .pointerInput(Unit) {
//                                                    detectTapGestures(onTap = {
//                                                        showSheet = true
//                                                    })
//                                                }
//                                                .padding(horizontal = 8.dp),
//                                            horizontalArrangement = Arrangement.SpaceBetween,
//                                        ) {
////                                            val originalText = "Website Redesign"
////                                            val displayText = if (originalText.length > 16) {
////                                                originalText.take(16) + "..."
////                                            } else {
////                                                originalText
////                                            }
//                                            Text(
//                                                text = "${it + 1}",
//                                                color = PrimaryColor,
//                                                textAlign = TextAlign.Center,
//                                                fontSize = 16.sp,
//                                                fontFamily = latoFontFamily,
//                                                modifier = Modifier.weight(0.15f)
//                                            )
//                                            Text(
//                                                text = "Amogh Saxena",
//                                                color = PrimaryColor,
//                                                fontSize = 16.sp,
//                                                fontFamily = latoFontFamily,
//                                                maxLines = 1,
//                                                overflow = TextOverflow.Ellipsis,
//                                                modifier = Modifier.weight(0.48f)
//                                            )
//                                            Text(
//                                                text = "2",
//                                                color = PrimaryColor,
//                                                fontSize = 16.sp,
//                                                textAlign = TextAlign.Center,
//                                                fontFamily = latoFontFamily,
//                                                modifier = Modifier.weight(0.25f)
//                                            )
//                                            Text(
//                                                text = "Personal",
//                                                textAlign = TextAlign.Center,
//                                                color = PrimaryColor,
//                                                fontSize = 16.sp,
//                                                fontFamily = latoFontFamily,
//                                                modifier = Modifier.weight(0.3f)
//                                            )
//                                            Icon(
//                                                Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
//                                                contentDescription = "Arrow Icon",
//                                                modifier = Modifier
//                                                    .size(28.dp)
//                                                    .clickable {
//                                                        showSheet = true
//                                                    },
//                                                tint = PinkOne
//                                            )
//                                        }
//                                        if (it < 4) {
//                                            Spacer(modifier = Modifier.size(6.dp))
//                                            HorizontalDivider(
//                                                color = Color(0xFF2D2D2D),
//                                                thickness = 1.dp,
//                                                modifier = Modifier.fillMaxWidth()
//                                            )
//                                            Spacer(modifier = Modifier.size(6.dp))
//                                        }
//                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                                Spacer(modifier = Modifier.height(0.1 * screenHeight))
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                if (showSheet) Color(0xFF000000).copy(alpha = 0.85f) else Color.Transparent
                            )
                    ) {}

                    if (showSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showSheet = false },
                            sheetState = sheetState,
                            scrimColor = Color.Transparent,
                            containerColor = SecondaryColor,
                            dragHandle = null,
                            windowInsets = WindowInsets(
                                top = innerPadding.calculateTopPadding(),
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
                                        .background(PinkOne, shape = RoundedCornerShape(50))
                                )
                                Spacer(modifier = Modifier.size(0.01 * screenHeight))
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
                                        Image(
                                            painter = painterResource(R.drawable.arduniomini),
                                            contentDescription = "Asset Image",
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(30.dp))
                                                .height(130.dp)
                                                .border(
                                                    width = 1.dp,
                                                    color = PinkOne,
                                                    shape = RoundedCornerShape(30.dp)
                                                )
                                        )
                                        Spacer(modifier = Modifier.size(0.05 * screenWidth))
                                        Column {
                                            Text(
                                                text = "Amogh Saxena",
                                                color = PinkOne,
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = latoFontFamily,
                                                modifier = Modifier
                                            )
                                            Text(
                                                text = "Personal",
                                                color = PrimaryColor,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.W500,
                                                fontFamily = latoFontFamily,
                                            )
                                            Text(
                                                text = "Quantity: 2",
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
                                            text = "Project Name",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : Maneuver Bot",
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
                                            text = "Status",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : Issued",
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
                                            text = "Return Date",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : NA",
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
                                            text = "Approved By",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : Macle",
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
                                    ) {
                                        Text(
                                            text = "Notes",
                                            color = Color.Gray,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                        Text(
                                            text = " : --",
                                            color = PrimaryColor,
                                            fontFamily = latoFontFamily,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            modifier = Modifier
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.02 * screenHeight))
                                    FloatingActionButton(
                                        onClick = {
                                            showSheet = false
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 0.035 * screenWidth),
                                        containerColor = PrimaryText,
                                        elevation = FloatingActionButtonDefaults.elevation(
                                            defaultElevation = 0.dp,
                                            pressedElevation = 0.dp,
                                            focusedElevation = 0.dp,
                                            hoveredElevation = 0.dp
                                        )
                                    ) {
                                        Text(
                                            text = "Change Status",
                                            fontFamily = latoFontFamily,
                                            color = Color.Black,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.W500,
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(0.02 * screenHeight))
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
fun AssetViewPagePreview() {
    AssetViewPage(rememberNavController(), 0)
}
