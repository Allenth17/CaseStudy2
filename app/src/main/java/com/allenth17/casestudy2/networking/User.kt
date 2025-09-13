package com.allenth17.casestudy2.networking

data class User(
    val id: Int,
    val image: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val birthDate: String,
    val phone: String,
    val email: String,
    val company: Company
)

data class Company(
    val name: String,
    val title: String
)