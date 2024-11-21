package com.meta.customer.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerNotFindException extends RuntimeException {
    private final String msg;
}
