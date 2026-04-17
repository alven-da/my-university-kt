package com.myuniversity.studentprofileapi.adapter.out

import com.myuniversity.studentprofileapi.domain.StudentProfile

interface IStudentProfileRepository {
    fun findById(id: String): StudentProfile
    fun save(studentProfile: StudentProfile): StudentProfile
}