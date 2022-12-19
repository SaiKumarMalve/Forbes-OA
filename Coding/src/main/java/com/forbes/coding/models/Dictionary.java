package com.forbes.coding.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Getter
@NoArgsConstructor
@Component
public class Dictionary {
    private HashSet<String> dict = new HashSet<>();

    public void addToDictionary(String word){
        // This method takes in a String parameter called word and adds it to the dict HashSet.
        this.dict.add(word);
    }
    public void removeFromDictionary(String word){
        // This method takes in a String parameter called word and removes it from the dict HashSet.
        this.dict.remove(word);
    }
}
