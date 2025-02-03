package com.aivle.platform.domain;

import com.aivle.platform.domain.type.PoliceUnitType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PoliceUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policeUnitId;

    @Column(length = 50, nullable = false)
    private String deptName;

    @Column(length = 50, nullable = false)
    private String stationName;

    @Column(length = 50, nullable = false)
    private String policeUnitName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PoliceUnitType policeUnitType;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(precision = 11, scale = 8, nullable = false)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8, nullable = false)
    private BigDecimal longitude;


    @OneToOne(mappedBy = "policeUnit")
    @JsonIgnore // 순환 참조 방지
    private Member member;


    public String getPoliceUnitTypeDescription() {
        return policeUnitType.getDescription();
    }

}
