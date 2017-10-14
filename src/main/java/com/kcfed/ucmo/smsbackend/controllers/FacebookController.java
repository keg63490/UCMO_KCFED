package com.kcfed.ucmo.smsbackend.controllers;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/facebookfeed")
public class FacebookController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;


    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        String [] fields = { "id","name","birthday","email","location","hometown","gender","first_name","last_name"};
        User user = facebook.fetchObject("me", User.class, fields);
        String name=user.getName();
        String birthday=user.getBirthday();
        String email=user.getEmail();
        String gender=user.getGender();
        String firstname=user.getFirstName();
        String lastname=user.getLastName();
        model.addAttribute("name",name );
        model.addAttribute("birthday",birthday );
        model.addAttribute("email",email );
        model.addAttribute("gender",gender);
        model.addAttribute("firstname",firstname);
        model.addAttribute("lastname",lastname);
        model.addAttribute("facebookProfile", facebook.fetchObject("me", User.class, fields));
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        ArrayList<Post> filteredFeed = new ArrayList<>();

        String[] words = {"next", "birthday", "whooped"};

        for (String word : words) {

            for (Post post : feed) {
                if (post.getMessage() != null && post.getMessage().toLowerCase().contains(word))
                    filteredFeed.add(post);
            }
        }
        feed.clear();
        feed.addAll(filteredFeed);
        model.addAttribute("feed", feed);
        return "hello";
    }

}