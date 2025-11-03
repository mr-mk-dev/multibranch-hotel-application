package me.manishcodes.hotelapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private String password;
    private String role;
    private Long hotelId;
}
