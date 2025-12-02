package com.ponc.dto;

import com.ponc.model.AttendanceDetail;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {
    private Integer idAttendance;

    @NotNull
    private Integer idCell;

    @NotNull
    private Integer idPeriod;

    @NotNull
    private Integer offering;

    List<AttendanceDetail> attendanceDetails;
}
