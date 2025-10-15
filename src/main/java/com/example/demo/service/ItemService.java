package com.example.demo.service;

import com.example.demo.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repo.ItemRepository;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        itemRepository.findAll().forEach(allItems::add);

        return allItems;
    }

    // use Long for id to match Item and ItemRepository
    public Optional<Item> getItem(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public void addItem(Item item) {
        itemRepository.save(item);
    }

    public void updateItem(Long itemId, Item item) {
        if(itemRepository.existsById(itemId)) {
            itemRepository.save(item);
        }
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}