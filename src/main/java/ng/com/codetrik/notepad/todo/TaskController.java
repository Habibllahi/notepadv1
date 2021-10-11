/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/tasks",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
public class TaskController {
    @Autowired
    ITaskService taskService;

    @GetMapping("/{id}")
    public DeferredResult<ResponseEntity<Task>> getTask(@PathVariable("id") UUID id){
        var deferredResult = new DeferredResult<ResponseEntity<Task>>();
        taskService.getTaskById(id).subscribe(task -> {
            deferredResult.setResult(new ResponseEntity<>(task, HttpStatus.OK));
        }, error->{
            deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        });
        return deferredResult;
    }

    @PostMapping()
    public DeferredResult<ResponseEntity<Task>> setTask(@RequestBody @Valid Task task, BindingResult br){
        var deferredResult = new DeferredResult<ResponseEntity<Task>>();
        taskService.createTask(task).subscribe(savedTask->{
            deferredResult.setResult(new ResponseEntity<>(savedTask,HttpStatus.CREATED));
        },error->{
            deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
        });
        return deferredResult;
    }

    @PutMapping("/{id}")
    public DeferredResult<ResponseEntity<Task>> updateTask(@RequestBody Task task, @PathVariable("id") UUID id, BindingResult br){
        var deferredResult = new DeferredResult<ResponseEntity<Task>>();
        taskService.updateTask(task,id).subscribe(updateTask->{
            deferredResult.setResult(new ResponseEntity<>(updateTask,HttpStatus.OK));
        },error->{
            deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        });
        return deferredResult;
    }

    @DeleteMapping("/{id}")
    public DeferredResult<ResponseEntity> deleteTask(@PathVariable("id") UUID id){
        var deferredResult = new DeferredResult<ResponseEntity>();
        taskService.deleteTask(id).subscribe(()-> {
            deferredResult.setResult(new ResponseEntity(HttpStatus.OK));
        }, error->{
            deferredResult.setErrorResult(new ResponseEntity(HttpStatus.EXPECTATION_FAILED));
        });
        return deferredResult;
    }

    @GetMapping()
    public  DeferredResult<ResponseEntity<List<Task>>> getTasks(){
        var deferredResult = new DeferredResult<ResponseEntity<List<Task>>>();
        List<Task> taskList = new ArrayList<>();
        taskService.getTasks().subscribe(task->{
            taskList.add(task);
        },error->{
            deferredResult.setErrorResult(new ResponseEntity(HttpStatus.EXPECTATION_FAILED));
        },()->{
            deferredResult.setResult(new ResponseEntity<>(taskList,HttpStatus.OK));
        });
        return deferredResult;
    }
}
