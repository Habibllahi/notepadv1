/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.todo;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ng.com.codetrik.notepad.note.INoteService;
import ng.com.codetrik.notepad.util.DateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TaskService implements ITaskService {
    @Autowired
    ITaskRepository taskRepository;

    @Autowired
    ITodoRepository todoRepository;

    @Autowired
    ITodoService todoService;

    @Autowired
    DateDTO dateDTO;

    @Override
    public Single<Task> getTaskById(UUID id) {
        return Single.just(taskRepository.findById(id).get()).map(
                fetchedTask ->{
                    fetchedTask.setCreationTime(constructCreationTime(fetchedTask,dateDTO));
                    fetchedTask.setUpdateTime(constructUpdateTime(fetchedTask,dateDTO));
                    fetchedTask.setTimeToAccomplishTask(constructTimeToAccomplishTask(fetchedTask,dateDTO));
                    fetchedTask.setAssociatedTodoID(fetchedTask.getTodo().getId());
                    return fetchedTask;
                }
        );
    }

    @Override
    public Observable<Task> getTasks() {
        return Observable.fromStream(taskRepository.findAll().stream()).map(
                fetchedTask ->{
                    fetchedTask.setCreationTime(constructCreationTime(fetchedTask,dateDTO));
                    fetchedTask.setAssociatedTodoID(fetchedTask.getTodo().getId());
                    fetchedTask.setUpdateTime(constructUpdateTime(fetchedTask,dateDTO));
                    fetchedTask.setTimeToAccomplishTask(constructTimeToAccomplishTask(fetchedTask,dateDTO));
                    return fetchedTask;
                }
        );
    }

    @Override
    public Single<Task> createTask(Task task) {
        task.setTimeToGetTaskAccomplished(createTimeToGetTaskAccomplished(task));
        task.setTodo(todoRepository.findById(task.getAssociatedTodoID()).get());
        return Single.just(taskRepository.save(task)).map(
                savedTask -> {
                    savedTask.setCreationTime(constructCreationTime(savedTask,dateDTO));
                    savedTask.setUpdateTime(constructUpdateTime(savedTask,dateDTO));
                    savedTask.setTimeToAccomplishTask(constructTimeToAccomplishTask(savedTask,dateDTO));
                    return savedTask;
                }
        );
    }

    @Override
    public Single<Task> updateTask(Task task, UUID id) {

        return Single.just(updateTaskProcess(task,id)).map(
                updatedTask -> {
                    updatedTask.setCreationTime(constructCreationTime(updatedTask, dateDTO));
                    updatedTask.setUpdateTime(constructUpdateTime(updatedTask, dateDTO));
                    updatedTask.setTimeToAccomplishTask(constructTimeToAccomplishTask(updatedTask, dateDTO));
                    updatedTask.setAssociatedTodoID(updatedTask.getTodo().getId());
                    return updatedTask;
                }
        );
    }

    @Override
    public Completable deleteTask(UUID id) {
        return Completable.fromRunnable(()->{
            deleteTaskProcess(id);
        });
    }

    @Override
    public Single<Todo> getTodo(UUID id) {
        return Single.just(taskRepository.findById(id).get().getTodo()).map(
                fetchedTodo -> {
                    fetchedTodo.setCreationTime(todoService.constructCreationTime(fetchedTodo,dateDTO));
                    fetchedTodo.setUpdateTime(todoService.constructUpdateTime(fetchedTodo,dateDTO));
                    return fetchedTodo;
                }
        );
    }

    @Override
    public DateDTO constructUpdateTime(Task task, DateDTO dateDTO) {
        var localDateTime = task.getUpdateTimestamp();
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
    public DateDTO constructCreationTime(Task task, DateDTO dateDTO) {
        var localDateTime = task.getCreationTimestamp();
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
    public DateDTO constructTimeToAccomplishTask(Task task, DateDTO dateDTO) {
        var localDateTime = task.getTimeToGetTaskAccomplished();
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
    public LocalDateTime createTimeToGetTaskAccomplished(Task task) {
        var dateDTO = task.getTimeToAccomplishTask();
        return LocalDateTime.of(dateDTO.getYear(),dateDTO.getMonth(),dateDTO.getDayOfMonth(),dateDTO.getHour(), dateDTO.getMinute());
    }

    private Task updateTaskProcess(Task task, UUID id){
        return taskRepository.findById(id).map(fetchedTask -> {
            fetchedTask.setBody(task.getBody());
            fetchedTask.setTimeToAccomplishTask(task.getTimeToAccomplishTask());
            fetchedTask.setTimeToGetTaskAccomplished(createTimeToGetTaskAccomplished(fetchedTask));
            fetchedTask.setTodo(todoRepository.findById(task.getAssociatedTodoID()).get());
            return taskRepository.save(fetchedTask);
        }).get();
    }

    private void deleteTaskProcess(UUID id) {
        taskRepository.delete(taskRepository.getById(id));
    }
}
