package com.polotechnologies.roadwatch.dataModels

data class Report (
    var user_id : String = "",
    var incident : String = "",
    var area_county : String = "",
    var type_of_vehicle : String = "",
    var vehicle_number_plate : String = "",
    var vehicle_destination : String = ""
)