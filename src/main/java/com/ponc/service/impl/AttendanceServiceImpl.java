package com.ponc.service.impl;

import com.ponc.model.Attendance;
import com.ponc.repo.IAttendanceRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.IAttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttendanceServiceImpl extends CRUDImpl<Attendance,Integer> implements IAttendanceService {

    private final IAttendanceRepo repo;

    @Override
    protected IGenericRepo<Attendance, Integer> getRepo() {
        return repo;
    }
}
