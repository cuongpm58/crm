package cybersoft.java18.crm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoleModel {
    private int id;
    private String name;
    private String description;
}
