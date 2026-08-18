package com.example.utils

fun randomNumberPadRows(): List<List<String>> {
    val shuffledNumbers = (0..9).map { it.toString() }.shuffled()
    return listOf(
        shuffledNumbers.subList(fromIndex = 0, toIndex = 3),
        shuffledNumbers.subList(fromIndex = 3, toIndex = 6),
        shuffledNumbers.subList(fromIndex = 6, toIndex = 9),
        listOf("", shuffledNumbers[9], "←")
    )
}