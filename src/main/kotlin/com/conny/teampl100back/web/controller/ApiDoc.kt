package com.conny.teampl100back.web.controller

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@Hidden
@RestController
class ApiDoc {
    @GetMapping("api-doc")
    fun redirect(): RedirectView {
        return RedirectView("/swagger-ui/index.html")
    }
}