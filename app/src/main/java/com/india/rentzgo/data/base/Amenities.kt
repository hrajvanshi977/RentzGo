package com.india.rentzgo.data.base

class Amenities {
    private var isFurnished: Boolean
    private var isBachelorAllowed: Boolean
    private var isDrinkingAllowed: Boolean
    private var isSmokingAllowed: Boolean
    private var isNonVegAllowed: Boolean
    private var hasWifiOrInternet: Boolean = false
    private var hasMessFacility: Boolean = false
    private var isAirConditioned: Boolean = false
    private var hasLift: Boolean = false

    constructor(
        isFurnished: Boolean,
        isBachelorAllowed: Boolean,
        isDrinkingAllowed: Boolean,
        isSmokingAllowed: Boolean,
        isNonVegAllowed: Boolean,
        hasWifiInternet: Boolean,
        hasMessFacility: Boolean,
        isAirConditioned: Boolean,
        hasLift: Boolean,
    ) {
        this.isFurnished = isFurnished
        this.isBachelorAllowed = isBachelorAllowed
        this.isDrinkingAllowed = isDrinkingAllowed
        this.isSmokingAllowed = isSmokingAllowed
        this.isNonVegAllowed = isNonVegAllowed
        this.hasWifiOrInternet = hasWifiOrInternet
        this.hasMessFacility = hasMessFacility
        this.isAirConditioned = isAirConditioned
        this.hasLift = hasLift
    }


    constructor(
        isFurnished: Boolean,
        isBachelorAllowed: Boolean,
        isDrinkingAllowed: Boolean,
        isSmokingAllowed: Boolean,
        isNonVegAllowed: Boolean,
    ) {
        this.isFurnished = isFurnished
        this.isBachelorAllowed = isBachelorAllowed
        this.isDrinkingAllowed = isDrinkingAllowed
        this.isSmokingAllowed = isSmokingAllowed
        this.isNonVegAllowed = isNonVegAllowed
    }
}