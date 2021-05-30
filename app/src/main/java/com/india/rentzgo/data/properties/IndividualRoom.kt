package com.india.rentzgo.data.properties

import com.india.rentzgo.data.Property

class IndividualRoom : Property {
    private lateinit var maxPeople: String
    private lateinit var furnishing: String
    private lateinit var facing: String
    private lateinit var totalFloors: String
    private lateinit var currentFloors: String
    private lateinit var parkingFacility: String
    private var bachelorsAllowed: Boolean = false
    private var nonVegAllowed: Boolean = false
    private var drinkAndSmokingAllowed: Boolean = false
    private lateinit var propertyDescription: String

    constructor() {}

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
        address: String,
        maxPeople: String,
        furnishing: String,
        facing: String,
        totalFloors: String,
        currentFloors: String,
        parkingFacility: String,
        bachelorsAllowed: Boolean,
        nonVegAllowed: Boolean,
        drinkAndSmokingAllowed: Boolean,
        propertyDescription: String,
    ) : super(
        propertyId,
        ownerId,
        propertyTitle,
        propertyType,
        isRented,
        price,
        distance,
        propertyPostedDate,
        propertyStars,
        address,
    ) {
        this.maxPeople = maxPeople
        this.furnishing = furnishing
        this.facing = facing
        this.totalFloors = totalFloors
        this.currentFloors = currentFloors
        this.parkingFacility = parkingFacility
        this.bachelorsAllowed = bachelorsAllowed
        this.nonVegAllowed = nonVegAllowed
        this.drinkAndSmokingAllowed = drinkAndSmokingAllowed
        this.propertyDescription = propertyDescription
    }

    fun getMaxPeople(): String? {
        return maxPeople
    }

    fun setMaxPeople(maxPeople: String?) {
        this.maxPeople = maxPeople!!
    }

    fun getFurnishing(): String? {
        return furnishing
    }

    fun setFurnishing(furnishing: String?) {
        this.furnishing = furnishing!!
    }

    fun getFacing(): String? {
        return facing
    }

    fun setFacing(facing: String?) {
        this.facing = facing!!
    }

    fun getTotalFloors(): String? {
        return totalFloors
    }

    fun setTotalFloors(totalFloors: String?) {
        this.totalFloors = totalFloors!!
    }

    fun getCurrentFloors(): String? {
        return currentFloors
    }

    fun setCurrentFloors(currentFloors: String?) {
        this.currentFloors = currentFloors!!
    }

    fun getParkingFacility(): String? {
        return parkingFacility
    }

    fun setParkingFacility(parkingFacility: String?) {
        this.parkingFacility = parkingFacility!!
    }

    fun isBachelorsAllowed(): Boolean? {
        return bachelorsAllowed
    }

    fun setBachelorsAllowed(bachelorsAllowed: Boolean?) {
        this.bachelorsAllowed = bachelorsAllowed!!
    }

    fun isNonVegAllowed(): Boolean? {
        return nonVegAllowed
    }

    fun setNonVegAllowed(nonVegAllowed: Boolean?) {
        this.nonVegAllowed = nonVegAllowed!!
    }

    fun isDrinkAndSmokingAllowed(): Boolean? {
        return drinkAndSmokingAllowed
    }

    fun setDrinkAndSmokingAllowed(drinkAndSmokingAllowed: Boolean?) {
        this.drinkAndSmokingAllowed = drinkAndSmokingAllowed!!
    }

    fun getPropertyDescription(): String? {
        return propertyDescription
    }

    fun setPropertyDescription(propertyDescription: String?) {
        this.propertyDescription = propertyDescription!!
    }
}
