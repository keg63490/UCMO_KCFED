package com.kcfed.ucmo.smsbackend.controllers;


import javax.inject.Inject;

import org.springframework.social.connect.ConnectionRepository;
        import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
        import org.springframework.social.twitter.api.TwitterProfile;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tweets")
public class TwitterController {

    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    @Inject
    public TwitterController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String helloTwitter(Model model) {

        model.addAttribute(twitter.userOperations().getUserProfile());
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();

        List<Tweet> tweets = twitter.timelineOperations()
                                                        .getUserTimeline(20, 0, Long.MIN_VALUE)
                                                        .stream()
                                                        .filter(tweet -> checkWords(tweet.getText()))
                                                        .collect(Collectors.toList());
        model.addAttribute("tweets", tweets);
        return "tweets";
    }

    private boolean checkWords(String text) {
        String[] words = {"test", "random"};

        for (String word : words) {
            if (text.contains(word))
                return true;
        }
        return false;
    }
}