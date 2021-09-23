package ng.com.codetrik.notepad.todo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import ng.com.codetrik.notepad.util.DateDTO;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "todo")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonPropertyOrder(alphabetic = true)
public class Todo {
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

    @Column(name = "title", nullable = false)
    @NotNull
    private String title;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private DateDTO creationTime;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private DateDTO updateTime;

    @OneToMany(mappedBy = "todo")
    @JsonIgnore
    private List<Task> tasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}