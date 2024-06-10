package com.example.collectorapp.ui.screens.Goals

sealed class EnumGoals(val goalDetails: GoalDetails){
    data object Starter: EnumGoals(GoalDetails.Starter)
    data object Collector: EnumGoals(goalDetails = GoalDetails.Collector)
    data object Packrat: EnumGoals(goalDetails = GoalDetails.Packrat)
}

sealed class GoalDetails(val goalDescription: String){
    data object Starter: GoalDetails(goalDescription = "Add the first Item into the app")
    data object Collector: GoalDetails(goalDescription = "Add three items to the app")
    data object Packrat: GoalDetails(goalDescription = "Added 10 items to the app")
}
