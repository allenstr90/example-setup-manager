package com.example.setupmagement.entity;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Point {
    private Double longitude;
    private Double latitude;
}
