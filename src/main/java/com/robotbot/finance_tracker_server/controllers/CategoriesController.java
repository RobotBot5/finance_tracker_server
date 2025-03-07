package com.robotbot.finance_tracker_server.controllers;

import com.robotbot.finance_tracker_server.domain.dto.CategoryCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.CategoryUpdateRequest;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Object> createCategory(
            @RequestBody @Validated CategoryCreateRequest categoryCreateRequest,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        categoryService.addCategory(categoryCreateRequest, userPrincipal);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Object> getCategories(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return ResponseEntity.ok().body(categoryService.getCategoriesByUser(userPrincipal));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateCategory(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id,
            @RequestBody CategoryUpdateRequest categoryUpdateRequest
    ) {
        categoryService.updateCategory(id, categoryUpdateRequest, userPrincipal);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id
    ) {
        categoryService.deleteCategory(id, userPrincipal);
        return ResponseEntity.ok().build();
    }
}
