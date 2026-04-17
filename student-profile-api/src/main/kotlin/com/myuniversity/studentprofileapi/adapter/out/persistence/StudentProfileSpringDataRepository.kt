package com.myuniversity.studentprofileapi.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface StudentProfileSpringDataRepository : JpaRepository<StudentProfileEntity, String> {

}