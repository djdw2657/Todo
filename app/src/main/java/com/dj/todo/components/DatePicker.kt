package com.dj.todo.components

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dj.todo.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import java.util.*


@Composable
fun DatePicker(
    date: String,
    onDateChange: (String) -> Unit
) {

    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }
    val zeroText = "0"
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear, mMonth, mDayOfMonth ->
            if (mMonth < 9) {
                val selectedDate = "${mYear}-${zeroText}${mMonth + 1}-${mDayOfMonth}"
                mDate.value = selectedDate
                onDateChange(selectedDate)
                if (mDayOfMonth < 10) {
                    val selectedDate = "${mYear}-${zeroText}${mMonth + 1}-${zeroText}${mDayOfMonth}"
                    onDateChange(selectedDate)
                }
            } else {
                val selectedDate = "${mYear}-${mMonth + 1}-${mDayOfMonth}"
                mDate.value = selectedDate
                onDateChange(selectedDate)
            }
        }, mYear, mMonth, mDay
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(PRIORITY_DROP_DOWN_HEIGHT)
            .clickable { mDatePickerDialog.show() }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                shape = MaterialTheme.shapes.small
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Complete Date: $date", style = MaterialTheme.typography.subtitle2)
        Log.d("DatePicker", "DatePicker: $date")
    }
}