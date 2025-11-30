package com.openwallet.domain;

import com.openwallet.Category;
import com.openwallet.Emotion;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(
            name = "subscription_id",
            columnDefinition = "CHAR(36)",
            updatable = false,
            nullable = false
    )
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate nextPaymentDate;

    @Column(nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category; // SUBSCRIPTION

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    @Column(length = 250)
    private String memo;

    @Column(nullable = false)
    private int satisfaction;
}
