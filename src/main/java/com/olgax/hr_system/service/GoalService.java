package com.olgax.hr_system.service;

import com.olgax.hr_system.entity.Goal;
import com.olgax.hr_system.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    // 1. (Create Goal)
    public Goal createGoal(Goal goal) {
        if (goal.getStatus() == null) {
            goal.setStatus("IN_PROGRESS");
        }
        return goalRepository.save(goal);
    }

    // 2. (Update Progress & Status)
    public Goal updateGoalProgress(Long id, int progressPercent, String status) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found with id: " + id));
        
        goal.setProgressPercent(progressPercent);
        if (status != null) {
            goal.setStatus(status);
        }
        return goalRepository.save(goal);
    }

    // 3. get all goals
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    // 4. Delete a goal and return a message with its name
    public String deleteGoal(Long id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found with id: " + id));
        
        String goalTitle = goal.getTitle(); // take name of the goal
        goalRepository.deleteById(id);
        
        return "Goal deleted successfully: " + goalTitle;
    }
}