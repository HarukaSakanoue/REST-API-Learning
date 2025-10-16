package com.example.demo.service;

import com.example.demo.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repo.ItemRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import com.example.demo.model.HelloMessage;



@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    private RestTemplate restTemplate;

    public ItemService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Cacheable("getItems")
    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();

        try {
            Thread.sleep(3000); // 3秒の遅延を追加
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemRepository.findAll().forEach(allItems::add);

        return allItems;
    }

    // use Long for id to match Item and ItemRepository
    @Cacheable(value = "getItem", key = "#p0")
    public Optional<Item> getItem(Long itemId) {

        try {
            Thread.sleep(3000); // 3秒の遅延を追加
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemRepository.findById(itemId);
    }

    @CacheEvict(value = "getItems", allEntries = true)
    public void addItem(Item item) {
        itemRepository.save(item);
    }

    @Caching(evict = {
            @CacheEvict(value = "getItem", key = "#p0"),
            @CacheEvict(value = "getItems", allEntries = true)
    })
    public void updateItem(Long itemId, Item item) {
        if (itemRepository.existsById(itemId)) {
            itemRepository.save(item);
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "getItem", key = "#p0"),
            @CacheEvict(value = "getItems", allEntries = true)
    })
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }


    public HelloMessage getHelloResponse() {
        String url = "http://localhost:8080/hello";
        String hello = restTemplate.getForObject(url, String.class);
        HelloMessage retHello = new HelloMessage(hello);
        return retHello;
    }
}