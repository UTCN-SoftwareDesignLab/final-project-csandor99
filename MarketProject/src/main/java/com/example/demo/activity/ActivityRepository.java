package com.example.demo.activity;

import com.example.demo.activity.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    int countActivityByEmployeeNameEquals(String name);
}
