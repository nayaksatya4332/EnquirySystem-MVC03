package com.satya.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.entity.Course;

public interface CourseRepo extends JpaRepository<Course, Integer> {

}
