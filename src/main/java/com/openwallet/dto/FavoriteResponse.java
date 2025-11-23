package com.openwallet.dto;

import com.openwallet.domain.Favorite;
import lombok.Getter;

import java.util.UUID;

@Getter
public class FavoriteResponse {
    private UUID id;
    private String title;
    private Long price;
    private String category;

    public FavoriteResponse(Favorite entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.price = entity.getPrice();
        this.category = entity.getCategory() != null ? entity.getCategory().name() : null;
    }
}
