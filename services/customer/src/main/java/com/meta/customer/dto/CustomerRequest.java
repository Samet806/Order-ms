package com.meta.customer.dto;

import com.meta.customer.customer.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(String id,
                              @NotNull(message = "Customerın firstname  girilmeli")
                              String firstname,
                              @NotNull(message = "Customerın lastname  girilmeli")
                              String lastname,
                              @NotNull(message = "Customerın email  girilmeli")
                              @Email(message = "email geçersiz")
                              String email,
                              Address address) {
}
