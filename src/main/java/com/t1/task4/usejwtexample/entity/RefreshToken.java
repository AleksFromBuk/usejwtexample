package com.t1.task4.usejwtexample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    private String value;
}
