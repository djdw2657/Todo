package com.dj.todo.data.models

import com.dj.todo.ui.theme.*

enum class Priority(val color: androidx.compose.ui.graphics.Color) {
    URGENT(UrgentPriorityColor),
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}