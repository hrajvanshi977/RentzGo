package com.india.rentzgo.data

import com.india.rentzgo.data.base.Address

class Property {
    private lateinit var ownerId: String
    private lateinit var propertyType: String
    private var isRented: Boolean = false
    private var distanceInKilometer: Int = 0
    private lateinit var price: String
    private lateinit var propertyPostedDate: String
    private lateinit var propertyRentedDate: String
    private var propertyStars: Int = 0
    private lateinit var rentedBy: String
    private lateinit var address: Address


    constructor(
        ownerId: String,
        propertyType: String,
        isRented: Boolean,
        distanceInKilometer: Int,
        price: String,
        propertyPostedDate: String,
        propertyRentedDate: String,
        propertyStars: Int,
        rentedBy: String,
        address: Address
    ) {
        this.ownerId = ownerId
        this.propertyType = propertyType
        this.isRented = isRented
        this.distanceInKilometer = distanceInKilometer
        this.price = price
        this.propertyPostedDate = propertyPostedDate
        this.propertyRentedDate = propertyRentedDate
        this.propertyStars = propertyStars
        this.rentedBy = rentedBy
        this.address = address
    }

    constructor() {}

    fun getOwnerId(): String? {
        return ownerId
    }

    fun setOwnerId(ownerId: String?) {
        this.ownerId = ownerId!!
    }

    fun getPropertyType(): String? {
        return propertyType
    }

    fun setPropertyType(propertyType: String?) {
        this.propertyType = propertyType!!
    }

    fun isRented(): Boolean {
        return isRented
    }

    fun setRented(rented: Boolean) {
        isRented = rented
    }

    fun getPropertyPostedDate(): String {
        return propertyPostedDate
    }

    fun setPropertyPostedDate(propertyPostedDate: String) {
        this.propertyPostedDate = propertyPostedDate
    }

    fun getPropertyRentedDate(): String {
        return propertyRentedDate
    }

    fun setPropertyRentedDate(propertyRentedDate: String) {
        this.propertyRentedDate = propertyRentedDate
    }

    fun getPropertyStars(): Int {
        return propertyStars
    }

    fun setPropertyStars(propertyStars: Int) {
        this.propertyStars = propertyStars
    }

    fun getRentedBy(): String? {
        return rentedBy
    }

    fun setRentedBy(rentedBy: String?) {
        this.rentedBy = rentedBy!!
    }

    fun getAddress(): Address {
        return address
    }

    fun setAddress(address: Address) {
        this.address = address
    }

    fun getPrice(): String {
        return price
    }

    fun setPrice(price: String) {
        this.price = price
    }

    fun getDistanceInKilometer(): Int {
        return distanceInKilometer
    }

    fun setDistanceInKilometer(distanceInKilometer: Int) {
        this.distanceInKilometer = distanceInKilometer
    }

}