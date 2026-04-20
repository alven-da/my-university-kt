package com.myuniversity.studentprofileapi.application

import com.myuniversity.studentprofileapi.adapter.dto.UpdateStudentProfileDto
import com.myuniversity.studentprofileapi.adapter.out.IStudentProfileRepository
import com.myuniversity.studentprofileapi.domain.StudentProfile
import io.mockk.every
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class StudentProfileServiceTest {
    private val studentRepo: IStudentProfileRepository = mock()
    private val studentProfileService: StudentProfileService = StudentProfileService(studentRepo)

    @Test
    fun `should retrieve a student profile`() {
        val studentId = "1"

        val mockProfile: StudentProfile = StudentProfile(
            id = "1",
            firstName = "Alven",
            lastName = "Alinan",
            gender = "Male"
        )

        whenever(studentRepo.findById(studentId)).thenReturn(mockProfile)

        val result = studentProfileService.getProfile(studentId)

        assertEquals("Alven", result.firstName)
        assertEquals("Alinan", result.lastName)
    }

    @Test
    fun `should throw exception when no record found`() {
        val studentId = "1"

        whenever(studentRepo.findById(studentId)).thenReturn(null)

        assertThrows<EntityNotFoundException> {
            studentProfileService.getProfile(studentId)
        }
    }

    @Test
    fun `should update student profile with only provided fields`() {
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

    @Test
    fun `should throw NotFoundException when no record found during the update`() {
        val studentId = "1"
        val mockDto: UpdateStudentProfileDto = UpdateStudentProfileDto()

        mockDto.firstName = "Alvin"
        mockDto.lastName = "Pat"

        whenever(studentRepo.save(any())).thenReturn(null)

        assertThrows<EntityNotFoundException> {
            studentProfileService.updateProfile(studentId,mockDto)
        }
    }
}