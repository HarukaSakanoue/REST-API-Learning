package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.ItemRepository;
import com.example.demo.model.Item;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private ItemRepository itemRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 初期データの投入
		itemRepository.save(new Item("ネックレス", "ジュエリー"));
		itemRepository.save(new Item("パーカー", "ファッション"));
		itemRepository.save(new Item("フェイスクリーム", "ビューティ"));
		itemRepository.save(new Item("サプリメント", "ヘルス"));
		itemRepository.save(new Item("ブルーベリー", "フード"));	
	}

}
