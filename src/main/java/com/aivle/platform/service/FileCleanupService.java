package com.aivle.platform.service;

import com.aivle.platform.domain.Image;
import com.aivle.platform.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileCleanupService {

    private final ImageRepository imageRepository; // 주입받은 이미지 리포지토리

    //    @Scheduled(cron = "0 0 3 * * ?") // 매일 새벽 3시에 실행
    @Scheduled(fixedRate = 10000) // 10초마다 실행 (밀리초 단위)
    public void cleanUpUnusedFiles() {
        log.info("10초마다 사진 삭제 서비스 실행 중: {}", LocalDateTime.now());
        String saveDir = new File("src/main/resources/static/uploads").getAbsolutePath() + "/";
        File directory = new File(saveDir);

        // DB에서 관리 중인 파일 URL 가져오기
        List<String> activeFileUrls = imageRepository.findAll()
                .stream()
                .map(Image::getImageUrl)
                .toList();

        // 디렉터리 내 파일과 비교하여 삭제
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    String relativePath = "/uploads/" + file.getName();
                    if (!activeFileUrls.contains(relativePath)) {
                        if (file.delete()) {
                            log.info("사용되지 않는 파일 삭제 성공: {}", file.getAbsolutePath());
                        } else {
                            log.warn("사용되지 않는 파일 삭제 실패: {}", file.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }
}
