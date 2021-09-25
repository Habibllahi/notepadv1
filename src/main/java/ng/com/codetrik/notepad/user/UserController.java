package ng.com.codetrik.notepad.user;

import ng.com.codetrik.notepad.util.Authenticate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {
    @Autowired
    IUserService userService;

    @PostMapping()
    public DeferredResult<ResponseEntity<User>> createUser(@RequestBody @Valid User user, BindingResult br){
        var deferredResult = new DeferredResult<ResponseEntity<User>>();
        userService.createUser(user).subscribe(
                createdUser -> {
                    deferredResult.setResult(new ResponseEntity<>(createdUser, HttpStatus.CREATED));
                },
                error -> {
                    deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                });
        return deferredResult;
    }

    @PutMapping("/{id}")
    public DeferredResult<ResponseEntity<User>> updateUser(@PathVariable("id") UUID id, @RequestBody @Valid User user, BindingResult br){
        var deferredResult = new DeferredResult<ResponseEntity<User>>();
        userService.UpdateUser(user,id).subscribe(
                updatedUser -> {
                    deferredResult.setResult(new ResponseEntity<>(updatedUser, HttpStatus.OK));
                },
                error -> {
                    deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                });
        return deferredResult;
    }

    @GetMapping("/authenticate")
    public DeferredResult<ResponseEntity<Authenticate>> isUserExist(){
        var deferredResult = new DeferredResult<ResponseEntity<Authenticate>>();
        userService.userExist().subscribe(authenticate -> {
            deferredResult.setResult(new ResponseEntity<>(authenticate, HttpStatus.OK));
        },error->{
            deferredResult.setErrorResult(new ResponseEntity(new Authenticate(false),HttpStatus.NETWORK_AUTHENTICATION_REQUIRED));
        });
        return deferredResult;
    }
}
