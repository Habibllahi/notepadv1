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
@RequestMapping(path = "/api/v1/todos",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
public class TodoController {
    @Autowired
    ITodoService todoService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public DeferredResult<ResponseEntity<Todo>> getTodo(@PathVariable("id") UUID id){
        var deferredResult = new DeferredResult<ResponseEntity<Todo>>();
        todoService.getTodoById(id).subscribe(todo -> {
            deferredResult.setResult(new ResponseEntity<>(todo, HttpStatus.OK));
        }, error->{
            deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        });
        return deferredResult;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping()
    public DeferredResult<ResponseEntity<Todo>> setTodo(@RequestBody @Valid Todo todo, BindingResult br){
        var deferredResult = new DeferredResult<ResponseEntity<Todo>>();
        todoService.createTodo(todo).subscribe(savedTodo->{
            deferredResult.setResult(new ResponseEntity<>(savedTodo,HttpStatus.CREATED));
        },error->{
            deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
        });
        return deferredResult;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    public DeferredResult<ResponseEntity<Todo>> updateTodo(@RequestBody Todo todo, @PathVariable("id") UUID id, BindingResult br){
        var deferredResult = new DeferredResult<ResponseEntity<Todo>>();
        todoService.updateTodo(todo,id).subscribe(updateTodo->{
            deferredResult.setResult(new ResponseEntity<>(updateTodo,HttpStatus.OK));
        },error->{
            deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        });
        return deferredResult;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public DeferredResult<ResponseEntity> deleteTodo(@PathVariable("id") UUID id){
        var deferredResult = new DeferredResult<ResponseEntity>();
        todoService.deleteTodo(id).subscribe(()-> {
            deferredResult.setResult(new ResponseEntity(HttpStatus.OK));
        }, error->{
            deferredResult.setErrorResult(new ResponseEntity(HttpStatus.EXPECTATION_FAILED));
        });
        return deferredResult;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping()
    public  DeferredResult<ResponseEntity<List<Todo>>> getNotes(){
        var deferredResult = new DeferredResult<ResponseEntity<List<Todo>>>();
        List<Todo> todoList = new ArrayList<>();
        todoService.getTodos().subscribe(todo->{
            todoList.add(todo);
        },error->{
            deferredResult.setErrorResult(new ResponseEntity(HttpStatus.EXPECTATION_FAILED));
        },()->{
            deferredResult.setResult(new ResponseEntity<>(todoList,HttpStatus.OK));
        });
        return deferredResult;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}/tasks")
    public DeferredResult<ResponseEntity<List<Task>>> getTaskAssociatedWithATodo(@PathVariable("id") UUID id){
        var deferredResult = new DeferredResult<ResponseEntity<List<Task>>>();
        List<Task> taskList = new ArrayList<>();
        todoService.getTasks(id).subscribe(task -> {
            taskList.add(task);
        }, error->{
            deferredResult.setErrorResult(new ResponseEntity(HttpStatus.EXPECTATION_FAILED));
        }, ()->{
            deferredResult.setResult(new ResponseEntity<>(taskList,HttpStatus.OK));
        });
        return deferredResult;
    }
}
