package com.jaesay.microservices.limitsservice.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Limits {
    private int minimum;
    private int maximum;
}
