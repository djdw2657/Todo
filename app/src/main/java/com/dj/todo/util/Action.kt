package com.dj.todo.util

enum class Action {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    SETTING,
    NO_ACTION
}

fun String?.toAction(): Action {
    return when {
        this == "ADD" -> {
            Action.ADD
        }
        this == "DELETE" -> {
            Action.DELETE
        }
        this == "UPDATE" -> {
            Action.UPDATE
        }
        this == "DELETE_ALL" -> {
            Action.DELETE_ALL
        }
        this == "UNDO" -> {
            Action.UNDO
        }
        this == "SETTING" -> {
            Action.SETTING
        }
        else -> {
            Action.NO_ACTION
        }
    }
}