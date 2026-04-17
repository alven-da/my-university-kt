package com.myuniversity.studentprofileapi.application

import com.myuniversity.studentprofileapi.adapter.dto.UpdateStudentProfileDto
import com.myuniversity.studentprofileapi.domain.StudentProfile

class StudentProfileService {
    fun getProfile(studentId: String): StudentProfile {
        val profile: StudentProfile = StudentProfile()

        profile.id = studentId
        profile.firstName = "Alven"
        profile.lastName = "Alinan"
        profile.gender = "Male"

        return profile
    }

    fun updateProfile(updateDto: UpdateStudentProfileDto): StudentProfile {
        val profile: StudentProfile = StudentProfile()

        profile.id = "1"
        profile.firstName = "Alven"
        profile.lastName = "Alinan"
        profile.gender = "Male"

        return profile
    }
}