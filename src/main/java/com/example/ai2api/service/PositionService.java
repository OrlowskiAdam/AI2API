package com.example.ai2api.service;

import com.example.ai2api.model.Company;
import com.example.ai2api.model.Position;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface PositionService {
    List<Position> getCompanyPositions(Long companyId);
    List<Position> getCompanyPositionsById(Company company, Set<Long> ids);
    Position getCompanyPositionById(Company company, Long id);
    Position createPosition(String positionName, Company company);

    void deletePosition(Company company, Long positionId);
    void deletePositions(Company company, Set<Long> positionIds);

    Position updatePosition(Long positionId, String positionName);
}
