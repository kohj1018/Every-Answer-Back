package com.fineapple.everyanswerback.domain.deptClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeptClassRepository extends JpaRepository<DeptClass, Long> {

    @Query(value = "SELECT dc FROM DeptClass dc ORDER BY dc.deptId ASC")
    List<DeptClass> getAll();
}
