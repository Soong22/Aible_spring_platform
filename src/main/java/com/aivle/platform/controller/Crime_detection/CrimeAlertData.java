package com.aivle.platform.controller.Crime_detection;

public class CrimeAlertData {
    private String id;         // 파일명 혹은 고유 ID
    private String type;       // "Crime" 고정
    private String timestamp;  // 발생 시각
    private double accuracy;   // 예측 정확도
    private String imageUrl;   // 저장된 이미지 URL

    // 추가 정보 필드
    private String name;       // 성명
    private String crimeType;  // 범죄 유형
    private int crimeCount;    // 범죄 횟수
    private boolean released;  // 출소 여부
    private String riskLevel;  // 위험도 등급

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
