package com.example.supabasetest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.supabasetest.model.Student
import com.example.supabasetest.viewmodel.MyViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudentsListScreen(navigateToNewStudent: () -> Unit, navigateToDetail: (String) -> Unit) {
    val myViewModel = viewModel<MyViewModel>()
    val studentsList by myViewModel.studentsList.observeAsState(emptyList<Student>())
    myViewModel.getAllStudents()
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(0.2f), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { navigateToNewStudent() }) {
                Text("New Student")
            }
        }
        Text(
            "Students List",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        LazyColumn(Modifier
            .fillMaxWidth()
            .weight(0.6f)) {
            items(items = studentsList) { student ->
                val dismissState = rememberSwipeToDismissBoxState()
                if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart &&
                    dismissState.targetValue == SwipeToDismissBoxValue.EndToStart
                ) {
                    LaunchedEffect(Unit) {
                        myViewModel.deleteStudent(student.id.toString(), student.image.toString())
                    }
                }
                SwipeToDismissBox(state = dismissState, backgroundContent = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Red),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }) {
                    StudentItem(student) { navigateToDetail(student.id.toString()) }
                }
            }
        }

    }
}

@Composable
fun StudentItem(student: Student, navigateToDetail: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .border(width = 2.dp, Color.DarkGray)
            .clickable { navigateToDetail(student.id.toString()) }) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(student.name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(text = "Mark: ${student.mark}")
        }
    }
}
