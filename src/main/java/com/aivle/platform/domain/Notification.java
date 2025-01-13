package com.aivle.platform.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender; // Sender (Member 테이블과 다대일 관계)

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver; // Receiver (Member 테이블과 다대일 관계)

    @Column(precision = 11, scale = 8, nullable = false)
    private BigDecimal cctvLatitude;

    @Column(precision = 11, scale = 8, nullable = false)
    private BigDecimal cctvLongitude;

    @Column(length = 500, nullable = false)
    private String gifUrl;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReadStatus readStatus;

}
