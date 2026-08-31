package com.example.home.utils

import kotlinx.collections.immutable.PersistentSet

internal fun <T> PersistentSet<T>.toggled(element: T): PersistentSet<T> =
    if (element in this) removing(element = element) else adding(element = element)