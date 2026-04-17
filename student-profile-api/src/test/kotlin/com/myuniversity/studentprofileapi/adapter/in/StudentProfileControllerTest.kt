package com.myuniversity.studentprofileapi.adapter.`in`

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

import org.mockito.kotlin.whenever
import org.mockito.kotlin.any

import com.myuniversity.studentprofileapi.adapter.dto.UpdateStudentProfileDto
import com.myuniversity.studentprofileapi.application.StudentProfileService
import com.myuniversity.studentprofileapi.domain.StudentProfile

import org.junit.jupiter.api.Test


import org.mockito.Mockito.`when`
import org.mockito.kotlin.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [StudentProfileController::class])
class StudentProfileControllerTest {


    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var studentProfileService: StudentProfileService

    @Test
    fun `should return 200 when getting student profile`() {
        val studentId = "1"
        val firstName = "Alven"
        val mockProfile: StudentProfile = StudentProfile()

        mockProfile.id = studentId
        mockProfile.firstName = firstName
        mockProfile.lastName = "Alinan"
        mockProfile.gender = "Male"

        `when`(studentProfileService.getProfile(studentId)).thenReturn(mockProfile)

        mockMvc.perform(get("/profile/$studentId")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(studentId))
            .andExpect(jsonPath("$.firstName").value(firstName))

    }

    @Test
    fun `should update student profile`() {
        val studentId = "1"
        val firstName = "Alvin"
        val mapper = jacksonObjectMapper()
        val mockProfile: StudentProfile = StudentProfile()
        val mockDto: UpdateStudentProfileDto = UpdateStudentProfileDto()

        mockDto.firstName = firstName
        mockDto.lastName = "Alinan"
        mockDto.gender = "Male"

        val jsonReq = mapper.writeValueAsString(mockDto)

        mockProfile.id = studentId
        mockProfile.firstName = firstName
        mockProfile.lastName = "Alinan"
        mockProfile.gender = "Male"

        whenever(studentProfileService.updateProfile(eq(studentId),any())).thenReturn(mockProfile)

        mockMvc.perform(put("/profile/$studentId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonReq))
        .andExpect(status().isOk)
        .andExpect(jsonPath("$.firstName").value(firstName))
    }
}