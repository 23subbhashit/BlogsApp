package com.example.blogs.payloads;

import com.example.blogs.entities.Category;
import com.example.blogs.entities.Comment;
import com.example.blogs.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    //private Date addDate;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments=new HashSet<>();
    private long addDate;

    public long getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        // Convert Date to timestamp (milliseconds)
        this.addDate = addDate.getTime();
    }
}
