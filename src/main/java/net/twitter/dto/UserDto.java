package net.twitter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String id;
    private String email;
    private boolean isActive;
}
