package com.example.demo.activity;

import com.example.demo.activity.model.Activity;
import com.example.demo.activity.model.dto.ActivityDTO;
import com.example.demo.item.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    private Activity findById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<ActivityDTO> findAll() {
        return activityRepository.findAll().stream()
                .map(activityMapper::toDto)
                .collect(Collectors.toList());
    }

    public int countEmployeeActivity(String name){
        return activityRepository.countActivityByEmployeeNameEquals(name);
    }

    public Activity create(Activity activity) {
        return activityRepository.save(activity);
    }
}
