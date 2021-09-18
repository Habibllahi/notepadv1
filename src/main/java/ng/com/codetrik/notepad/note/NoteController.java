package ng.com.codetrik.notepad.note;

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
@RequestMapping(path = "/api/v1/notes",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
public class NoteController {
    @Autowired
    INoteService noteService;

    @GetMapping(path = "/{id}")
    public DeferredResult<ResponseEntity<Note>> getNote(@PathVariable("id") UUID id){
        DeferredResult<ResponseEntity<Note>> deferredResult = new DeferredResult<>();
        AtomicReference<ResponseEntity<Note>> responseEntity = null;
        noteService.getNoteById(id).subscribe(
                note -> {
                    responseEntity.set(new ResponseEntity<>(note, HttpStatus.OK));
                },error->{
                    responseEntity.set(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                });
        deferredResult.setResult(responseEntity.get());
        return  deferredResult;
    }

    @PostMapping()
    public DeferredResult<ResponseEntity<Note>> setNote(@RequestBody @Valid Note note, BindingResult br){
        DeferredResult<ResponseEntity<Note>> deferredResult = new DeferredResult<>();
        AtomicReference<ResponseEntity<Note>> responseEntity = null;
        noteService.setNoteById(note).subscribe(
                savedNote -> {
                    responseEntity.set(new ResponseEntity<>(savedNote, HttpStatus.CREATED));
                },error->{
                    responseEntity.set(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                });
        deferredResult.setResult(responseEntity.get());
        return  deferredResult;
    }
}
