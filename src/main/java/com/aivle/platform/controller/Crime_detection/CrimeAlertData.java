package com.aivle.platform.controller.Crime_detection;

public class CrimeAlertData {
    private String id;         // 파일명 혹은 고유 ID
    private String type;       // "Crime" 고정
    private String timestamp;  // 발생 시각 (파일명에서 추출된 값 또는 서버 생성 값)
    private double accuracy;   // 예측 정확도 (파일명에서 추출된 값)
    private String imageUrl;   // 저장된 이미지 URL

    // 추가 정보 필드 (고정값)
    private String name;       // 성명
    private String crimeType;  // 범죄 유형
    private int crimeCount;    // 범죄 횟수
    private boolean released;  // 출소 여부
    private String riskLevel;  // 위험도 등급

    // 전체 값을 전달받는 생성자 (클라이언트에서 모든 값 전달 시)
    public CrimeAlertData(String id, String type, String timestamp, double accuracy, String imageUrl,
                          String name, String crimeType, int crimeCount, boolean released, String riskLevel) {
        this.id = id;
        this.type = type;
        this.timestamp = timestamp;
        this.accuracy = accuracy;
        this.imageUrl = imageUrl;
        this.name = name;
        this.crimeType = crimeType;
        this.crimeCount = crimeCount;
        this.released = released;
        this.riskLevel = riskLevel;
    }

    // 오버로딩 생성자: 파일명으로부터 정확도와 타임스탬프가 추출된 경우, 고정값을 할당
    public CrimeAlertData(String id, String timestamp, double accuracy, String imageUrl) {
        this.id = id;
        this.type = "Crime";           // 항상 "Crime"
        this.timestamp = timestamp;
        this.accuracy = accuracy;
        this.imageUrl = imageUrl;

        // 고정값 할당
        this.name = "전홍석";
        this.crimeType = "흉기 난동";
        this.crimeCount = 100;
        this.released = false;         // 미출소
        this.riskLevel = "100%";
    }

    // Getters and Setters

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public double getAccuracy() {
        return accuracy;
    }
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCrimeType() {
        return crimeType;
    }
    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }
    public int getCrimeCount() {
        return crimeCount;
    }
    public void setCrimeCount(int crimeCount) {
        this.crimeCount = crimeCount;
    }
    public boolean isReleased() {
        return released;
    }
    public void setReleased(boolean released) {
        this.released = released;
    }
    public String getRiskLevel() {
        return riskLevel;
    }
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
