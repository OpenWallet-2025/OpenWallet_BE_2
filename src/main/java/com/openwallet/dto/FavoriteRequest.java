package com.openwallet.dto;

import com.openwallet.Category;
import com.openwallet.domain.Favorite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequest {

    private String title;

    private Long price;

    private String category;

    public Favorite toEntity() {
        Category categoryEnum = (category != null) ? Category.valueOf(category) : null;

        return Favorite.builder()
                .title(title)
                .price(price)
                .category(categoryEnum)
                .build();
    }
}
