package ng.com.codetrik.notepad.todo;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ng.com.codetrik.notepad.util.DateDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ITaskService {
    Single<Task> getTaskById(UUID id);
    Single<Task> createTask(Task task);
    Single<Task> updateTask(Task task, UUID id);
    Completable deleteTask(UUID id);
    Single<Todo> getTodo(UUID id);
    Observable<Task> getTasks();
    DateDTO constructUpdateTime(Task task, DateDTO dateDTO);
    DateDTO constructCreationTime(Task task, DateDTO dateDTO);
    DateDTO constructTimeToAccomplishTask(Task task, DateDTO dateDTO);
    LocalDateTime createTimeToGetTaskAccomplished(Task task);
}
