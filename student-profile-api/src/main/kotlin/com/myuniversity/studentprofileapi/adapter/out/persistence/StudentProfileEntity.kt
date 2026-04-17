package com.myuniversity.studentprofileapi.adapter.out.persistence

import jakarta.persistence.*

@Entity
@Table(name = "students")
class StudentProfileEntity(
    @Id
    val id: String? = "",

    @Column(nullable = false)
    var firstName: String? = "",

    @Column(nullable = false)
    var lastName: String? = "",

    var gender: String? = null
)