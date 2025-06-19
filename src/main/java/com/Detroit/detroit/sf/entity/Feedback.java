package com.Detroit.detroit.sf.entity;

import com.Detroit.detroit.library.EntityDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Feedback extends EntityDetails {

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String title;

    private String documentUrl;
    

}

