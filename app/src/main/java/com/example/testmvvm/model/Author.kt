package com.example.testmvvm.model

import com.example.testmvvm.helpers.DEFAULT_AUTHOR_EMAIL
import com.example.testmvvm.helpers.DEFAULT_AUTHOR_ID
import com.example.testmvvm.helpers.DEFAULT_AUTHOR_NAME
import com.example.testmvvm.helpers.DEFAULT_AUTHOR_TYPE

data class Author(
    val id : String = DEFAULT_AUTHOR_ID,
    val name: String = DEFAULT_AUTHOR_NAME,
    val authorType: AuthorType = DEFAULT_AUTHOR_TYPE,
    val email: String = DEFAULT_AUTHOR_EMAIL
)