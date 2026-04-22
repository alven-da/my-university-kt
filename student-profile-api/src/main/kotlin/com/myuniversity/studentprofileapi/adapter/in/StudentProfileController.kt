package com.myuniversity.studentprofileapi.adapter.`in`

// import com.myuniversity.studentprofileapi.adapter.dto.UpdateStudentProfileDto
import com.myuniversity.studentprofileapi.application.StudentProfileService
import com.myuniversity.studentprofileapi.domain.StudentProfile
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
// import org.springframework.web.bind.annotation.PathVariable
// import org.springframework.web.bind.annotation.PutMapping
// import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class StudentProfileController(
    private val studentProfileService: StudentProfileService
) {

    @GetMapping("/me")
    fun getStudentProfile(
        @AuthenticationPrincipal jwt: Jwt
    ): StudentProfile {
        // TODO: Proper guarding the execution using jwt
        val studentId = jwt.subject
        return studentProfileService.getProfile(studentId)
    }

    /**
    @PutMapping("/me")
    @PreAuthorize("hasAuthority('SCOPE_profile:write')")
    fun updateStudentProfile(
        @RequestBody updateReq: UpdateStudentProfileDto,
        @AuthenticationPrincipal jwt: Jwt
    ): StudentProfile {
        // TODO: Proper guarding the execution using jwt
        val studentId = jwt.subject
        return studentProfileService.updateProfile(studentId, updateReq)
    }
    **/
}
