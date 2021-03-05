package com.plantalive.plantalive.persistence;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class TopicDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private long plantId;

    @NotNull
    private String topicName;

    public TopicDAO(String topicName) {
        this.topicName = topicName;
    }
}
