package com.aivle.platform.domain;

import com.aivle.platform.domain.type.ReadStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReadStatus readStatus;


    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender; // Sender (Member 테이블과 다대일 관계)

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver; // Receiver (Member 테이블과 다대일 관계)

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> images = new ArrayList<>();


    public String getReadStatusDescription() {
        return readStatus.getDescription();
    }

}
