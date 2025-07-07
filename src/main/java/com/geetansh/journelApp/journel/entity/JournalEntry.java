package com.geetansh.journelApp.journel.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document  //MongoDB mapped entity
@Getter
@Setter
public class JournalEntry {


    @Id  // mapped as primary key
    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime date;

}
