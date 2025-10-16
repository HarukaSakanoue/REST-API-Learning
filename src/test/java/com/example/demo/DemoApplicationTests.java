package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.controller.ItemController;
import com.example.demo.service.ItemService;
import com.example.demo.repo.ItemRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private ItemController itemController;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemRepository itemRepository;

	//アプリケーションがSpringコンテキストを正常にロードできたかどうかを検証するための基本的なテスト
	@Test
	void contextLoads() {
		//AssertJを使用して、各コンポーネントがnullでないことを確認
		assertThat(itemController).isNotNull();
		assertThat(itemService).isNotNull();
		assertThat(itemRepository).isNotNull();
	}

}
