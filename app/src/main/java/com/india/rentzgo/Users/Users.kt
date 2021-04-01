package com.example.rentzgo.Users

class Users {
    var displayName: String
    var email: String
    var givenName: String
    var familyName: String
    var photoUrlString : String

    constructor(
        displayName: String,
        email: String,
        givenName: String,
        familyName: String,
        photoUrlString : String
    ) {
        this.displayName = displayName
        this.email = email
        this.givenName = givenName
        this.familyName = familyName
        this.photoUrlString = photoUrlString
    }
}