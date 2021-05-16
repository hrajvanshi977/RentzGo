package com.india.rentzgo.data.properties

import com.india.rentzgo.data.base.Amenities
import com.india.rentzgo.data.base.Properties
import com.india.rentzgo.data.base.PropertyBaseInfo

 class IndividualRoom : PropertyBaseInfo {
    private var facing: String
    private var maxPeople: Int

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
        facing: String,
        maxPeople: Int
    ) : super(
        price,
        propertyId,
        propertiesType,
        advertisementTitle,
        propertyDescription,
        totalFloors,
        currentFloor,
        parkingFacility,
        amenities,
    ) {
        this.facing = facing
        this.maxPeople = maxPeople
    }
}
