package cybersoft.java18.crm.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class JobModel {
    private transient int id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int createdUserId;
    private List<TaskModel> tasks;
}
