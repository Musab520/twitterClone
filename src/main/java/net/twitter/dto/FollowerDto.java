package net.twitter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowerDto {
    private int id;
    private String followedId;
    private String followerId;
}
