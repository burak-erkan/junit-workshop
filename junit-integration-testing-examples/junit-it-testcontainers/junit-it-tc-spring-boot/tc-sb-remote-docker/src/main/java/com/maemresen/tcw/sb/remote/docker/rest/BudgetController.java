package com.maemresen.tcw.sb.remote.docker.rest;

import com.maemresen.tcw.sb.remote.docker.dto.BudgetCreateRequestDto;
import com.maemresen.tcw.sb.remote.docker.dto.BudgetDto;
import com.maemresen.tcw.sb.remote.docker.dto.StatementCreateDto;
import com.maemresen.tcw.sb.remote.docker.exceptions.NotFoundException;
import com.maemresen.tcw.sb.remote.docker.service.BudgetService;
import com.maemresen.tcw.sb.remote.docker.utils.constants.UriConstant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(UriConstant.Budget.BASE_URI)
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping(UriConstant.Budget.FIND_BY_ID)
    public ResponseEntity<BudgetDto> findById(@PathVariable Long budgetId) {
        return budgetService.findById(budgetId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Budget not found", budgetId));
    }

    @GetMapping(UriConstant.Budget.FIND_ALL)
    public ResponseEntity<List<BudgetDto>> findAll() {
        return ResponseEntity.ok(budgetService.findAll());
    }

    @PostMapping(UriConstant.Budget.CREATE)
    public ResponseEntity<BudgetDto> create(@RequestBody @Valid BudgetCreateRequestDto createRequestDto) {
        return ResponseEntity.ok(budgetService.create(createRequestDto));
    }

    @PostMapping(UriConstant.Budget.ADD_STATEMENT)
    public ResponseEntity<BudgetDto> addStatement(@PathVariable Long budgetId, @RequestBody @Valid StatementCreateDto statementCreateDto) {
        return ResponseEntity.ok(budgetService.addStatement(budgetId, statementCreateDto));
    }

    @DeleteMapping(UriConstant.Budget.REMOVE_STATEMENT)
    public ResponseEntity<Void> removeStatement(@PathVariable Long budgetId, @PathVariable Long statementId) {
        budgetService.removeStatement(budgetId, statementId);
        return ResponseEntity.ok().build();
    }
}
