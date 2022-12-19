package com.forbes.coding.services;

import com.forbes.coding.models.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    private Dictionary dict;

    @Autowired
    public void setDict(Dictionary dict) throws Exception{
        // To inject an instance of Dictionary into the service.
        // The setter method also calls the updateDictionary method, which reads the file dictionary.txt
        this.dict = dict;
        updateDictionary();
    }

    @Override
    public void addWord(String word) {
        // This method takes in a String parameter called word and calls the addToDictionary method on the dict object,
        // passing in the lowercase version of the word.
        dict.addToDictionary(word.toLowerCase());
    }

    @Override
    public void removeWord(String word) {
        // This method takes in a String parameter called word and calls the removeFromDictionary
        // method on the dict object, passing in the word.
        dict.removeFromDictionary(word);
    }

    @Override
    public List<String> getDictionary() {
        // This method returns an ArrayList containing the elements of the dict HashSet.
        return new ArrayList<String>(dict.getDict());
    }

    public void updateDictionary() throws Exception {
        // This method reads from the file dictionary.txt from the classpath and adds each line(word) to the dict HashSet.
        Resource resource = new ClassPathResource("files/dictionary.txt");
        File file = resource.getFile();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine())!=null){
                addWord(line);
            }
        }

    }

    public HashMap<String, String> search(String para){
        // Takes in a String parameter called para and returns a HashMap with the keys being words in the para string
        // that are not in the dictionary and the values being the closest matching word in the dictionary.
        HashMap<String, String> closestWords = new HashMap<String, String>();
        String[] words = para.replaceAll("[^a-zA-Z0-9 ]", "").split(" ");
        for(String word: words){
            if(!dict.getDict().contains(word.toLowerCase()))
                closestWords.put(word.toLowerCase(), findClosestMatch(word, getDictionary()));
        }

        return closestWords;
    }

    public static String findClosestMatch(String word, List<String> dictionary) {
        // Initialize the closest match to the first word in the dictionary
        String closestMatch = dictionary.get(0);
        int minDistance = getEditDistance(word, closestMatch);

        // Iterate through the rest of the words in the dictionary and update the closest match if necessary
        for (int i = 1; i < dictionary.size(); i++) {
            String dictWord = dictionary.get(i);
            int distance = getEditDistance(word, dictWord);
            if (distance < minDistance) {
                closestMatch = dictWord;
                minDistance = distance;
            }
        }

        return closestMatch;
    }

    public static int getEditDistance(String word1, String word2) {
        // Initialize a two-dimensional array to store the edit distances between prefixes of the two words
        int[][] distances = new int[word1.length() + 1][word2.length() + 1];

        // Initialize the distance between the empty string and each prefix of the second word
        for (int j = 0; j <= word2.length(); j++) {
            distances[0][j] = j;
        }

        // Initialize the distance between the empty string and each prefix of the first word
        for (int i = 0; i <= word1.length(); i++) {
            distances[i][0] = i;
        }

        // Compute the edit distance between the two words using dynamic programming
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                // If the last characters of the prefixes match, the distance is the same as the distance between the
                // two prefixes without the last characters
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    distances[i][j] = distances[i - 1][j - 1];
                }
                // Otherwise, the distance is one more than the minimum of the distances between the two prefixes with
                // the last character removed, or the distance between the two prefixes with the last character
                // replaced by the other character
                else {
                    distances[i][j] = 1 + Math.min(Math.min(distances[i - 1][j], distances[i][j - 1]),
                            distances[i - 1][j - 1]);
                }
            }
        }

        // Return the edit distance between the two words
        return distances[word1.length()][word2.length()];
    }
}
