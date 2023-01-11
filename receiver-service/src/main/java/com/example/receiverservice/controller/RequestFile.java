package com.example.receiverservice.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RequestFile extends RequestDto {
    public String fileId;
}
