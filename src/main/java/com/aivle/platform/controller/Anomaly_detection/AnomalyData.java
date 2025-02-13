package com.aivle.platform.controller.Anomaly_detection;

public class AnomalyData {
    private String id;         // 파일명 혹은 고유 ID
    private String type;       // 예: Danger, Weapon 등
    private String timestamp;  // 발생 시각
    private double accuracy;   // 예측 정확도
    private String imageUrl;   // 저장된 이미지 URL

    public AnomalyData(String id, String type, String timestamp, double accuracy, String imageUrl) {
        this.id = id;
        this.type = type;
        this.timestamp = timestamp;
        this.accuracy = accuracy;
        this.imageUrl = imageUrl;
    }

    // getter/setter
    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public double getAccuracy() {
        return accuracy;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}