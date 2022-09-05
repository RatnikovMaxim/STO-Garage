package com.example.id.security;

import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {
    private final Base64StringKeyGenerator generator = new Base64StringKeyGenerator(60);

    public String generate() {
        return generator.generateKey();
    }
} //раздать коллегам токены,что бы они их добавили к своим базам, а они прописываю  вапликейшн пропертиес, в своих проектах
