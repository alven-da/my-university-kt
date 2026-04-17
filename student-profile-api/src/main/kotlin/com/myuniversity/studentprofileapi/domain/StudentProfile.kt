package com.myuniversity.studentprofileapi.domain

data class StudentProfile (
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,

    // TODO: To be typed as an enum
    var gender: String? = null
)