package com.kcfed.ucmo.smsbackend.controllers;

import com.kcfed.ucmo.smsbackend.models.Resume;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String indexPage(Model model) {
        model.addAttribute("resume", new Resume());
        return "index";
    }
}
