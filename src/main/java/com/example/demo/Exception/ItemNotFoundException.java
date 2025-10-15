package com.example.demo.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)


public class ItemNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public ItemNotFoundException(String itemId) {
        super("商品コード: " + itemId + " は見つかりません");
    }

    public ItemNotFoundException(Long itemId) {
        super("商品ID: " + itemId + " は見つかりません");
    }
}