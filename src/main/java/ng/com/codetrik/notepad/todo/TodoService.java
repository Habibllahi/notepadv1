/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.todo;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.Data;
import ng.com.codetrik.notepad.util.DateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TodoService implements ITodoService {
    @Autowired
    ITodoRepository todoRepository;

    @Autowired
    ITaskService taskService;

    @Autowired
    DateDTO dateDTO;

    @Override
    public Single<Todo> getTodoById(UUID id) {
        return Single.just(todoRepository.findById(id).get()).map(todo -> {
            todo.setCreationTime(constructCreationTime(todo,dateDTO));
            todo.setUpdateTime(constructUpdateTime(todo,dateDTO));
            return todo;
        });
    }

    @Override
    public Single<Todo> createTodo(Todo todo) {
        return Single.just(todoRepository.save(todo)).map(savedTodo -> {
            savedTodo.setCreationTime(constructCreationTime(savedTodo,dateDTO));
            savedTodo.setUpdateTime(constructUpdateTime(savedTodo,dateDTO));
            return savedTodo;
        });
    }

    @Override
    public Single<Todo> updateTodo(Todo todo, UUID id) {
        return Single.just(updateTodoProcess(todo,id)).map(updatedTodo -> {
            updatedTodo.setCreationTime(constructCreationTime(updatedTodo,dateDTO));
            updatedTodo.setUpdateTime(constructUpdateTime(updatedTodo,dateDTO));
            return updatedTodo;
        });
    }

    @Override
    public Completable deleteTodo(UUID id) {return Completable.fromRunnable(()->deleteTodoProcess(id));}

    @Override
    public Observable<Todo> getTodos() {
        return Observable.fromStream(todoRepository.findAll().stream()).map(fetchedTodo -> {
            fetchedTodo.setCreationTime(constructCreationTime(fetchedTodo,dateDTO));
            fetchedTodo.setUpdateTime(constructUpdateTime(fetchedTodo,dateDTO));
            return fetchedTodo;
        });
    }

    @Override
    public Observable<Task> getTasks(UUID id) {
        return Observable.fromStream(todoRepository.findById(id).get().getTasks().stream()).map(
                fetchedTask ->{
                    fetchedTask.setCreationTime(taskService.constructCreationTime(fetchedTask,dateDTO));
                    fetchedTask.setUpdateTime(taskService.constructUpdateTime(fetchedTask,dateDTO));
                    fetchedTask.setTimeToAccomplishTask(taskService.constructTimeToAccomplishTask(fetchedTask,dateDTO));
                    fetchedTask.setAssociatedTodoID(fetchedTask.getTodo().getId());
                    return fetchedTask;
                }
        );
    }

    @Override
    public DateDTO constructUpdateTime(Todo todo, DateDTO dateDTO) {
        var localDateTime = todo.getUpdateTimestamp();
        dateDTO.setDayOfYear(localDateTime.getDayOfYear());
        dateDTO.setMonth(localDateTime.getMonth());
        dateDTO.setMinute(localDateTime.getMinute());
        dateDTO.setHour(localDateTime.getHour());
        dateDTO.setYear(localDateTime.getYear());
        dateDTO.setDayOfWeek(localDateTime.getDayOfWeek());
        dateDTO.setDayOfMonth(localDateTime.getDayOfMonth());
        return dateDTO;
    }

    @Override
    public DateDTO constructCreationTime(Todo todo, DateDTO dateDTO) {
        var localDateTime = todo.getCreationTimestamp();
        dateDTO.setDayOfYear(localDateTime.getDayOfYear());
        dateDTO.setMonth(localDateTime.getMonth());
        dateDTO.setMinute(localDateTime.getMinute());
        dateDTO.setHour(localDateTime.getHour());
        dateDTO.setYear(localDateTime.getYear());
        dateDTO.setDayOfWeek(localDateTime.getDayOfWeek());
        dateDTO.setDayOfMonth(localDateTime.getDayOfMonth());
        return dateDTO;
    }

    private Todo updateTodoProcess(Todo todo, UUID id){
        return todoRepository.findById(id).map(
                fetchedTodo -> {
                    fetchedTodo.setTitle(todo.getTitle());
                    return todoRepository.save(fetchedTodo);
                }
        ).get();
    }

    private void deleteTodoProcess(UUID id){todoRepository.delete(todoRepository.getById(id));}
}
