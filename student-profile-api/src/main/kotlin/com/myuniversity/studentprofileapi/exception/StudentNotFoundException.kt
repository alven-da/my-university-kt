package com.myuniversity.studentprofileapi.exception

import jakarta.persistence.EntityNotFoundException

class StudentNotFoundException(studentId: String) : EntityNotFoundException("Student with ID $studentId was not in our records")