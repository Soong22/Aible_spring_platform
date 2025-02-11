package com.aivle.platform.controller.Anomaly_detection;

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
public class AnomalyController {

    // 메모리 내에 저장 (실제 서비스에서는 DB 사용 권장)
    private List<AnomalyData> anomalyList = new CopyOnWriteArrayList<>();

    @PostMapping("/anomalies")
    @ResponseBody  // JSON 응답으로 처리
    public ResponseEntity<?> receiveAnomaly(@RequestParam("file") MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null) {
            originalFileName = "anomaly_" + System.currentTimeMillis() + ".jpg";
        }
        // 파일명 형식 예시: Danger_98.7_20250207_153045.jpg
        String[] parts = originalFileName.split("_");
        String type = (parts.length >= 1) ? parts[0] : "Unknown";
        String accuracyStr = (parts.length >= 2) ? parts[1] : "0";
        double accuracy = 0;
        try {
            accuracy = Double.parseDouble(accuracyStr);
        } catch(NumberFormatException e) {
            // 파싱 실패 시 0 처리
        }
        // 타임스탬프 (FastAPI에서 파일명에 포함되어 있더라도, 서버에서 기록)
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // 파일 저장 위치 (프로젝트 루트 하위 anomalies 폴더)
        Path anomalyDir = Paths.get("anomalies");
        try {
            if (!Files.exists(anomalyDir)) {
                Files.createDirectories(anomalyDir);
            }
            Path filePath = anomalyDir.resolve(originalFileName);
            Files.write(filePath, file.getBytes());

            // AnomalyData 객체 생성 (필요한 정보에 따라 확장 가능)
            AnomalyData anomaly = new AnomalyData(
                    originalFileName, // id로 파일명을 사용
                    type,
                    timestamp,
                    accuracy,
                    "/anomalies/" + originalFileName  // URL 경로
            );
            anomalyList.add(anomaly);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 저장 실패");
        }
        return ResponseEntity.ok("Anomaly received");
    }

    @GetMapping("/anomalies")
    @ResponseBody  // JSON 응답으로 처리
    public List<AnomalyData> getAnomalies() {
        return anomalyList;
    }

    // 관리 페이지로 이동하는 매핑 추가 (뷰를 렌더링)
    // 전체 URL은 "/api/management/anomaly.html"이 됩니다.
    @GetMapping("/management/anomaly")
    public String anomalyManagementPage() {
        // templates/management/anomaly.html 파일을 렌더링합니다.
        return "management/anomaly";
    }
}
