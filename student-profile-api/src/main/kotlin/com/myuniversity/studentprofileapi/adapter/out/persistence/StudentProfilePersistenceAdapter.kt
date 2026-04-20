package com.myuniversity.studentprofileapi.adapter.out.persistence

import com.myuniversity.studentprofileapi.domain.StudentProfile
import com.myuniversity.studentprofileapi.adapter.out.IStudentProfileRepository
import org.springframework.stereotype.Component

@Component
class StudentProfilePersistenceAdapter(
    private val studentProfileSpringRepo: StudentProfileSpringDataRepository
) : IStudentProfileRepository {

    override fun findById(id: String): StudentProfile? {
        return studentProfileSpringRepo.findById(id).map { entity ->
            StudentProfile(entity?.id, entity?.firstName, entity?.lastName, entity?.gender ?: "")
        }.orElse(null)
    }

    override fun save(studentProfile: StudentProfile): StudentProfile {
        val entity = StudentProfileEntity(
            id = studentProfile.id,
            firstName = studentProfile.firstName,
            lastName = studentProfile.lastName,
            gender = studentProfile.gender
        )
        val saved = studentProfileSpringRepo.save(entity)
        return StudentProfile(saved.id, saved.firstName, saved.lastName, saved.gender ?: "")
    }
}