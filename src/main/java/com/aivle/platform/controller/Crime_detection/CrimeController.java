package com.aivle.platform.controller.Crime_detection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; // @RestController 대신 @Controller 사용
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@RequestMapping("/api")
public class CrimeController {

    // 메모리 내에 저장 (실제 서비스에서는 DB 사용을 권장)
    private List<CrimeAlertData> crimeList = new CopyOnWriteArrayList<>();

    /**
     * 범죄 알림을 수신하는 엔드포인트.
     *
     * 파일명이 "Crime_정확도_날짜_시간.jpg" 형태로 들어오면,
     * 파일명에서 정확도만 추출하고, 타임스탬프는 서버에서 생성합니다.
     *
     * 예) "Crime_95.3_20250207_050441.jpg"
     *     → 정확도: 95.3, 타임스탬프: 서버 생성 시각 (형식: yyyyMMdd_HHmmss)
     */
    @PostMapping("/crime")
    @ResponseBody  // JSON 응답으로 처리
    public ResponseEntity<?> receiveCrimeAlert(
            @RequestParam("file") MultipartFile file,
            // 클라이언트에서 따로 전달된 정확도가 있다면(기본값 0), 추출된 값과 비교할 수 있습니다.
            @RequestParam(value = "accuracy", required = false, defaultValue = "0") double accuracyParam
    ) {
        // 파일명이 없으면 기본 형식의 파일명 생성
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            originalFileName = "crime_" + System.currentTimeMillis() + ".jpg";
        }

        // 타임스탬프는 항상 서버에서 현재 시각 생성 (형식: yyyyMMdd_HHmmss)
        String serverTimestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // 정확도는 기본적으로 클라이언트에서 전달된 값(또는 0)을 사용하되,
        // 파일명이 "Crime_"로 시작하며 ".jpg"로 끝난다면 파일명에서 정확도만 추출 시도
        double extractedAccuracy = accuracyParam;
        if (originalFileName.startsWith("crime_") && originalFileName.toLowerCase().endsWith(".jpg")) {
            // 확장자 제거 → 예: "crime_95.3_20250207_050441"
            String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
            String[] parts = baseName.split("_");
            // parts[0] = "Crime", parts[1] = "95.3", parts[2] = "20250207", parts[3] = "050441"
            if (parts.length >= 2) {
                try {
                    extractedAccuracy = Double.parseDouble(parts[1]);
                } catch (NumberFormatException e) {
                    // 파싱 실패 시 기본값(accuracyParam 또는 0) 유지
                }
            }
        }

        // 파일 저장 폴더: 프로젝트 루트의 crime_alerts 폴더
        Path crimeDir = Paths.get("crime_alerts");
        try {
            if (!Files.exists(crimeDir)) {
                Files.createDirectories(crimeDir);
            }
            Path filePath = crimeDir.resolve(originalFileName);
            Files.write(filePath, file.getBytes());

            // 오버로딩 생성자를 사용하여, 고정값(이름, 범죄 유형 등)과 함께
            // id는 파일명, 타임스탬프는 서버에서 생성한 값, 정확도는 파일명에서 추출한 값을 설정합니다.
            CrimeAlertData alert = new CrimeAlertData(
                    originalFileName,       // id: 파일명
                    serverTimestamp,        // 타임스탬프: 서버 생성 시각
                    extractedAccuracy,      // 정확도: 파일명에서 추출한 값
                    "/crime_alerts/" + originalFileName  // 이미지 접근 URL
            );
            crimeList.add(alert);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 저장 실패");
        }
        return ResponseEntity.ok("Crime alert received");
    }

    /**
     * 범죄 알림 데이터를 제공하는 API 엔드포인트.
     */
    @GetMapping("/crime")
    @ResponseBody  // JSON 응답으로 처리
    public List<CrimeAlertData> getCrimeAlerts() {
        return crimeList;
    }

    /**
     * 관리 페이지로 이동하는 매핑.
     * 최종 URL: "/api/management/crime"
     * 해당 뷰는 src/main/resources/templates/management/crime.html 파일을 렌더링합니다.
     */
    @GetMapping("/management/crime")
    public String crime() {
        return "management/crime";
    }
}
