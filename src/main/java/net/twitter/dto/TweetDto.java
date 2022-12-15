package net.twitter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TweetDto {
    private int id;
    private String authorId;
    private String message;
    private Date createdOn;

}
