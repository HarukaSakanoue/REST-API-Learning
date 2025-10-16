package com.example.demo.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

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

import static org.hamcrest.MatcherAssert.assertThat;   
import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.*;
import com.example.demo.model.Item;
import com.example.demo.repo.ItemRepository;
import com.example.demo.Exception.ItemNotFoundException;
import com.example.demo.model.HelloMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.ArrayList;



@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest2 {

        // @Autowired
        // private WebApplicationContext wac;

        @Autowired
        private MockMvc mockMvc;

        @Test
        void testGetItem() throws Exception {
                int itemId = 1;
                String responseJsonString = mockMvc.perform(get("/items/{itemId}", itemId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .characterEncoding("UTF-8"))
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();

                ObjectMapper objectMapper = new ObjectMapper();
                Item responseItem = objectMapper.readValue(responseJsonString, Item.class);

                assertThat(responseItem.getItemId(), is(1L));
                assertThat(responseItem.getItemName(), is("ネックレス"));
                assertThat(responseItem.getItemCategory(), is("ジュエリー"));
        }
}
