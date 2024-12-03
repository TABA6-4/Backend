package com.example.focus.controller;
import com.example.focus.dto.planner.planRequestDTO;
import com.example.focus.entity.Planner;
import com.example.focus.service.PlannerService;
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
public class PlannerController {

    @Autowired
    private final PlannerService plannerService;

    //planner 생성
    @PostMapping()
    public ResponseEntity<Planner> createPlan(@RequestBody planRequestDTO planRequestDTODTO) {
        Planner plan = plannerService.createPlanner(planRequestDTODTO);
        return (plan != null) ?
                ResponseEntity.status(HttpStatus.OK).body(plan) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //플래너 조회(useer_id, password로 조회)
    @GetMapping("/{user_id}/{date}")
    public ResponseEntity<List<Planner>> getPlannersByUserId(
            @PathVariable Long user_id,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Planner> planners = plannerService.getPlannersByUserId(user_id, date);
        if (planners.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(planners);
    }

}
