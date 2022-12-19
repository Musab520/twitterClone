package net.twitter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowerCreatedDto {
    private String followedId;
}
