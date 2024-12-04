package com.example.focus.service;


import com.example.focus.dto.planner.planRequestDTO;
import com.example.focus.entity.Planner;
import com.example.focus.entity.User;
import com.example.focus.repository.PlannerRepository;
import com.example.focus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlannerService {

    @Autowired
    private final PlannerRepository plannerRepository;
    @Autowired
    private final UserRepository userRepository;

    public Planner createPlanner(planRequestDTO planRequestDTO){
        User user = userRepository.findById(planRequestDTO.getUser_id()).orElse(null);
        if(user == null){
            return null;
        }

        Planner planner = planRequestDTO.toPlanner(planRequestDTO, user);
        if(planner == null){
            return null;
        }

        plannerRepository.save(planner);
        return planner;

    }


    public List<Planner> getPlannersByUserId(Long userId, LocalDate date) {
        // 유저 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found with ID: " + userId)
        );

        // Planner 조회
        if (date != null) {
            // 특정 deadline 기준으로 조회
            return plannerRepository.findPlannersByUserAndDeadline(user, date);
        } else {
            // 모든 Planner 조회
            return null; //plannerRepository.findPlannersByUser(user);
        }

    }
}
