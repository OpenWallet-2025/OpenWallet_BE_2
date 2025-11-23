package com.openwallet.service;

import com.openwallet.domain.Favorite;
import com.openwallet.dto.FavoriteRequest;
import com.openwallet.dto.FavoriteResponse;
import com.openwallet.dto.FavoriteUpdateRequest;
import com.openwallet.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public FavoriteResponse save(FavoriteRequest request) {
        Favorite saved = favoriteRepository.save(request.toEntity());

        return new FavoriteResponse(saved);
    }

    public List<FavoriteResponse> findAll() {
        return favoriteRepository.findAll()
                .stream()
                .map(FavoriteResponse::new)
                .toList();
    }

    public FavoriteResponse update(UUID id, FavoriteUpdateRequest request) {
        Favorite favorite = favoriteRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 즐겨찾기 내역을 찾을 수 없습니다."));

        favorite.setTitle(request.getTitle());
        favorite.setPrice(request.getPrice());
        favorite.setCategory(request.getCategory());

        Favorite updated = favoriteRepository.save(favorite);

        return new FavoriteResponse(updated);
    }

    public void delete(UUID id) {
        if (!favoriteRepository.existsById(id)) {
            throw new RuntimeException("해당 즐겨찾기 내역을 찾을 수 없습니다: " + id);
        }
        favoriteRepository.deleteById(id);
    }
}
