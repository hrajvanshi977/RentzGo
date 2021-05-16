package com.example.rentzgo.data.Users

class Users {
    private var firstName : String
    private var lastName : String
    private var email: String
    private var photoUrlString : String

    constructor(
        firstName: String,
        lastName: String,
        email: String,
        photoUrlString: String
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.photoUrlString = photoUrlString
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
}