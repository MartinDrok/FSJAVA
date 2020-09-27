package com.mdrok._spring_core.services;

import com.mdrok._spring_core.repositories.StringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    private final StringRepository repo;

    @Autowired
    public StringService(StringRepository repo){
        this.repo = repo;
    }

    public Integer count(String text){
        if (text == null || text.isBlank()) return 0;

        if (repo.alreadyAdded(text)){
            return repo.getWordCount(text);
        }

        String[] words = text.split("\\s+");
        repo.add(text, words.length);
        return words.length;
    };

    public String invert(String text){
        if (text == null || text.isBlank()) return text;

        return new StringBuilder(text).reverse().toString();
    };

    public String capitalise(String text){
        if (text == null || text.isBlank()) return text;

        return text.toUpperCase();
    }
}
