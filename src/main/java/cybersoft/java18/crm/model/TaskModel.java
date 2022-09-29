package cybersoft.java18.crm.model;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
public class TaskModel {
    @Expose
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
