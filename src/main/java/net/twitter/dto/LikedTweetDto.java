package net.twitter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikedTweetDto {
    private int id;
    private int likedId;
    private String likerId;
}
