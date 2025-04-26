package com.robotbot.finance_tracker_server.controllers;

import com.robotbot.finance_tracker_server.services.IconService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/icons")
@RequiredArgsConstructor
public class IconsController {

    private final IconService iconService;

    @GetMapping
    public ResponseEntity getIcons() {
        return ResponseEntity.ok(iconService.getIcons());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getIconById(@PathVariable Long id) {
        return ResponseEntity.ok(iconService.getIconById(id));
    }
}
