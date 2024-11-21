package com.meta.notification.email;

import lombok.Getter;

public enum EmailTemplate {

    PAYMENT_COMFIRMATION("payment-confirmation.html","Payment successfully processed"),
    ORDER_COMFIRMATION("order-confirmation.html","Order successfully processed")
    ;
    @Getter
    private final String template;

    @Getter
    private  final String subject;

    EmailTemplate(String template,String subject){
        this.subject=subject;
        this.template=template;
    }


}
