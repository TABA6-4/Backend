package com.example.focus.service;


import com.example.focus.dto.planner.planRequestDTO;
import com.example.focus.dto.planner.planResponseDTO;
import com.example.focus.entity.Planner;
import com.example.focus.entity.User;
import com.example.focus.entity.VideoSession;
import com.example.focus.repository.PlannerRepository;
import com.example.focus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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


    // 기존 메서드 수정
    public List<planResponseDTO> getPlannersByUserId(Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found with ID: " + userId)
        );

        List<Planner> planners;
        if (date != null) {
            planners = plannerRepository.findPlannersByUserAndDeadline(user, date);
        } else {
            planners = plannerRepository.findPlannersByUser(user);
        }

        return planners.stream()
                .map(planner -> new planResponseDTO(
                        planner.getPlanner_id(),
                        planner.getTitle(),
                        planner.getDate(),
                        planner.getState()
                ))
                .collect(Collectors.toList());
    }

    public Planner createSessionPlanner(VideoSession videoSession){
        Planner planner = new Planner(videoSession);
        if(planner == null){
            return null;
        }

        plannerRepository.save(planner);
        return planner;

    }

}
