package com.example.demo2.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemRequestDTO {
    private Long productoId;
    private Integer cantidad;
}
