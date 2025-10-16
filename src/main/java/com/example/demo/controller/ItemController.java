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


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.example.demo.Exception.ItemNotFoundException;
import com.example.demo.model.HelloMessage;


@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public List<Item> getItems() {

        return itemService.getAllItems();
    }

    @GetMapping("/items/{itemId}")
    public Item getItem(@PathVariable("itemId") Long itemId) {
        return itemService.getItem(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
    }


    @PostMapping("/items")
    public void addItem(@RequestBody Item item) {
        itemService.addItem(item);
    }


    @PutMapping("/items/{itemId}")
    public void updateItem(@PathVariable("itemId") Long itemId, @RequestBody Item item) {
        itemService.updateItem(itemId, item);
    }


    @DeleteMapping("/items/{itemId}")
    public void deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
    }



    @GetMapping("/callHello")
    public HelloMessage getHelloResponse() {
        return itemService.getHelloResponse();
    }
}