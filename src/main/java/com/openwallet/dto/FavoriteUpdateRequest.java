package com.openwallet.dto;

import com.openwallet.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteUpdateRequest {

    private String title;
    private Long price;
    private Category category;
}
