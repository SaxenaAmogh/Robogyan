package com.example.robogyan.view.secpages

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.robogyan.viewmodel.ProjectsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddProjectPage(navController: NavController){

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var head by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var github by remember { mutableStateOf("") }
    var pdf by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var start by remember { mutableStateOf("") }
    var complete by remember { mutableStateOf("") }
    var moneySpent by remember { mutableStateOf("") }
    var team by remember { mutableStateOf("") }
    var components by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val headOptions = listOf("Amogh", "Macle", "Ayush")
    var expanded2 by remember { mutableStateOf(false) }
    val statusOptions = listOf("In Progress", "Completed", "On Hold", "Abandoned")
    var expanded3 by remember { mutableStateOf(false) }
    val categoryOptions = listOf("Software", "Hardware", "Others")
    var showDatePicker by remember { mutableStateOf(false) }
    var showDatePicker2 by remember { mutableStateOf(false) }

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }

    val allMemberFlow: Flow<List<AllMembers>> =
        AppDatabase.getDatabase(context).allMembersDao().getAllMembers()
    val members by allMemberFlow.collectAsState(initial = emptyList())
    val projectViewModel: ProjectsViewModel = viewModel()
    var fetch by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (fetch){
            delay(200)
            projectViewModel.fetchProjects()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DatePickerModal(
        onDateSelected: (String) -> Unit, // Now directly returns formatted date string
        onDismiss: () -> Unit
    ) {
        val datePickerState = rememberDatePickerState()

        fun formatDate(millis: Long?): String {
            return if (millis != null) {
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = millis
                formatter.format(calendar.time)
            } else {
                ""
            }
        }

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    val formattedDate = formatDate(datePickerState.selectedDateMillis)
                    onDateSelected(formattedDate) // Send formatted string directly
                    onDismiss()
                }) {
                    Text(
                        text = "OK",
                        fontSize = 16.sp,
                        )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
        ) {
            DatePicker(state = datePickerState)
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
                                    text = "Add New Project",
                                    color = TextColor,
                                    fontSize = 24.sp,
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
                                    text = "Project Name",
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
                                            "Name",
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
                                    text = "Project Head",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
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
                                            value = head,
                                            onValueChange = { head = it },
                                            readOnly = true,
                                            placeholder = { Text("Select Project Head") },
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

                                        val leads = members
                                            .filter {
                                            it.is_alumni == false &&
                                                    (it.clearance == "Lead" || it.clearance == "President" || it.clearance == "Vice President")
                                            }

                                        ExposedDropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false },
                                            modifier = Modifier
                                                .fillParentMaxWidth()
                                                .background(Color(0xFFFFFFFF)),
                                        ) {
                                            leads.forEach { item ->
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
                                                            item.name,
                                                            fontFamily = latoFontFamily,
                                                            fontSize = 16.sp,
                                                            modifier = Modifier
                                                                .fillMaxWidth(),
                                                            textAlign = TextAlign.Center
                                                        )
                                                    },
                                                    onClick = {
                                                        head = item.id
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
                                    text = "Project Desctiption",
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
                                            "Description",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = description,
                                    onValueChange = { description = it },
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
                                    text = "Status",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
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
                                            value = status,
                                            onValueChange = { status = it },
                                            placeholder = { Text("Select Status") },
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
                                            statusOptions.forEach { item ->
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
                                                        status = item
                                                        focusManager.clearFocus()
                                                        expanded2 = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Github Link",
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
                                            "Github",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = github,
                                    onValueChange = { github = it },
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
                                    text = "PDF Link",
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
                                            "PDF",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = pdf,
                                    onValueChange = { pdf = it },
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
                                            text = "Category",
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
                                                    value = category,
                                                    onValueChange = { category = it },
                                                    readOnly = true,
                                                    placeholder = { Text("Select") },
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
                                                    categoryOptions.forEach { item ->
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
                                                                category = item
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
                                            text = "Money Spent",
                                            fontFamily = latoFontFamily,
                                            color = PurpleOne,
                                            fontSize = 16.sp,
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                        )
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .height(0.063 * screenHeight),
                                            shape = RoundedCornerShape(size = 16.dp),
                                            placeholder = {
                                                Text(
                                                    "Rs. Spent",
                                                    fontFamily = latoFontFamily,
                                                    color = Color(0xFFB2B2B2),
                                                )
                                            },
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Number
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onDone = {focusManager.clearFocus()}
                                            ),
                                            value = moneySpent,
                                            onValueChange = { moneySpent = it },
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
                                    }
                                }
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Start Date",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { showDatePicker = true }
                                        .height(0.063 * screenHeight)
                                        .background(
                                            color = Color(0x14ABABAB),
                                            shape = RoundedCornerShape(16.dp),
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (showDatePicker){ PurpleOne } else { Color(0x66ABABAB) },
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .clickable(
                                            onClick = { showDatePicker = true },
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        )
                                ){
                                    Text(
                                        text = if (start.isEmpty()) "yyyy-mm-dd" else start,
                                        modifier = Modifier
                                            .padding(start = 12.dp)
                                            .align(Alignment.CenterStart),
                                        fontFamily = latoFontFamily,
                                        color = Color(0xFFFFFFFF),
                                        fontSize = 16.sp,
                                    )
                                    Icon(
                                        imageVector = Icons.Rounded.DateRange,
                                        contentDescription = "Date Icon",
                                        tint = Color(0xFFB2B2B2),
                                        modifier = Modifier
                                            .align(Alignment.CenterEnd)
                                            .padding(end = 12.dp)
                                    )
                                }
                                if (showDatePicker) {
                                    DatePickerModal(
                                        onDateSelected = { dateString ->
                                            // If you want to store full date in viewModel.date:
                                            start = dateString
                                            showDatePicker = false
                                        },
                                        onDismiss = { showDatePicker = false }
                                    )

                                }
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "End Date",
                                    fontFamily = latoFontFamily,
                                    color = PurpleOne,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { showDatePicker2 = true }
                                        .height(0.063 * screenHeight)
                                        .background(
                                            color = Color(0x14ABABAB),
                                            shape = RoundedCornerShape(16.dp),
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (showDatePicker2){ PurpleOne } else { Color(0x66ABABAB) },
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .clickable(
                                            onClick = { showDatePicker2 = true },
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        )
                                ){
                                    Text(
                                        text = if (complete.isEmpty()) "yyyy-mm-dd" else complete,
                                        modifier = Modifier
                                            .padding(start = 12.dp)
                                            .align(Alignment.CenterStart),
                                        fontFamily = latoFontFamily,
                                        color = Color(0xFFFFFFFF),
                                        fontSize = 16.sp,
                                    )
                                    Icon(
                                        imageVector = Icons.Rounded.DateRange,
                                        contentDescription = "Date Icon",
                                        tint = Color(0xFFB2B2B2),
                                        modifier = Modifier
                                            .align(Alignment.CenterEnd)
                                            .padding(end = 12.dp)
                                    )
                                }
                                if (showDatePicker2) {
                                    DatePickerModal(
                                        onDateSelected = { dateString ->
                                            // If you want to store full date in viewModel.date:
                                            complete = dateString
                                            showDatePicker2 = false
                                        },
                                        onDismiss = { showDatePicker2 = false }
                                    )

                                }
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Team Members",
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
                                            "A, B, C",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = team,
                                    onValueChange = { team = it },
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
                                    text = "Components",
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
                                            "X, Y, Z",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = components,
                                    onValueChange = { components = it },
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
                                        projectViewModel.addNewProject(
                                            name = name,
                                            project_head = head,
                                            status = status,
                                            github_link = github,
                                            pdf_link = pdf,
                                            category = category,
                                            description = description,
                                            start_date = start,
                                            completion_date = complete,
                                            money_spent = moneySpent,
                                            team = team,
                                            components = components
                                        )
                                        Toast.makeText(context, "Project Added", Toast.LENGTH_SHORT).show()
                                        fetch = true
                                        navController.popBackStack()
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
                                        text = "Save",
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
fun AddPagePreview(){
    AddProjectPage(rememberNavController())
}