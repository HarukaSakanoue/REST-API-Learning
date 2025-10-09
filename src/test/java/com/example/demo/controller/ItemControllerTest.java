package com.example.demo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class ItemControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    @Test
    public void testGetItems() throws Exception {   
        mockMvc.perform(get("/items"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(5)))
               .andExpect(jsonPath("$[0].itemId", is("1001")))
               .andExpect(jsonPath("$[0].itemName", is("ネックレス")))
               .andExpect(jsonPath("$[0].itemCategory", is("ジュエリー")));
    }      
    @Test
    public void testGetItem() throws Exception {       
        mockMvc.perform(get("/items/1002"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.itemId", is("1002")))
               .andExpect(jsonPath("$.itemName", is("パーカー")))
               .andExpect(jsonPath("$.itemCategory", is("ファッション")));
    }   

    @Test
    public void testAddItem() throws Exception {       
        String newItemJson = "{\"itemId\":\"1006\",\"itemName\":\"タブレット\",\"itemCategory\":\"電子機器\"}";
        mockMvc.perform(post("/items")
               .contentType(MediaType.APPLICATION_JSON)
               .content(newItemJson))
               .andExpect(status().isOk());

        mockMvc.perform(get("/items/1006"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.itemId", is("1006")))
               .andExpect(jsonPath("$.itemName", is("タブレット")))
               .andExpect(jsonPath("$.itemCategory", is("電子機器")));
    }
}
