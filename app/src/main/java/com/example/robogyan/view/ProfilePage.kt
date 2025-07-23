package com.example.robogyan.view

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Dialog
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.robogyan.R
import com.example.robogyan.SupabaseClientProvider
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.MemberData
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.PinkOne
import com.example.robogyan.ui.theme.PrimaryColor
import com.example.robogyan.ui.theme.PrimaryText
import com.example.robogyan.ui.theme.PurpleOne
import com.example.robogyan.ui.theme.SecondaryColor
import com.example.robogyan.ui.theme.YellowOne
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.utils.SharedPrefManager
import com.example.robogyan.viewmodel.AuthViewModel
import com.example.robogyan.viewmodel.UpdateViewModel
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.Flow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfilePage(navController: NavController){

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }

    val userId: String = SupabaseClientProvider.client.auth.currentUserOrNull()!!.id
    val memberFlow: Flow<MemberData?> =
        AppDatabase.getDatabase(context).memberDao().getMemberById(userId)
    val member by memberFlow.collectAsState(initial = null)

    val authViewModel: AuthViewModel = viewModel()
    val updatesViewModel: UpdateViewModel = viewModel()
    val updateA by updatesViewModel.updateA.collectAsState()
    val updateB by updatesViewModel.updateB.collectAsState()
    var update by remember { mutableStateOf("UpdateA") }
    var showDialog by remember { mutableStateOf(false) }
    var final by remember { mutableStateOf("") }

    var checkedA by remember { mutableStateOf(true) }
    var checkedB by remember { mutableStateOf(false) }

    @Composable
    fun ChangeUpdateDialog(
        onDismiss: () -> Unit,
        onConfirm: () -> Unit
    ) {
        var placeholder by remember { mutableStateOf("") }
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 8.dp,
                color = Color(0xFF151515),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF1A1A1A),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Change Update",
                        fontFamily = latoFontFamily,
                        color = PrimaryText,
                        fontSize = 18.sp,
                        textDecoration = TextDecoration.Underline,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(0.4f),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(text = "Update A")
                            RadioButton(
                                selected = checkedA,
                                onClick = {
                                    update = "UpdateA"
                                    checkedA = true
                                    checkedB = false
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Green,
                                    unselectedColor = Color.Gray
                                )
                            )
                        }
                        Row(
                            modifier = Modifier
                                .weight(0.4f),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(text = "Update B")
                            RadioButton(
                                selected = checkedB,
                                onClick = {
                                    update = "UpdateB"
                                    checkedA = false
                                    checkedB = true
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Green,
                                    unselectedColor = Color.Gray
                                )
                            )
                        }
                    }
                    if (update == "UpdateA"){
                        placeholder = updateA.toString()
                    }else{
                        placeholder = updateB.toString()
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.063 * screenHeight),
                        shape = RoundedCornerShape(size = 16.dp),
                        placeholder = {
                            Text(
                                placeholder,
                                fontFamily = latoFontFamily,
                                color = Color(0xFFB2B2B2),
                            )
                        },
                        value = final,
                        onValueChange = { final = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PurpleOne,
                            unfocusedBorderColor = Color(0x66ABABAB),
                            focusedTextColor = Color(0xFFFFFFFF),
                            unfocusedTextColor = Color(0xFFFFFFFF),
                            unfocusedContainerColor = Color(0x14ABABAB),
                            focusedContainerColor = Color(0x14ABABAB)
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text(
                                "Cancel",
                                color = Color(0xFFDE4251),
                                fontFamily = latoFontFamily,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(onClick = {
                            // Now pass all values back to parent composable
                            onConfirm()
                            onDismiss()
                        }) {
                            Text(
                                "Confirm",
                                color = Color(0xFF4CAF50),
                                fontFamily = latoFontFamily,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
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
                        .pointerInput(Unit) {
                            detectTapGestures {
                                focusManager.clearFocus()
                            }
                        }
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        item {
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = 50.dp,
                                            bottomEnd = 50.dp
                                        )
                                    )
                                    .background(BackgroundColor)
                            ) {
                                AsyncImage(
                                    model = member?.image,//R.drawable.mee
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .alpha(0.65f)
                                        .fillMaxWidth()
                                        .height(0.38 * screenHeight)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 0.035 * screenWidth)
                                        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .border(
                                                width = 3.dp,
                                                color = PurpleOne,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.TwoTone.KeyboardArrowLeft,
                                            contentDescription = "Arrow Icon",
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .size(34.dp)
                                                .align(Alignment.CenterStart)
                                                .clickable {
                                                    navController.popBackStack()
                                                },
                                            tint = PurpleOne
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(0.04 * screenHeight))
                                Column(
                                    modifier = Modifier
                                        .padding(bottom = 20.dp)
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    member?.let { it1 ->
                                        Text(
                                            text = it1.name,
                                            color = Color.White,
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = latoFontFamily,
                                            modifier = Modifier
                                        )
                                        Text(
                                            text = it1.current_pos,
                                            color = Color.White,
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = latoFontFamily,
                                        )
                                        Text(
                                            text = it1.pos_period,
                                            color = Color(0xFF9F9F9F),
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = latoFontFamily,
                                            modifier = Modifier
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(0.05 * screenHeight))
                            }
                            Spacer(modifier = Modifier.height(0.04 * screenHeight))
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 0.035 * screenWidth)
                            )  {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .weight(1f)
                                        .background(PurpleOne)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable {
                                        }
                                ){
                                    Column(
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    if (member?.clearance == "President" || member?.clearance == "Vice President"){
//                                                        navController.navigate("addProject")
                                                    }else{
                                                        Toast.makeText(context, "Only President or Vice President can add project.", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            )
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.member_d),
                                            contentDescription = "members",
                                            modifier = Modifier.size(38.dp),
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Edit Members",
                                            color = Color.Black,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .weight(1f)
                                        .background(PrimaryText)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable {
                                        }
                                ){
                                    Column(
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    if (member?.clearance == "President" || member?.clearance == "Vice President" || member?.clearance == "Lead"){
                                                        showDialog = true
                                                    }else{
                                                        Toast.makeText(context, "Only Core Team can add updates.", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            )
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.update),
                                            contentDescription = "members",
                                            modifier = Modifier.size(38.dp),
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Add New Update",
                                            color = Color.Black,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(0.01 * screenHeight))
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 0.035 * screenWidth)
                            )  {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .weight(1f)
                                        .background(PinkOne)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable {
                                        }
                                ){
                                    Column(
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    if (member?.clearance == "President" || member?.clearance == "Vice President"){
//                                                        navController.navigate("addProject")
                                                    }else{
                                                        Toast.makeText(context, "Only President or Vice President can add project.", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            )
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.open),
                                            contentDescription = "members",
                                            modifier = Modifier.size(38.dp),
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Open Lab",
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .weight(1f)
                                        .background(YellowOne)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable {
                                        }
                                ){
                                    Column(
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    if (member?.clearance == "President" || member?.clearance == "Vice President"){
//                                                        navController.navigate("addProject")
                                                    }else{
                                                        Toast.makeText(context, "Only President or Vice President can add project.", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            )
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.component),
                                            contentDescription = "members",
                                            modifier = Modifier.size(38.dp),
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Edit Assets",
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(0.02 * screenWidth))
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20))
                                        .weight(1f)
                                        .background(PinkOne)
                                        .padding(
                                            horizontal = 0.02 * screenWidth,
                                            vertical = 0.03 * screenWidth
                                        )
                                        .clickable {
                                        }
                                ){
                                    Column(
                                        modifier = Modifier
                                            .clickable(
                                                onClick = {
                                                    if (member?.clearance == "President" || member?.clearance == "Vice President"){
                                                        navController.navigate("addProject")
                                                    }else{
                                                        Toast.makeText(context, "Only President or Vice President can add project.", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            )
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.projects_d),
                                            contentDescription = "members",
                                            modifier = Modifier.size(38.dp),
                                            tint = Color.Black
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Add Project",
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(0.03 * screenHeight))
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 0.035 * screenWidth)
                                    .clip(RoundedCornerShape(20.dp))
                                    .border(
                                        width = 0.5.dp,
                                        color = Color(0xFF2D2D2D),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .background(SecondaryColor)
                                    .padding(
                                        horizontal = 0.02 * screenWidth,
                                        vertical = 0.03 * screenWidth
                                    )
                            ){
                                Spacer(modifier = Modifier.height(0.01 * screenHeight))
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 0.025 * screenWidth)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.edit),
                                            contentDescription = "members",
                                            modifier = Modifier.size(34.dp),
                                            tint = PurpleOne
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Edit Profile",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                                        contentDescription = "Arrow Icon",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable {},
                                        tint = PurpleOne
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.012 * screenHeight))
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 0.025 * screenWidth)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.password),
                                            contentDescription = "members",
                                            modifier = Modifier.size(34.dp),
                                            tint = PinkOne
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Change Password",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                                        contentDescription = "Arrow Icon",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable {},
                                        tint = PinkOne
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.012 * screenHeight))
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 0.025 * screenWidth)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.review),
                                            contentDescription = "members",
                                            modifier = Modifier.size(34.dp),
                                            tint = PinkOne
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Send Feedback",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                                        contentDescription = "Arrow Icon",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable {},
                                        tint = PinkOne
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.012 * screenHeight))
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 0.025 * screenWidth)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                authViewModel.logout()
                                                SharedPrefManager.clear(context)
                                                context.deleteDatabase("robogyan_database")
                                                navController.navigate("start") {
                                                    popUpTo(0)
                                                    launchSingleTop = true
                                                }
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            painter = painterResource(R.drawable.logout),
                                            contentDescription = "members",
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clickable {
                                                    authViewModel.logout()
                                                    SharedPrefManager.clear(context)
                                                    context.deleteDatabase("robogyan_database")
                                                    navController.navigate("start") {
                                                        popUpTo(0) // Clears the entire backstack
                                                        launchSingleTop = true
                                                    }
                                            },
                                            tint = PurpleOne
                                        )
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Text(
                                            text = "Logout",
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                    Icon(
                                        Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                                        contentDescription = "Arrow Icon",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable {
                                                authViewModel.logout()
                                                SharedPrefManager.clear(context)
                                                context.deleteDatabase("robogyan_database")
                                                navController.navigate("start") {
                                                    popUpTo(0) // Clears the entire backstack
                                                    launchSingleTop = true
                                                }
                                            },
                                        tint = PurpleOne
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.01 * screenHeight))
                            }
                            if (showDialog){
                                ChangeUpdateDialog(
                                    onDismiss = { showDialog = false },
                                    onConfirm = {
                                        updatesViewModel.updateMessage(
                                            update, final
                                        )
                                    }
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
fun ProfilePagePreview(){
    ProfilePage(rememberNavController())
}