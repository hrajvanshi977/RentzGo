package com.example.rentzgo.data.Users

import java.util.*

class Users {
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String
    private lateinit var photoUrlString: String
    private lateinit var properties: ArrayList<String>
    private lateinit var userChats: ArrayList<String>

    constructor() {}
    constructor(
        firstName: String,
        lastName: String,
        email: String,
        photoUrlString: String,
        properties: ArrayList<String>,
        userChats: ArrayList<String>
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.photoUrlString = photoUrlString
        this.properties = properties
        this.userChats = userChats
    }

    fun getFirstName(): String? {
        return firstName
    }

    fun setFirstName(firstName: String?) {
        this.firstName = firstName!!
    }

    fun getLastName(): String? {
        return lastName
    }

    fun setLastName(lastName: String?) {
        this.lastName = lastName!!
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email!!
    }

    fun getPhotoUrlString(): String? {
        return photoUrlString
    }

    fun setPhotoUrlString(photoUrlString: String?) {
        this.photoUrlString = photoUrlString!!
    }

    fun getProperties(): ArrayList<String>? {
        return properties
    }

    fun setProperties(properties: ArrayList<String>?) {
        this.properties = properties!!
    }

    fun getUserChats(): ArrayList<String> {
        return userChats
    }

    fun setUserChats(userChats: ArrayList<String>) {
        this.userChats = userChats
    }
}