package com.aivle.platform.service;

import com.aivle.platform.domain.Image;
import com.aivle.platform.exception.file.FileSaveException;
import com.aivle.platform.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final ImageRepository imageRepository; // 주입받은 이미지 리포지토리

    // 파일 저장 및 URL 반환
    public static String saveFileAndGetUrl(MultipartFile file) {
        // 프로젝트 루트 디렉토리 내의 static/uploads 디렉토리에 저장
        String saveDir = new File("src/main/resources/static/uploads").getAbsolutePath() + "/";

        // 저장 디렉토리가 존재하는지 확인
        File directory = new File(saveDir);
        if (!directory.exists()) {
            boolean wasSuccessful = directory.mkdirs();

            if (!wasSuccessful) {
                log.error("디렉토리 생성 실패: {}", saveDir);
            } else {
                log.info("디렉토리가 성공적으로 생성되었습니다: {}", saveDir);
            }
        }

        try {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File savedFile = new File(saveDir + filename);
            file.transferTo(savedFile);
            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new FileSaveException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }

    //    @Scheduled(cron = "0 0 3 * * ?") // 매일 새벽 3시에 실행
    @Scheduled(fixedRate = 10000) // 10초마다 실행 (밀리초 단위)
    public void cleanUpUnusedFiles() {
        log.info("10초마다 불필요한 사진 삭제 서비스 실행 중: {}", LocalDateTime.now());
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
