package com.plantalive.plantalive.persistence;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
public class TopicDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long plantId;
    @NotNull
    private String topicName;

    public TopicDAO(String topicName) {
        this.topicName = topicName;
    }
}
