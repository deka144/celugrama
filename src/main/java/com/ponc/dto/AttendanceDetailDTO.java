package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDetailDTO {

    private Integer idAttendanceDetail;
    @NotNull
    private Integer idAttendance;
    @NotNull
    private Integer idMemberCell;

    private Boolean attendance=false;
}
