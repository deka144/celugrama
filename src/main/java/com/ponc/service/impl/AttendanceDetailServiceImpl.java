package com.ponc.service.impl;

import com.ponc.model.AttendanceDetail;
import com.ponc.repo.IAttendanceDetailRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.IAttendanceDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttendanceDetailServiceImpl extends CRUDImpl<AttendanceDetail, Integer> implements IAttendanceDetailService {
    private final IAttendanceDetailRepo repo;

    @Override
    protected IGenericRepo<AttendanceDetail, Integer> getRepo() {
        return repo;
    }
}
