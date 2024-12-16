package com.example.focus.controller;
import com.example.focus.dto.planner.planRequestDTO;
import com.example.focus.dto.planner.planResponseDTO;
import com.example.focus.entity.Planner;
import com.example.focus.repository.PlannerRepository;
import com.example.focus.service.PlannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/planner")
@RequiredArgsConstructor
@Tag(name = "user Planner", description = "API") // API를 그룹화할 태그명을 지정
public class PlannerController {

    @Autowired
    private final PlannerService plannerService;

    //planner 생성
    @PostMapping()
    @Operation(summary = "Planner 생성", description = "플래너에서 일정 생성시 사용하는 API")
    public ResponseEntity<Planner> createPlan(@RequestBody planRequestDTO planRequestDTODTO) {
        Planner plan = plannerService.createPlanner(planRequestDTODTO);
        return (plan != null) ?
                ResponseEntity.status(HttpStatus.OK).body(plan) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //플래너 조회(useer_id, password로 조회)
    @GetMapping("/{user_id}/{date}")
    @Operation(summary = "유저 Planner 조회", description = "플래너에서 유저id와 날짜로 조회하는 API")
    public ResponseEntity<List<planResponseDTO>> getPlannersByUserId(
            @PathVariable Long user_id,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<planResponseDTO> planners = plannerService.getPlannersByUserId(user_id, date);
        if (planners.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(planners);
    }

}
