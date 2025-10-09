package com.example.demo.service;

import com.example.demo.model.Item;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    private List<Item> allItems = new ArrayList<> (Arrays.asList(
            new Item("1001", "ネックレス", "ジュエリー"),
            new Item("1002", "パーカー", "ファッション"),
            new Item("1003", "フェイスクリーム", "ビューティ"),
            new Item("1004", "サプリメント", "ヘルス"),
            new Item("1005", "ブルーベリー", "フード")));

    public List<Item> getAllItems() {
        return allItems;
    }


    public Item getItem(String itemId) {
        for(int i = 0; i < allItems.size(); i++) {
            if(allItems.get(i).getItemId().equals(itemId)) {
                return allItems.get(i);
            }
        }
        return null;
    }

    public void addItem(Item item) {
        allItems.add(item);
    }



    public void updateItem(String itemId, Item item) {
        for(int i = 0; i < allItems.size(); i++) {
            if(allItems.get(i).getItemId().equals(itemId)) {
                allItems.set(i, item);
            }
        }
    }
}