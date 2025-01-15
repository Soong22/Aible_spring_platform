package com.aivle.platform.repository;

import com.aivle.platform.domain.PoliceUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PoliceUnitRepository extends JpaRepository<PoliceUnit, Long> {

    @Query("SELECT DISTINCT p.deptName FROM PoliceUnit p")
    List<String> findDistinctDeptNames();

    @Query("SELECT DISTINCT p.stationName FROM PoliceUnit p WHERE p.deptName = :deptName")
    List<String> findDistinctStationNamesByDeptName(String deptName);

    List<PoliceUnit> findByDeptNameAndStationName(String deptName, String stationName);

}
