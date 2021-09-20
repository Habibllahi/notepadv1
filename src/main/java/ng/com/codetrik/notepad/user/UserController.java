package ng.com.codetrik.notepad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping(path = "/api/v1/users",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {
    @Autowired
    IUserService userService;

    @PostMapping()
    public DeferredResult<ResponseEntity<User>> createUser(@RequestBody @Valid User user, BindingResult br){
        DeferredResult<ResponseEntity<User>> deferredResult = new DeferredResult<>();
        AtomicReference<ResponseEntity<User>> responseEntity = new AtomicReference<>();
        userService.createUser(user).subscribe(
                createdUser -> {
                    responseEntity.set(new ResponseEntity<>(createdUser, HttpStatus.CREATED));
                    deferredResult.setResult(responseEntity.get());
                },
                error -> {
                    responseEntity.set(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                    deferredResult.setErrorResult(responseEntity.get());
                });
        return deferredResult;
    }

    @PutMapping("/{id}")
    public DeferredResult<ResponseEntity<User>> updateUser(@PathVariable("id") UUID id, @RequestBody @Valid User user, BindingResult br){
        DeferredResult<ResponseEntity<User>> deferredResult = new DeferredResult<>();
        AtomicReference<ResponseEntity<User>> responseEntity = new AtomicReference<>();
        userService.UpdateUser(user,id).subscribe(
                updatedUser -> {
                    responseEntity.set(new ResponseEntity<>(updatedUser, HttpStatus.OK));
                    deferredResult.setResult(responseEntity.get());
                },
                error -> {
                    responseEntity.set(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                    deferredResult.setErrorResult(responseEntity.get());
                });
        return deferredResult;
    }
}
