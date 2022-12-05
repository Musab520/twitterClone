package net.twitter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TweetDto {
    private int id;
    private String userId;
    private String message;
    private String title;
    private Date createdOn;

}
