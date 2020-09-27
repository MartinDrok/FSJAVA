package com.mdrok._spring_core.repositories;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class StringRepository {

    private HashMap<String, Integer> repo;

    public StringRepository(){
        this.repo = new HashMap<>();
    }

    public boolean add(String text, Integer wordCount){
        if (text == null || text.isBlank() || wordCount == 0) return false;

        repo.put(text, wordCount);
        return true;
    }

    public boolean alreadyAdded(String text){
        if (text == null || text.isBlank()) return false;

        return repo.containsKey(text);
    }

    public Integer getWordCount(String text){
        if (text == null || text.isBlank()) return 0;

        return repo.get(text);
    }
}
