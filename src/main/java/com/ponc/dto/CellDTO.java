package com.ponc.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CellDTO {

    private Integer idCell;

    @NotNull
    private String level;

    @NotNull
    private String address;

    @NotNull
    private String reference;

    @NotNull
    private String cellular;


    private String phone;

    @NotNull
    private String photoUrl;

    @NotNull
    private LocalDateTime openDate;

    @NotNull
    private String day;

    @NotNull
    private String hour;

    @NotNull
    private String grid;

    @NotNull
    private String type;


    private String observations;

    private Boolean state=true;
}
