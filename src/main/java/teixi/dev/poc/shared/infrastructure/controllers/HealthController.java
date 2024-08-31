package teixi.dev.poc.shared.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {
    private static final String OK_STATUS = "OK";

    @GetMapping(value = "/health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok(OK_STATUS);
    }
}
