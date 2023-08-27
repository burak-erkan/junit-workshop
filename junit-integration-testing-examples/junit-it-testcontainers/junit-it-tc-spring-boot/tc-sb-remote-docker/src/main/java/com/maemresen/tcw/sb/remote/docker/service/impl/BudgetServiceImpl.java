package com.maemresen.tcw.sb.remote.docker.service.impl;

import com.maemresen.tcw.sb.remote.docker.aspects.annotations.BusinessMethod;
import com.maemresen.tcw.sb.remote.docker.dto.BudgetCreateRequestDto;
import com.maemresen.tcw.sb.remote.docker.dto.BudgetDto;
import com.maemresen.tcw.sb.remote.docker.dto.FieldErrorDto;
import com.maemresen.tcw.sb.remote.docker.dto.StatementCreateDto;
import com.maemresen.tcw.sb.remote.docker.entity.BudgetEntity;
import com.maemresen.tcw.sb.remote.docker.entity.StatementEntity;
import com.maemresen.tcw.sb.remote.docker.entity.base.BaseEntity;
import com.maemresen.tcw.sb.remote.docker.exceptions.NotFoundException;
import com.maemresen.tcw.sb.remote.docker.mapper.BudgetMapper;
import com.maemresen.tcw.sb.remote.docker.mapper.StatementMapper;
import com.maemresen.tcw.sb.remote.docker.repository.BudgetRepository;
import com.maemresen.tcw.sb.remote.docker.service.BudgetService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Validated
@Service
public class BudgetServiceImpl implements BudgetService {

    public static final String BUDGET_NOT_FOUND = "Budget not found";
    public static final String STATEMENT_NOT_FOUND = "Statement not found";

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final StatementMapper statementMapper;

    @BusinessMethod
    @Override
    public Optional<BudgetDto> findById(@NotNull Long mockBudgetId1) {
        return budgetRepository.findById(mockBudgetId1).map(budgetMapper::mapToBudgetDto);
    }

    @BusinessMethod
    @Override
    public List<BudgetDto> findAll() {
        return budgetRepository.findAll().stream()
                .map(budgetMapper::mapToBudgetDto)
                .toList();
    }

    @BusinessMethod
    @Transactional
    @Override
    public BudgetDto create(@Valid BudgetCreateRequestDto budgetCreateRequestDto) {
        BudgetEntity budgetEntity = budgetMapper.mapToBudgetEntity(budgetCreateRequestDto);
        return budgetMapper.mapToBudgetDto(budgetRepository.save(budgetEntity));
    }

    @BusinessMethod
    @Transactional
    @Override
    public BudgetDto addStatement(@NotNull(message = "Budget Id cannot be null") Long budgetId, @Valid StatementCreateDto statementCreateDto) {
        BudgetEntity budgetEntity = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new NotFoundException(BUDGET_NOT_FOUND, List.of(
                        FieldErrorDto.withFieldClass(BudgetEntity.class, BaseEntity.Fields.id, BUDGET_NOT_FOUND, budgetId)
                )));
        StatementEntity statementEntity = statementMapper.mapToStatementEntity(statementCreateDto);
        var statements = budgetEntity.getStatements();
        if (statements == null) {
            statements = new ArrayList<>();
            budgetEntity.setStatements(statements);
        }

        statements.add(statementEntity);
        statementEntity.setBudget(budgetEntity);

        return budgetMapper.mapToBudgetDto(budgetRepository.save(budgetEntity));
    }

    @BusinessMethod
    @Transactional
    @Override
    public BudgetDto removeStatement(@NotNull(message = "Budget Id cannot be null") Long budgetId, @NotNull(message = "Statement Id cannot be null") Long statementId) {
        BudgetEntity budgetEntity = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new NotFoundException(BUDGET_NOT_FOUND, List.of(
                        FieldErrorDto.withFieldClass(BudgetEntity.class, BaseEntity.Fields.id, BUDGET_NOT_FOUND, budgetId)
                )));

        var statements = budgetEntity.getStatements();
        if (!CollectionUtils.emptyIfNull(statements).removeIf(statementEntity -> statementEntity.getId().equals(statementId))) {
            throw new NotFoundException(STATEMENT_NOT_FOUND, List.of(
                    FieldErrorDto.withFieldClass(StatementEntity.class, BaseEntity.Fields.id, STATEMENT_NOT_FOUND, statementId)
            ));
        }

        return budgetMapper.mapToBudgetDto(budgetRepository.save(budgetEntity));
    }
}
