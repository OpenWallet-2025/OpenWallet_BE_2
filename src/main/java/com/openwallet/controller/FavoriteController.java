package com.openwallet.controller;

import com.openwallet.dto.FavoriteRequest;
import com.openwallet.dto.FavoriteResponse;
import com.openwallet.dto.FavoriteUpdateRequest;
import com.openwallet.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<FavoriteResponse> create(@RequestBody FavoriteRequest request) {
        FavoriteResponse saved = favoriteService.save(request);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public List<FavoriteResponse> getAll() {
        return favoriteService.findAll();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FavoriteResponse> update(@PathVariable UUID id, @RequestBody FavoriteUpdateRequest request) {
        FavoriteResponse updated = favoriteService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        favoriteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
