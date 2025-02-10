package com.aivle.platform.controller.Crime_detection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api")
public class CrimeController {

    // 메모리 내에 저장 (실제 서비스에서는 DB 사용을 권장)
    private List<CrimeAlertData> crimeList = new CopyOnWriteArrayList<>();

    /**
     * 범죄 알림을 수신하는 엔드포인트.
     *
     * FastAPI 등에서 전송하는 폼 데이터는 이미지 파일과 함께
     * 성명(name), 범죄 유형(crimeType), 범죄 횟수(crimeCount),
     * 출소 여부(released), 위험도 등급(riskLevel), 예측 정확도(accuracy) 등의
     * 정보를 포함할 수 있습니다.
     */
    @PostMapping("/crime")
    public ResponseEntity<?> receiveCrimeAlert(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "crimeType", required = false) String crimeType,
            @RequestParam(value = "crimeCount", required = false, defaultValue = "0") int crimeCount,
            @RequestParam(value = "released", required = false, defaultValue = "false") boolean released,
            @RequestParam(value = "riskLevel", required = false) String riskLevel,
            @RequestParam(value = "accuracy", required = false, defaultValue = "0") double accuracy
    ) {
        // 파일명이 없으면 기본 형식의 파일명 생성 (예: Crime_1634567890123.jpg)
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            originalFileName = "Crime_" + System.currentTimeMillis() + ".jpg";
        }
        // 서버에서 발생 시각 기록 (yyyyMMdd_HHmmss 형식)
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // 파일 저장 폴더: 프로젝트 루트의 crime_alerts 폴더
        Path crimeDir = Paths.get("crime_alerts");
        try {
            if (!Files.exists(crimeDir)) {
                Files.createDirectories(crimeDir);
            }
            Path filePath = crimeDir.resolve(originalFileName);
            Files.write(filePath, file.getBytes());

            // CrimeAlertData 객체 생성 (추가 정보 포함)
            CrimeAlertData alert = new CrimeAlertData(
                    originalFileName,                      // id (파일명 사용)
                    "Crime",                               // type 고정
                    timestamp,
                    accuracy,
                    "/crime_alerts/" + originalFileName,   // 접근 URL
                    name,
                    crimeType,
                    crimeCount,
                    released,
                    riskLevel
            );
            crimeList.add(alert);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 저장 실패");
        }
        return ResponseEntity.ok("Crime alert received");
    }

    @GetMapping("/crime")
    public List<CrimeAlertData> getCrimeAlerts() {
        return crimeList;
    }
}
