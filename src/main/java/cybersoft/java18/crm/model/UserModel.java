package cybersoft.java18.crm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
@Builder
public class UserModel {
    private String email;
    private String password;
    private String fullname;
    private String avatar;
    private RoleModel role;


}
