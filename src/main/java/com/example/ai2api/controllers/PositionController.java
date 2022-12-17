package com.example.ai2api.controllers;

import com.example.ai2api.model.Company;
import com.example.ai2api.model.Position;
import com.example.ai2api.payload.CreatePosition;
import com.example.ai2api.payload.DeletePositions;
import com.example.ai2api.payload.GetCompanyPositions;
import com.example.ai2api.payload.UpdatePosition;
import com.example.ai2api.service.CompanyService;
import com.example.ai2api.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/position")
public class PositionController {
    private final CompanyService companyService;
    private final PositionService positionService;

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Position>> getCompanyPositions(@PathVariable Long companyId) {
        List<Position> positions = positionService.getCompanyPositions(companyId);
        return ResponseEntity.ok(positions);
    }

    @GetMapping
    public ResponseEntity<List<Position>> getCompanyPositionsById(@RequestBody GetCompanyPositions body) {
        Company company = companyService.getCompanyById(body.getCompanyId());
        List<Position> positions = positionService.getCompanyPositionsById(company, body.getPositionIds());
        return ResponseEntity.ok(positions);
    }

    @PostMapping
    public ResponseEntity<Position> createPosition(@RequestBody CreatePosition body) {
        Company company = companyService.getCompanyById(body.getCompanyId());
        Position position = positionService.createPosition(body.getPositionName(), company);
        return ResponseEntity.ok(position);
    }

    @DeleteMapping("/{positionId}/company/{companyId}")
    public ResponseEntity<?> deletePosition(@PathVariable Long positionId, @PathVariable Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        positionService.deletePosition(company, positionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deletePositions(@RequestBody DeletePositions body) {
        Company company = companyService.getCompanyById(body.getCompanyId());
        positionService.deletePositions(company, body.getPositionIds());
        return ResponseEntity.ok().build();
    }

    /**
     * Update the position with the given id, and return the updated position
     *
     * @param positionId The id of the position to be updated.
     * @param body The request body.
     * @return A ResponseEntity object is being returned.
     */
    @PutMapping("/update/{positionId}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long positionId,
                                                   @RequestBody UpdatePosition body) {
        Position position = positionService.updatePosition(positionId, body.getPositionName());
        return ResponseEntity.ok(position);
    }
}
