package com.example.robogyan.view.secpages

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.robogyan.data.local.entities.AssetUsage
import com.example.robogyan.ui.theme.BackgroundColor
import com.example.robogyan.ui.theme.PurpleOne
import com.example.robogyan.ui.theme.TextColor
import com.example.robogyan.ui.theme.latoFontFamily
import com.example.robogyan.viewmodel.AssetUsageViewModel
import com.example.robogyan.viewmodel.ProjectsViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddUsagePage(navController: NavController, assetId: Int, usageId: Int) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var approvedBy by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var useCase by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var projectName by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
    if (windowInsetsController != null) {
        windowInsetsController.isAppearanceLightStatusBars = false
    }

    val assetUsageViewModel: AssetUsageViewModel = viewModel()
    val db = FirebaseDatabase.getInstance().getReference("DbUpdate")

    var remoteVersion by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        val dbRef = FirebaseDatabase.getInstance().getReference("DbUpdate/remoteVersion")
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val version = snapshot.getValue(Int::class.java) ?: 0
                remoteVersion = version
                Log.d("&&Firebase", "Remote version fetched: $version")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("&&Firebase", "Fetch cancelled: ${error.message}")
            }
        })
    }

    val allMemberFlow: Flow<List<AllMembers>> =
        AppDatabase.getDatabase(context).allMembersDao().getAllMembers()
    val members by allMemberFlow.collectAsState(initial = emptyList())

    val assetUsageFlow: Flow<AssetUsage?> =
        AppDatabase.getDatabase(context).assetUsageDao().getUsageByUsageId(usageId)
    val updateUsage by assetUsageFlow.collectAsState(initial = null)
    
    if (updateUsage != null && usageId!=0) {
        name = updateUsage!!.granted_to
        approvedBy = updateUsage!!.approved_by
        quantity = updateUsage!!.quantity.toString()
        useCase = updateUsage!!.use_case
        status = updateUsage!!.status
        projectName = updateUsage!!.project_name.toString()
        returnDate = updateUsage!!.return_date.toString()
        notes = updateUsage!!.notes.toString()
    }
    
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DatePickerModal(
        onDateSelected: (String) -> Unit,
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
                        .pointerInput(Unit) {
                            detectTapGestures {
                                focusManager.clearFocus()
                            }
                        }
                ) {
                    LazyColumn {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .border(
                                            width = 3.dp,
                                            color = Color(0xFF3872D9),
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
                                        tint = Color(0xFF3872D9)
                                    )
                                }
                                Text(
                                    text = "Edit Asset Usage",
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
                                    text = "Granted To",
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
                                            value = name,
                                            onValueChange = { name = it },
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
                                                        name = item.id
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
                                    text = "Approved By",
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
                                            value = approvedBy,
                                            onValueChange = { approvedBy = it },
                                            readOnly = true,
                                            placeholder = { Text("Select Approved by") },
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

                                        val approved = members
                                            .filter {
                                                it.is_alumni == false &&
                                                        (it.clearance == "President" || it.clearance == "Vice President")
                                            }

                                        ExposedDropdownMenu(
                                            expanded = expanded2,
                                            onDismissRequest = { expanded2 = false },
                                            modifier = Modifier
                                                .background(Color(0xFFFFFFFF)),
                                        ) {
                                            approved.forEach { item ->
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
                                                        approvedBy = item.name
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
                                    text = "Use Case ",
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
                                            "Used for",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = useCase,
                                    onValueChange = { useCase = it },
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
                                    text = "Project Name ",
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
                                            "Project Name",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = projectName,
                                    onValueChange = { projectName = it },
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
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .weight(0.5f)
                                    ) {
                                        Text(
                                            text = "Quantity",
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
                                                    "Enter Quantity",
                                                    fontFamily = latoFontFamily,
                                                    color = Color(0xFFB2B2B2),
                                                )
                                            },
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Number
                                            ),
                                            value = quantity,
                                            onValueChange = { quantity = it },
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
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(
                                        modifier = Modifier
                                            .weight(0.5f)
                                    ) {
                                        Text(
                                            text = "Status",
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
                                                    "Select Status",
                                                    fontFamily = latoFontFamily,
                                                    color = Color(0xFFB2B2B2),
                                                )
                                            },
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Number
                                            ),
                                            value = status,
                                            onValueChange = { status = it },
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
                                    text = "Return Date",
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
                                            color = if (showDatePicker) {
                                                PurpleOne
                                            } else {
                                                Color(0x66ABABAB)
                                            },
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .clickable(
                                            onClick = { showDatePicker = true },
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        )
                                ) {
                                    Text(
                                        text = if (returnDate.isEmpty()) "yyyy-mm-dd" else returnDate,
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
                                            returnDate = dateString
                                            showDatePicker = false
                                        },
                                        onDismiss = { showDatePicker = false }
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.02 * screenHeight))
                                Text(
                                    text = "Notes",
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
                                        .height(0.15 * screenHeight),
                                    shape = RoundedCornerShape(size = 16.dp),
                                    placeholder = {
                                        Text(
                                            "Additional Notes",
                                            fontFamily = latoFontFamily,
                                            color = Color(0xFFB2B2B2),
                                        )
                                    },
                                    value = notes,
                                    onValueChange = { notes = it },
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
                                        if (usageId != 0) {
                                            assetUsageViewModel.updateUsage(
                                                id = usageId,
                                                assetId = assetId,
                                                name = name,
                                                approvedBy = approvedBy,
                                                quantity = quantity,
                                                useCase = useCase,
                                                status = status,
                                                projectName = projectName,
                                                returnDate = returnDate,
                                                notes = notes
                                            )
                                            db.child("remoteVersion").setValue(remoteVersion + 1)
                                            db.child("usageId").setValue(usageId)
                                            Toast.makeText(
                                                context,
                                                "New Usage Added",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            navController.navigate("home") {
                                                popUpTo("home") { inclusive = true }
                                            }
                                        } else {
                                            assetUsageViewModel.addNewUsage(
                                                assetId = assetId,
                                                name = name,
                                                approvedBy = approvedBy,
                                                quantity = quantity,
                                                useCase = useCase,
                                                status = status,
                                                projectName = projectName,
                                                returnDate = returnDate,
                                                notes = notes
                                            )
                                            db.child("usageId").setValue(usageId)
                                            db.child("usageUpdated").setValue(true)
                                            Toast.makeText(
                                                context,
                                                "New Usage Added",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            navController.navigate("home") {
                                                popUpTo("home") { inclusive = true }
                                            }
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
                                        text = "Save",
                                        fontFamily = latoFontFamily,
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                                Spacer(modifier = Modifier.height(0.3 * screenHeight))
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
fun AddUsagePagePreview() {
    AddUsagePage(rememberNavController(), 0, 1)
}