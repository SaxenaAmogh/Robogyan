package com.example.robogyan.view.secpages

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robogyan.data.local.AppDatabase
import com.example.robogyan.data.local.entities.AllMembers
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.PinkOne
import com.example.robogyan.ui.theme.PurpleOne
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.viewmodel.MemberViewModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UpdateMemberPage(navController: NavController, memberId: String){

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var enrollment by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var batch by remember { mutableStateOf("") }
    var currentPos by remember { mutableStateOf("") }
    var posPeriod by remember { mutableStateOf("") }
    var isAlumni by remember { mutableStateOf("") }
    var labAccess by remember { mutableStateOf("") }
    var clearance by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val clearanceOp = listOf("President", "Vice President", "Core", "Lead", "Member")
    var expanded2 by remember { mutableStateOf(false) }
    val labAccOp = listOf("True", "False")
    var expanded3 by remember { mutableStateOf(false) }
    val alumniOp = listOf("True", "False")

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }

    val getMemberFlow: Flow<List<AllMembers>> =
        AppDatabase.getDatabase(context).allMembersDao().getMemberById(memberId)
    val member by getMemberFlow.collectAsState(initial = emptyList())

    val memberViewModel: MemberViewModel = viewModel()
    val db = FirebaseDatabase.getInstance().getReference("DbUpdate")

    if (member.isNotEmpty()) {
        val currentMember = member[0]
        name = currentMember.name
        enrollment = currentMember.enrollment.toString()
        email = currentMember.email
        mobile = currentMember.mobile
        batch = currentMember.batch
        currentPos = currentMember.current_pos
        posPeriod = currentMember.pos_period
        isAlumni = currentMember.is_alumni.toString()
        labAccess = currentMember.lab_access.toString()
        clearance = currentMember.clearance
        image = currentMember.image
    } else {
        Toast.makeText(context, "Fetching Data...", Toast.LENGTH_SHORT).show()
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
                        .pointerInput(Unit){
                            detectTapGestures {
                                focusManager.clearFocus()
                            }
                        }
                ) {
                    LazyColumn {
                        item{
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
                                    text = "Update Member",
                                    color = TextColor,
                                    fontSize = 22.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                )
                            }
                            Spacer(modifier = Modifier.height(0.022 * screenHeight))
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = "Member Name",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.063 * screenHeight),
                                    shape = RoundedCornerShape(size = 16.dp),
                                    placeholder = {
                                        Text(
                                            "CHANGE",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = name,
                                    onValueChange = { name = it },
                                    keyboardActions = KeyboardActions(
                                        onDone = {focusManager.clearFocus()}
                                    ),
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
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Enrollment Number",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )

                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.063 * screenHeight),
                                    shape = RoundedCornerShape(size = 16.dp),
                                    placeholder = {
                                        Text(
                                            "CHANGE",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = enrollment,
                                    onValueChange = { enrollment = it },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {focusManager.clearFocus()}
                                    ),
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
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Email Id",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.063 * screenHeight),
                                    shape = RoundedCornerShape(size = 16.dp),
                                    placeholder = {
                                        Text(
                                            "CHANGE",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = email,
                                    onValueChange = { email = it },
                                    keyboardActions = KeyboardActions(
                                        onDone = {focusManager.clearFocus()}
                                    ),
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
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Mobile Number",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.063 * screenHeight),
                                    shape = RoundedCornerShape(size = 16.dp),
                                    placeholder = {
                                        Text(
                                            "CHANGE",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = mobile,
                                    onValueChange = { mobile = it },
                                    keyboardActions = KeyboardActions(
                                        onDone = {focusManager.clearFocus()}
                                    ),
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
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Batch",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.063 * screenHeight),
                                    shape = RoundedCornerShape(size = 16.dp),
                                    placeholder = {
                                        Text(
                                            "CHANGE",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = batch,
                                    onValueChange = { batch = it },
                                    keyboardActions = KeyboardActions(
                                        onDone = {focusManager.clearFocus()}
                                    ),
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
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Current Position",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.063 * screenHeight),
                                    shape = RoundedCornerShape(size = 16.dp),
                                    placeholder = {
                                        Text(
                                            "CHANGE",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = currentPos,
                                    onValueChange = { currentPos = it },
                                    keyboardActions = KeyboardActions(
                                        onDone = {focusManager.clearFocus()}
                                    ),
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
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Position Period",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.063 * screenHeight),
                                    shape = RoundedCornerShape(size = 16.dp),
                                    placeholder = {
                                        Text(
                                            "CHANGE",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = posPeriod,
                                    onValueChange = { posPeriod = it },
                                    keyboardActions = KeyboardActions(
                                        onDone = {focusManager.clearFocus()}
                                    ),
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
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ){
                                    Column(
                                        modifier = Modifier
                                            .weight(0.5f)
                                    ) {
                                        Text(
                                            text = "Alumni Status",
                                            fontFamily = latoFontFamily,
                                            color = PurpleOne,
                                            fontSize = 16.sp,
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                        )
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            ExposedDropdownMenuBox(
                                                expanded = expanded3,
                                                onExpandedChange = { expanded3 = it },
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                OutlinedTextField(
                                                    value = isAlumni,
                                                    onValueChange = { isAlumni = it },
                                                    readOnly = true,
                                                    placeholder = { Text("CHANGE") },
                                                    modifier = Modifier
                                                        .menuAnchor()
                                                        .fillMaxWidth(),
                                                    trailingIcon = {
                                                        Icon(
                                                            Icons.Default.ArrowDropDown,
                                                            contentDescription = "Dropdown"
                                                        )
                                                    },
                                                    shape = RoundedCornerShape(16.dp),
                                                    colors = OutlinedTextFieldDefaults.colors(
                                                        focusedBorderColor = PurpleOne,
                                                        unfocusedBorderColor = Color(0x66ABABAB),
                                                        focusedTextColor = Color(0xFFFFFFFF),
                                                        unfocusedTextColor = Color(0xFFFFFFFF),
                                                        unfocusedContainerColor = Color(0x14ABABAB),
                                                        focusedContainerColor = Color(0x14ABABAB)
                                                    )
                                                )

                                                ExposedDropdownMenu(
                                                    expanded = expanded3,
                                                    onDismissRequest = { expanded3 = false },
                                                    modifier = Modifier
                                                        .fillParentMaxWidth()
                                                        .background(Color(0xFFFFFFFF)),
                                                ) {
                                                    alumniOp.forEach { item ->
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
                                                                .fillMaxWidth()
                                                                .background(Color.White),
                                                            text = {
                                                                Text(
                                                                    item,
                                                                    fontFamily = latoFontFamily,
                                                                    fontSize = 16.sp,
                                                                    modifier = Modifier
                                                                        .fillMaxWidth(),
                                                                    textAlign = TextAlign.Center
                                                                )
                                                            },
                                                            onClick = {
                                                                isAlumni = item
                                                                focusManager.clearFocus()
                                                                expanded3 = false
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(
                                        modifier = Modifier
                                            .weight(0.5f)
                                    ) {
                                        Text(
                                            text = "Lab Access",
                                            fontFamily = latoFontFamily,
                                            color = PurpleOne,
                                            fontSize = 16.sp,
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                        )
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            ExposedDropdownMenuBox(
                                                expanded = expanded2,
                                                onExpandedChange = { expanded2 = it },
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                OutlinedTextField(
                                                    value = labAccess,
                                                    onValueChange = { labAccess = it },
                                                    readOnly = true,
                                                    placeholder = { Text("CHANGE") },
                                                    modifier = Modifier
                                                        .menuAnchor()
                                                        .fillMaxWidth(),
                                                    trailingIcon = {
                                                        Icon(
                                                            Icons.Default.ArrowDropDown,
                                                            contentDescription = "Dropdown"
                                                        )
                                                    },
                                                    shape = RoundedCornerShape(16.dp),
                                                    colors = OutlinedTextFieldDefaults.colors(
                                                        focusedBorderColor = PurpleOne,
                                                        unfocusedBorderColor = Color(0x66ABABAB),
                                                        focusedTextColor = Color(0xFFFFFFFF),
                                                        unfocusedTextColor = Color(0xFFFFFFFF),
                                                        unfocusedContainerColor = Color(0x14ABABAB),
                                                        focusedContainerColor = Color(0x14ABABAB)
                                                    )
                                                )

                                                ExposedDropdownMenu(
                                                    expanded = expanded2,
                                                    onDismissRequest = { expanded2 = false },
                                                    modifier = Modifier
                                                        .fillParentMaxWidth()
                                                        .background(Color(0xFFFFFFFF)),
                                                ) {
                                                    labAccOp.forEach { item ->
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
                                                                .fillMaxWidth()
                                                                .background(Color.White),
                                                            text = {
                                                                Text(
                                                                    item,
                                                                    fontFamily = latoFontFamily,
                                                                    fontSize = 16.sp,
                                                                    modifier = Modifier
                                                                        .fillMaxWidth(),
                                                                    textAlign = TextAlign.Center
                                                                )
                                                            },
                                                            onClick = {
                                                                labAccess = item
                                                                focusManager.clearFocus()
                                                                expanded2 = false
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Clearance Level",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    ExposedDropdownMenuBox(
                                        expanded = expanded,
                                        onExpandedChange = { expanded = it },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        OutlinedTextField(
                                            value = clearance,
                                            onValueChange = { clearance = it },
                                            readOnly = true,
                                            placeholder = { Text("CHANGE") },
                                            modifier = Modifier
                                                .menuAnchor()
                                                .fillMaxWidth(),
                                            trailingIcon = {
                                                Icon(
                                                    Icons.Default.ArrowDropDown,
                                                    contentDescription = "Dropdown"
                                                )
                                            },
                                            shape = RoundedCornerShape(16.dp),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = PurpleOne,
                                                unfocusedBorderColor = Color(0x66ABABAB),
                                                focusedTextColor = Color(0xFFFFFFFF),
                                                unfocusedTextColor = Color(0xFFFFFFFF),
                                                unfocusedContainerColor = Color(0x14ABABAB),
                                                focusedContainerColor = Color(0x14ABABAB)
                                            )
                                        )

                                        ExposedDropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false },
                                            modifier = Modifier
                                                .fillParentMaxWidth()
                                                .background(Color(0xFFFFFFFF)),
                                        ) {
                                            clearanceOp.forEach { item ->
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
                                                        .fillMaxWidth()
                                                        .background(Color.White),
                                                    text = {
                                                        Text(
                                                            item,
                                                            fontFamily = latoFontFamily,
                                                            fontSize = 16.sp,
                                                            modifier = Modifier
                                                                .fillMaxWidth(),
                                                            textAlign = TextAlign.Center
                                                        )
                                                    },
                                                    onClick = {
                                                        clearance = item
                                                        focusManager.clearFocus()
                                                        expanded = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Image Link",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.063 * screenHeight),
                                    shape = RoundedCornerShape(size = 16.dp),
                                    placeholder = {
                                        Text(
                                            "CHANGE",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = image,
                                    onValueChange = { image = it },
                                    keyboardActions = KeyboardActions(
                                        onDone = {focusManager.clearFocus()}
                                    ),
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
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                FloatingActionButton(
                                    onClick = {
                                        memberViewModel.updateMember(
                                            id = memberId,
                                            name = name,
                                            enrollment = enrollment,
                                            email = email,
                                            mobile = mobile,
                                            batch = batch,
                                            currentPos = currentPos,
                                            posPeriod = posPeriod,
                                            isAlumni = isAlumni == "True" || isAlumni == "true",
                                            labAccess = labAccess == "True" || labAccess == "true",
                                            clearance = clearance,
                                            image = image
                                        )
                                        db.child("memberId").setValue(memberId)
                                        db.child("memberUpdated").setValue(true)
                                        Toast.makeText(context, "Member Updated", Toast.LENGTH_SHORT).show()
                                        navController.navigate("home") {
                                            popUpTo("home") { inclusive = true }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    containerColor = PurpleOne,
                                    elevation = FloatingActionButtonDefaults.elevation(
                                        defaultElevation = 0.dp,
                                        pressedElevation = 0.dp,
                                        focusedElevation = 0.dp,
                                        hoveredElevation = 0.dp
                                    )
                                ) {
                                    Text(
                                        text = "Update",
                                        fontFamily = latoFontFamily,
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(0.3 * screenHeight))
                        }
                    }
                }
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun UpdMemberPagePreview(){
    UpdateMemberPage(rememberNavController(), "44b9e4d1-d809-4209-a9ce-f4cb99992c84")
}