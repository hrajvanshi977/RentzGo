package com.india.rentzgo.data.base

class Address {
     lateinit var country: String
     lateinit var state: String
     lateinit var city: String
     lateinit var landmark: String

    constructor(
        country: String,
        state: String,
        city: String,
        landmark: String
    ) {
        this.country = country
        this.state = state
        this.city = city
        this.landmark = landmark
    }
    constructor() {}

}