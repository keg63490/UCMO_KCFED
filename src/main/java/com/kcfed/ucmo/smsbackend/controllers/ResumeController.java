package com.kcfed.ucmo.smsbackend.controllers;

import com.kcfed.ucmo.smsbackend.models.Resume;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/resume")
public class ResumeController {

    private Resume resume;

    @RequestMapping(method = RequestMethod.GET)
    public String resumeForm(Model model) {
        if (resume == null)
            model.addAttribute("resume", new Resume());
        else
            model.addAttribute("resume", resume);

        return "resume";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String helloTwitter(@ModelAttribute("resume") Resume resume, Model model) {

        this.resume = resume;
        model.addAttribute("resume", resume);

        return "resume";
    }
}
