package com.india.rentzgo

object CurrentPostingProperty {
    lateinit var propertyTitle: String
    lateinit var maxPeople: String
    lateinit var furnishing: String
    lateinit var facing: String
    lateinit var totalFloors: String
    lateinit var currentFloor: String
    lateinit var parkingFacility: String
    lateinit var propertyDescription: String
    var bachelorsAllowed: Boolean = false
    var drinkAndSmokingAllowed: Boolean = false
    var nonVegAllowed: Boolean = false
    lateinit var uniqueId: String
    lateinit var PropertyType: String
    var images = ArrayList<ByteArray>()
}