package com.india.rentzgo.data.base

open class PropertyBaseInfo {
    private var price: Int
    private var propertyId: String
    private var propertiesType: Properties
    private var advertisementTitle: String
    private var propertyDescription: String
    private var amenities: Amenities
    private var totalFloors: Int
    private var currentFloor: Int
    private var parkingFacility: ArrayList<String>

    constructor(
        price: Int,
        propertyId: String,
        propertiesType: Properties,
        advertisementTitle: String,
        propertyDescription: String,
        totalFloors: Int,
        currentFloor: Int,
        parkingFacility: ArrayList<String>,
        amenities: Amenities,
    ) {
        this.price = price
        this.propertyId = propertyId
        this.propertiesType = propertiesType
        this.advertisementTitle = advertisementTitle
        this.propertyDescription = propertyDescription
        this.totalFloors = totalFloors
        this.currentFloor = currentFloor
        this.parkingFacility = parkingFacility
        this.amenities = amenities
    }
}