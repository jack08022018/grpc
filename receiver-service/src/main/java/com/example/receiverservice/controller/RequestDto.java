package com.example.receiverservice.controller;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto<T> {
    @JsonIgnore
    public String lmid = "lmid";

    public String requestID;

    public T data;
}
