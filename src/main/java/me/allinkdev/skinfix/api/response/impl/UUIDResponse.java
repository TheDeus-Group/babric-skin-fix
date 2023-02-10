package me.allinkdev.skinfix.api.response.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.allinkdev.skinfix.api.response.Response;
import me.allinkdev.skinfix.api.response.ResponseData;

@ResponseData
@EqualsAndHashCode(callSuper = true)
@Data
public class UUIDResponse extends Response {
    private final String name;
    private final String id;
}
