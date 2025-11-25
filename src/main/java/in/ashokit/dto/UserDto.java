package in.ashokit.dto;

import lombok.Data;

@Data
public class UserDto {

    private Integer userId;

    private String name;

    private String email;

    private String pwd;

    private String pwdUpdated;

    private Long phno;

    private Integer countryId;

    private Integer stateId;

    private Integer cityId;
}
