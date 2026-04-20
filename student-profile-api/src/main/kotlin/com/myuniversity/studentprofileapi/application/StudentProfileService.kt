package com.myuniversity.studentprofileapi.application

import com.myuniversity.studentprofileapi.adapter.dto.UpdateStudentProfileDto
import com.myuniversity.studentprofileapi.adapter.out.IStudentProfileRepository
import com.myuniversity.studentprofileapi.domain.StudentProfile
import jakarta.persistence.EntityNotFoundException

class StudentProfileService(
    private val studentProfileRepo: IStudentProfileRepository
) {
    private fun getStudentProfile(studentId: String): StudentProfile {
        return studentProfileRepo.findById(studentId)?.apply {
            println("Profile retrieved for $studentId")
        } ?: throw EntityNotFoundException("Could not find student $studentId")
    }

    fun getProfile(studentId: String): StudentProfile {
        return getStudentProfile(studentId)
    }

    fun updateProfile(studentId: String, updateDto: UpdateStudentProfileDto): StudentProfile {
        // TODO: to optimize querying i.e. upsert API from repo
        val existing: StudentProfile = getStudentProfile(studentId)

        // Copy to updated object
        val updated: StudentProfile = existing.copy(
            firstName = updateDto.firstName ?: existing.firstName,
            lastName = updateDto.lastName ?: existing.lastName,
            gender = updateDto.gender ?: existing.gender
        )

        return studentProfileRepo.save(updated)
    }
}