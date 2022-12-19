package com.forbes.coding.services;

import java.util.HashMap;
import java.util.List;

public interface DictionaryService {
    public void addWord(String word);
    public void removeWord(String word);
    public List<String> getDictionary();
    public HashMap<String, String> search(String para);
}
