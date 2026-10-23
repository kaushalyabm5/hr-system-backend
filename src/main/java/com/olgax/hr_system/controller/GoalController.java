package com.olgax.hr_system.controller;

import com.olgax.hr_system.entity.Goal;
import com.olgax.hr_system.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    // (POST Endpoint)
    @PostMapping
    public Goal createGoal(@RequestBody Goal goal) {
        return goalService.createGoal(goal);
    }

    //(PUT Endpoint)
    @PutMapping("/{id}/progress")
    public Goal updateGoalProgress(@PathVariable Long id, 
                                   @RequestParam int progressPercent, 
                                   @RequestParam(required = false) String status) {
        return goalService.updateGoalProgress(id, progressPercent, status);
    }

    // (GET Endpoint)
    @GetMapping
    public List<Goal> getAllGoals() {
        return goalService.getAllGoals();
    }

    //(DELETE Endpoint)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoal(@PathVariable Long id) {
        String message = goalService.deleteGoal(id);
        return ResponseEntity.ok(message);
    }
}