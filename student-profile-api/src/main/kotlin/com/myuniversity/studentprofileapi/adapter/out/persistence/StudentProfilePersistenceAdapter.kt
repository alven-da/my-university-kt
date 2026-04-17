package com.myuniversity.student.adapter.out.persistence


import com.myuniversity.studentprofileapi.domain.StudentProfile
import com.myuniversity.studentprofileapi.adapter.out.IStudentProfileRepository
import com.myuniversity.studentprofileapi.adapter.out.persistence.StudentProfileEntity
import com.myuniversity.studentprofileapi.adapter.out.persistence.StudentProfileSpringDataRepository
import org.springframework.stereotype.Component

@Component
class StudentProfilePersistenceAdapter(
    private val springRepository: StudentProfileSpringDataRepository
) : IStudentProfileRepository {

    override fun findById(id: String): StudentProfile? {
        return springRepository.findById(id).map { entity ->
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
        val saved = springRepository.save(entity)
        return StudentProfile(saved.id, saved.firstName, saved.lastName, saved.gender ?: "")
    }
}