package com.example.collectorapp.ui.screens.Goals

sealed class EnumGoals(val goalDetails: GoalDetails){
    data object Starter: EnumGoals(GoalDetails.firstGoal)
    data object Collector: EnumGoals(goalDetails = GoalDetails.secondGoal)
    data object Packrat: EnumGoals(goalDetails = GoalDetails.lastGoal)
}

sealed class GoalDetails(val goalDescription: String){
    data object firstGoal: GoalDetails(goalDescription = "Add the first Item into the app")
    data object secondGoal: GoalDetails(goalDescription = "Add three items to the app")
    data object lastGoal: GoalDetails(goalDescription = "Added 10 items to the app")
}
