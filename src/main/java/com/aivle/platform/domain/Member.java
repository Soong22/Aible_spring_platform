package com.aivle.platform.domain;

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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 50, nullable = false)
    private String memberName;

    @Column(length = 13, nullable = false)
    private String personPhone;

    @Column(length = 13)
    private String officePhone;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    @OneToOne
    @JoinColumn(name = "police_unit_id")
    private PoliceUnit policeUnit;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    // 알림 발신자 관계 (Notification.sender_id)
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Notification> sentNotifications = new ArrayList<>();

    // 알림 수신자 관계 (Notification.receiver_id)
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Notification> receivedNotifications = new ArrayList<>();


    public String getRoleDescription() {
        return role.getDescription();
    }

}