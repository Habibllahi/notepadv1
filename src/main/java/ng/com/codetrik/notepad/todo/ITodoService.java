/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.todo;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ng.com.codetrik.notepad.util.DateDTO;

import java.util.UUID;

public interface ITodoService {
    Single<Todo> getTodoById(UUID id);
    Single<Todo> createTodo(Todo todo);
    Single<Todo> updateTodo(Todo todo, UUID id);
    Completable deleteTodo(UUID id);
    Observable<Todo> getTodos();
    Observable<Task> getTasks(UUID id);
    DateDTO constructUpdateTime(Todo todo, DateDTO dateDTO);
    DateDTO constructCreationTime(Todo todo, DateDTO dateDTO);
}
