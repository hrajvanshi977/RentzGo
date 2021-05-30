package com.india.rentzgo.data

open class Property {
    private lateinit var propertyId: String
    private lateinit var ownerId: String
    private lateinit var propertyTitle: String
    private lateinit var propertyType: String
    private var isRented: Boolean = false
    private lateinit var price: String
    private lateinit var distance: String
    private lateinit var propertyPostedDate: String
    private var propertyStars: Int = 0
    private lateinit var address: String

    constructor(
        propertyId: String,
        ownerId: String,
        propertyTitle: String,
        propertyType: String,
        isRented: Boolean,
        price: String,
        distance: String,
        propertyPostedDate: String,
        propertyStars: Int,
        address: String
    ) {
        this.propertyId = propertyId
        this.ownerId = ownerId
        this.propertyTitle = propertyTitle
        this.propertyType = propertyType
        this.isRented = isRented
        this.price = price
        this.distance = distance
        this.propertyPostedDate = propertyPostedDate
        this.propertyStars = propertyStars
        this.address = address
    }

    constructor() {}

    fun getPropertyId(): String? {
        return propertyId
    }

    fun setPropertyId(propertyId: String?) {
        this.propertyId = propertyId!!
    }

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

    fun getPropertyStars(): Int {
        return propertyStars
    }

    fun setPropertyStars(propertyStars: Int) {
        this.propertyStars = propertyStars
    }

    fun getAddress(): String {
        return address
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun getPrice(): String {
        return price
    }

    fun setPrice(price: String) {
        this.price = price
    }

    fun getPropertyTitle(): String {
        return propertyTitle
    }

    fun setPropertyTitle(propertyTitle: String) {
        this.propertyTitle = propertyTitle
    }

    fun getDistance(): String {
        return distance
    }

    fun setDistance(distance: String) {
        this.distance = distance
    }
}