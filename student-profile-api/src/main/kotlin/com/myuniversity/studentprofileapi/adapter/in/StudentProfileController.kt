package com.myuniversity.studentprofileapi.adapter.`in`

import com.myuniversity.studentprofileapi.adapter.dto.UpdateStudentProfileDto
import com.myuniversity.studentprofileapi.application.StudentProfileService
import com.myuniversity.studentprofileapi.domain.StudentProfile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class StudentProfileController(
    private val studentProfileService: StudentProfileService
) {

    @GetMapping("/{studentId}")
    fun getStudentProfile(@PathVariable studentId: String): StudentProfile {
        return studentProfileService.getProfile(studentId)
    }

    @PutMapping("/{studentId}")
    fun updateStudentProfile(@PathVariable studentId: String, @RequestBody updateReq: UpdateStudentProfileDto): StudentProfile {
        val profile: StudentProfile = studentProfileService.updateProfile(updateReq)

        println("updateStudentProfile")
        println(profile)

        return profile
    }
}
