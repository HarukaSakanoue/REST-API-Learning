package com.example.demo.controller;

import com.example.demo.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Item;

import java.util.List;
import java.util.Arrays;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public List<Item> getItems() {

        return itemService.getAllItems();
    }

    @GetMapping("/items/{itemId}")
    public Item getItem(@PathVariable("itemId") String itemId) {
        return itemService.getItem(itemId);
    }


    @PostMapping("/items")
    public void addItem(@RequestBody Item item) {
        itemService.addItem(item);
    }


    @PutMapping("/items/{itemId}")
    public void updateItem(@PathVariable("itemId") String itemId, @RequestBody Item item) {
        itemService.updateItem(itemId, item);
    }

}