package com.myuniversity.studentprofileapi.application

import com.myuniversity.studentprofileapi.adapter.dto.UpdateStudentProfileDto
import com.myuniversity.studentprofileapi.adapter.out.IStudentProfileRepository
import com.myuniversity.studentprofileapi.domain.StudentProfile

class StudentProfileService(
    private val studentProfileRepo: IStudentProfileRepository
) {
    fun getProfile(studentId: String): StudentProfile {
        val profile: StudentProfile = StudentProfile(
            id = studentId,
            firstName = "Alven",
            lastName = "Alinan",
            gender = "Male"
        )

        return profile
    }

    fun updateProfile(studentId: String, updateDto: UpdateStudentProfileDto): StudentProfile {
        // TODO: to optimize querying i.e. upsert API from repo
        val existing: StudentProfile = studentProfileRepo.findById(studentId)

        // Copy to updated object
        val updated: StudentProfile = existing.copy(
            firstName = updateDto.firstName ?: existing.firstName,
            lastName = updateDto.lastName ?: existing.lastName,
            gender = updateDto.gender ?: existing.gender
        )

        studentProfileRepo.save(updated)

        return updated
    }
}