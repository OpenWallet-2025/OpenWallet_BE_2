package com.openwallet.domain;

import com.openwallet.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="favorite")
public class Favorite {

    //즐겨찾기 = 상품명, 가격, 카테고리

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(
            name = "favorite_id",
            columnDefinition = "CHAR(36)",
            updatable = false,
            nullable = false
    )
    private UUID id;

    private String title;

    private Long price;

    @Enumerated(EnumType.STRING)
    private Category category;
}
