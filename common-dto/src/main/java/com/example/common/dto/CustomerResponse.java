package com.example.common.dto;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String street,
        String city,
        String state,
        String postalCode,
        String country
) {}
