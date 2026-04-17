package com.myuniversity.studentprofileapi.application

import com.myuniversity.studentprofileapi.adapter.dto.UpdateStudentProfileDto
import com.myuniversity.studentprofileapi.adapter.out.IStudentProfileRepository
import com.myuniversity.studentprofileapi.domain.StudentProfile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class StudentProfileServiceTest {
    private val studentRepo: IStudentProfileRepository = mock()
    private val studentProfileService: StudentProfileService = StudentProfileService(studentRepo)

    @Test
    fun `should update student profile`() {
        val studentId = "1"

        val existingProfile: StudentProfile = StudentProfile(
            id = studentId,
            firstName = "Alven",
            lastName = "Alinan",
            gender = "Male",
        )

        whenever(studentRepo.findById(studentId)).thenReturn(existingProfile)

        val mockDto: UpdateStudentProfileDto = UpdateStudentProfileDto()
        mockDto.firstName = "Alvin"
        mockDto.lastName = "Pat"

        whenever(studentRepo.save(any())).thenAnswer { invocation ->
            invocation.arguments[0] as StudentProfile
        }

        val result = studentProfileService.updateProfile(studentId, mockDto)

        assertEquals("Alvin", result.firstName)
        assertEquals("Pat", result.lastName)
    }
}