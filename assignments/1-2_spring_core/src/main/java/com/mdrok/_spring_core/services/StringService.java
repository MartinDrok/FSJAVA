package com.mdrok._spring_core.services;

import com.mdrok._spring_core.repositories.StringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    private StringRepository stringRepository;

    @Autowired
    public StringService(StringRepository repo){
        this.stringRepository = repo;
    }

    public Integer count(String text){
        if (text == null || text.isBlank()) return 0;

        if (stringRepository.alreadyAdded(text)){
            return stringRepository.getWordCount(text);
        }

        String[] words = text.split("\\s+");
        stringRepository.add(text, words.length);
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
