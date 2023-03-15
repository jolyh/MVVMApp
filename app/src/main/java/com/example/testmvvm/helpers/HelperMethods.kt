package com.example.testmvvm.helpers

fun Any?.ifNullOrEmpty(default: String) =
    if (this == null || (this is CharSequence && this.isEmpty()))
        default
    else
        this.toString()