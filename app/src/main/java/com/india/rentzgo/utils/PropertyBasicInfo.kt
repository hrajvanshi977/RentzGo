package com.india.rentzgo.utils

import com.india.rentzgo.data.base.Address

class PropertyBasicInfo {
    lateinit var distanceInKilometer : String
    lateinit var price : String
    lateinit var address : Address
    lateinit var stars : String
    lateinit var postedDate : String

    constructor(distanceInKilometer: String, price : String, address : Address, stars : String, postedDate : String) {
        this.distanceInKilometer = distanceInKilometer
        this.price = price
        this.address = address
        this.stars = stars
        this.postedDate = postedDate
    }
}