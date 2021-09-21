package ng.com.codetrik.notepad.todo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ng.com.codetrik.notepad.util.DateDTO;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "task")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Task {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @CreationTimestamp
    @Column(name="creation_timestamp")
    @JsonIgnore
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    @JsonIgnore
    private LocalDateTime updateTimestamp;

    @Column(name = "time_to_get_task_accomplished", nullable = false)
    @JsonIgnore
    private LocalDateTime timeToGetTaskAccomplished;

    @ManyToOne
    @Column(name = "todo_id")
    private Todo todo;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private DateDTO creationTime;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private DateDTO updateTime;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private DateDTO timeToAccomplishTask; //the time in which the task is set to be accomplished

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}