package com.example.ai2api.service.impl;

import com.example.ai2api.exception.ResourceNotFoundException;
import com.example.ai2api.model.Company;
import com.example.ai2api.model.Position;
import com.example.ai2api.repository.PositionRepository;
import com.example.ai2api.service.CompanyService;
import com.example.ai2api.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final CompanyService companyService;
    private final PositionRepository positionRepository;
    @Override
    public List<Position> getCompanyPositions(Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        return company.getPositions();
    }

    @Override
    public List<Position> getCompanyPositionsById(Company company, Set<Long> ids) {
        return company.getPositions()
                .stream()
                .filter(position -> ids.stream().anyMatch(id -> id.equals(position.getId())))
                .toList();
    }

    @Override
    public Position getCompanyPositionById(Company company, Long id) {
        return company.getPositions()
                .stream()
                .filter(position -> position.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Position not found"));
    }

    @Override
    public Position createPosition(String positionName, Company company) {
        Position position = new Position(positionName, company);
        return positionRepository.save(position);
    }

    @Override
    public void deletePosition(Company company, Long positionId) {
        Position position = getCompanyPositionById(company, positionId);
        positionRepository.delete(position);
    }

    @Override
    public void deletePositions(Company company, Set<Long> positionIds) {
        List<Position> positions = getCompanyPositionsById(company, positionIds);
        positionRepository.deleteAll(positions);
    }
}
