package com.india.rentzgo.entities

class ProfileSettings {

    var startIcon: Int
    var optionTitle: String
    var optionSubtitle: String
    var endIcon: Int

    public constructor(
        startIcon: Int,
        optionTitle: String,
        optionSubtitle: String,
        endIcon: Int
    ) {
        this.startIcon = startIcon
        this.optionTitle = optionTitle
        this.optionSubtitle = optionSubtitle
        this.endIcon = endIcon
    }
}