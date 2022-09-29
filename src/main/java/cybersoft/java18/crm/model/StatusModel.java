package cybersoft.java18.crm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusModel {
    private transient int id;
    private String name;
}
