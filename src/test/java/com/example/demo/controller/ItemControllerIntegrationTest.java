package com.example.demo.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        // mockMvc is auto-configured, but ensure it's wired to context
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetItems() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].itemName", is("ネックレス")));
    }

    @Test
    public void testGetItem() throws Exception {
        mockMvc.perform(get("/items/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.itemName", is("パーカー")));
    }

    @Test
    public void testAddItem() throws Exception {
        String newItemJson = "{\"itemName\":\"タブレット\",\"itemCategory\":\"電子機器\"}";
                        // add new item
                        mockMvc.perform(post("/items")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(newItemJson))
                                        .andExpect(status().isOk());

                        // verify the new item exists somewhere in the list
                        MvcResult after = mockMvc.perform(get("/items")).andExpect(status().isOk()).andReturn();
                        String afterStr = after.getResponse().getContentAsString();
                        JsonNode afterArr = mapper.readTree(afterStr);

                        boolean found = false;
                        for (JsonNode node : afterArr) {
                                if (node.has("itemName") && "タブレット".equals(node.get("itemName").asText())) {
                                        found = true;
                                        break;
                                }
                        }
                        org.junit.jupiter.api.Assertions.assertTrue(found, "Newly added item should be present");
    }

    @Test
    public void testUpdateItem() throws Exception {
        String updatedItemJson = "{\"itemId\":3,\"itemName\":\"モイスチャライザー\",\"itemCategory\":\"スキンケア\"}";

        mockMvc.perform(put("/items/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedItemJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/items/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.itemName", is("モイスチャライザー")));
    }

    @Test
    public void testDeleteItem() throws Exception {
        // create a new item
        String newItemJson = "{\"itemName\":\"スマートウォッチ\",\"itemCategory\":\"電子機器\"}";

        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newItemJson))
                .andExpect(status().isOk());

        // fetch all items, find last id
        MvcResult result = mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andReturn();

        String contentStr = result.getResponse().getContentAsString();
        JsonNode arr = mapper.readTree(contentStr);
        int lastIndex = arr.size() - 1;
        long idToDelete = arr.get(lastIndex).get("itemId").asLong();

        // delete the created item
        mockMvc.perform(delete("/items/" + idToDelete))
                .andExpect(status().isOk());

        // now GET should return 404 for that id
        mockMvc.perform(get("/items/" + idToDelete))
                .andExpect(status().isNotFound());
    }
}
