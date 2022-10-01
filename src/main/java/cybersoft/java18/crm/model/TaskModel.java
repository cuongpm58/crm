package cybersoft.java18.crm.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
public class TaskModel {
    private transient int id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String jobName;
    private String personInCharge;
    private String statusName;
    private transient int userId;
    private transient int jobId;
    private transient int statusId;
}
