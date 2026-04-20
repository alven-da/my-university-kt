package com.myuniversity.studentprofileapi.adapter.out.persistence

import com.myuniversity.studentprofileapi.domain.StudentProfile
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@DataJpaTest
class StudentProfilePersistenceAdapterTest {
    @Autowired
    private lateinit var studentProfileRepo: StudentProfileSpringDataRepository

    private lateinit var adapter: StudentProfilePersistenceAdapter

    @BeforeEach
    fun setup() {
        adapter = StudentProfilePersistenceAdapter(studentProfileRepo)
    }

    @Test
    fun `should persist and retrieve student from H2`() {
        // GIVEN
        val studentId = "1"
        val domainStudent = StudentProfile(studentId, "Alven", "Alinan", "Male")

        // WHEN
        adapter.save(domainStudent)
        val found = adapter.findById("1")

        // THEN
        assertNotNull(found, "Data should be found in H2")
        assertEquals("Alven", found?.firstName)
        assertEquals("Alinan", found?.lastName)
    }
}