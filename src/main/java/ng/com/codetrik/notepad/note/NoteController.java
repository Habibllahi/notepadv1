package ng.com.codetrik.notepad.note;

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
@RequestMapping(path = "/api/v1/notes",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
public class NoteController {
    @Autowired
    INoteService noteService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/{id}")
    public DeferredResult<ResponseEntity<Note>> getNote(@PathVariable("id") UUID id){
        var deferredResult = new DeferredResult<ResponseEntity<Note>>();
        noteService.getNoteById(id).subscribe(
                note -> {
                    deferredResult.setResult(new ResponseEntity<>(note, HttpStatus.OK));
                },error->{
                    deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                });
        return  deferredResult;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping()
    public DeferredResult<ResponseEntity<Note>> setNote(@RequestBody @Valid Note note, BindingResult br){
        var deferredResult = new DeferredResult<ResponseEntity<Note>>();
        noteService.createNote(note).subscribe(
                savedNote -> {
                    deferredResult.setResult(new ResponseEntity<>(savedNote, HttpStatus.CREATED));
                },error->{
                    deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                });
        return  deferredResult;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(path = "/{id}")
    public DeferredResult<ResponseEntity<Note>> updateNote(@RequestBody @Valid Note note, @PathVariable("id") UUID id, BindingResult br) {
        var deferredResult = new DeferredResult<ResponseEntity<Note>>();
        noteService.updateNote(note,id).subscribe(
                updatedNote -> {
                    deferredResult.setResult(new ResponseEntity<>(updatedNote, HttpStatus.OK));
                },error->{
                    deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                });
        return  deferredResult;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping(path = "/{id}")
    public DeferredResult<ResponseEntity> deleteNote(@PathVariable("id") UUID id){
        var deferredResult = new DeferredResult<ResponseEntity>();
        noteService.deleteNote(id).subscribe(()->{
                    deferredResult.setResult(new ResponseEntity(HttpStatus.OK));
                },error->{
                    deferredResult.setErrorResult(HttpStatus.EXPECTATION_FAILED);
                });
        return deferredResult;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping()
    public DeferredResult<ResponseEntity<List<Note>>> getNotes(){
        var deferredResult = new DeferredResult<ResponseEntity<List<Note>>>();
        List<Note>  noteList = new ArrayList<Note>();
        noteService.getNotes().subscribe(noteList::add, error->{
                    deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                },()->{
                    deferredResult.setResult(new ResponseEntity<>(noteList,HttpStatus.OK));
                });
        return deferredResult;
    }
}
