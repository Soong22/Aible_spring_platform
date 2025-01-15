package com.aivle.platform.service;

import com.aivle.platform.domain.PoliceUnit;
import com.aivle.platform.exception.PoliceUnitNotFoundException;
import com.aivle.platform.repository.PoliceUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PoliceUnitService {

    private final PoliceUnitRepository policeUnitRepository;

    public PoliceUnit getPoliceUnitById(Long policeUnitId) {
        return policeUnitRepository.findById(policeUnitId)
                .orElseThrow(() -> new PoliceUnitNotFoundException("지구대/파출소 정보를 찾을 수 없습니다. police_unit_id: " + policeUnitId));
    }

    // 광역청 목록 가져오기
    public List<String> getDepartments() {
        return policeUnitRepository.findDistinctDeptNames();
    }

    // 특정 광역청에 속한 경찰서 목록 가져오기
    public List<String> getStations(String deptName) {
        return policeUnitRepository.findDistinctStationNamesByDeptName(deptName);
    }

    // 특정 경찰서에 속한 지구대/파출소 목록 가져오기
    public List<PoliceUnit> getUnits(String deptName, String stationName) {
        return policeUnitRepository.findByDeptNameAndStationName(deptName, stationName);
    }

}
