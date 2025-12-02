package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private Integer idMenu;

    @NotNull
    private String name;

    @NotNull
    private String url;

    @NotNull
    private String icon;

    private Boolean state=true;
}
