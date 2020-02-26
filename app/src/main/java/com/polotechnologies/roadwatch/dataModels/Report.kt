package com.polotechnologies.roadwatch.dataModels

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp


data class Report(
    var user_id: String = "",
    var incident: String = "",
    var area_county: String = "",
    var type_of_vehicle: String = "",
    var vehicle_number_plate: String = "",
    var vehicle_destination: String = "",
    @ServerTimestamp var date_time_reported: Timestamp? = null
)