package com.forbes.coding.controller;

import com.forbes.coding.services.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@ComponentScan("com.forbes.coding.services")
public class APIController {

    private DictionaryService dictionaryService;

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService){
        //To inject an instance of DictionaryService into the controller.
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/dictionary")
    public List<String> getDictionary(){
        // Handles a GET request to the /dictionary endpoint. It calls the getDictionary method on the dictionaryService
        // and returns the result. If an exception is thrown, it is caught and a RuntimeException is thrown with a
        // custom message.
        try {
            List<String> dict = this.dictionaryService.getDictionary();
            if(dict == null)
                throw new Exception();
            return dict;
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve data", e);
        }
    }

    @PostMapping("/dictionary/{word}")
    public void addWordToDictionary(@PathVariable String word){
        // Handles a POST request to the /dictionary/{word} endpoint, where {word} is a path variable.
        // It calls the addWord method on the dictionaryService with the word path variable as an argument.
        dictionaryService.addWord(word);
    }

    @DeleteMapping("/dictionary/{word}")
    public void deleteWordFromDictionary(@PathVariable String word){
        // Handles a DELETE request to the /dictionary/{word} endpoint.
        // It calls the removeWord method on the dictionaryService with the word path variable as an argument.
        dictionaryService.removeWord(word);
    }

    @PostMapping("/story")
    public HashMap<String, String> getTheClosestWord(@RequestBody String para){
        // Handles a POST request to the /story endpoint. It takes in a request body as a String parameter
        // and calls the search method on the dictionaryService with the para parameter as an argument.
        // It returns the result as a HashMap<String, String> of Words not found and their closest word.
        return dictionaryService.search(para);
    }
}

