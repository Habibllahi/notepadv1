package ng.com.codetrik.notepad.note;

import ng.com.codetrik.notepad.util.DateDTO;
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
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping(path = "/api/v1/notes",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
public class NoteController {
    @Autowired
    INoteService noteService;

    @Autowired
    DateDTO dateDTO;

    @GetMapping(path = "/{id}")
    public DeferredResult<ResponseEntity<Note>> getNote(@PathVariable("id") UUID id){
        DeferredResult<ResponseEntity<Note>> deferredResult = new DeferredResult<>();
        AtomicReference<ResponseEntity<Note>> responseEntity = new AtomicReference<>();
        noteService.getNoteById(id).subscribe(
                note -> {
                    note.setUpdateTime(noteService.constructUpdateTime(note,dateDTO));
                    note.setCreationTime(noteService.constructCreationTine(note,dateDTO));
                    responseEntity.set(new ResponseEntity<>(note, HttpStatus.OK));
                    deferredResult.setResult(responseEntity.get());
                },error->{
                    responseEntity.set(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                    deferredResult.setErrorResult(responseEntity.get());
                });
        return  deferredResult;
    }

    @PostMapping()
    public DeferredResult<ResponseEntity<Note>> setNote(@RequestBody @Valid Note note, BindingResult br){
        DeferredResult<ResponseEntity<Note>> deferredResult = new DeferredResult<>();
        AtomicReference<ResponseEntity<Note>> responseEntity = new AtomicReference<>();
        noteService.createNote(note).subscribe(
                savedNote -> {
                    savedNote.setUpdateTime(noteService.constructUpdateTime(savedNote,dateDTO));
                    savedNote.setCreationTime(noteService.constructCreationTine(savedNote,dateDTO));
                    responseEntity.set(new ResponseEntity<>(savedNote, HttpStatus.CREATED));
                    deferredResult.setResult(responseEntity.get());
                },error->{
                    responseEntity.set(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                    deferredResult.setErrorResult(responseEntity.get());
                });
        return  deferredResult;
    }

    @PutMapping(path = "/{id}")
    public DeferredResult<ResponseEntity<Note>> getNote(@RequestBody @Valid Note note, @PathVariable("id") UUID id, BindingResult br) {
        DeferredResult<ResponseEntity<Note>> deferredResult = new DeferredResult<>();
        AtomicReference<ResponseEntity<Note>> responseEntity = new AtomicReference<>();
        noteService.updateNote(note,id).subscribe(
                updatedNote -> {
                    updatedNote.setUpdateTime(noteService.constructUpdateTime(updatedNote,dateDTO));
                    updatedNote.setCreationTime(noteService.constructCreationTine(updatedNote,dateDTO));
                    responseEntity.set(new ResponseEntity<>(updatedNote, HttpStatus.OK));
                    deferredResult.setResult(responseEntity.get());
                },error->{
                    responseEntity.set(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                    deferredResult.setErrorResult(responseEntity.get());
                });
        return  deferredResult;
    }

    @DeleteMapping(path = "/{id}")
    public DeferredResult<ResponseEntity> deleteNote(@PathVariable("id") UUID id){
        DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>();
        AtomicReference<ResponseEntity> responseEntity = new AtomicReference<>();
        noteService.deleteNote(id).subscribe(()->{
                    responseEntity.set(new ResponseEntity(HttpStatus.OK));
                    deferredResult.setResult(responseEntity.get());
                },error->{
                    responseEntity.set(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                    deferredResult.setErrorResult(responseEntity.get());
                });
        return deferredResult;
    }

    @GetMapping()
    public DeferredResult<ResponseEntity<List<Note>>> getNotes(){
        DeferredResult<ResponseEntity<List<Note>>> deferredResult = new DeferredResult<>();
        AtomicReference<ResponseEntity<List<Note>>> responseEntity = new AtomicReference<>();
        List<Note> noteList = new ArrayList<>();
        noteService.getNotes().subscribe(note -> {
                    note.setUpdateTime(noteService.constructUpdateTime(note,dateDTO));
                    note.setCreationTime(noteService.constructCreationTine(note,dateDTO));
                    noteList.add(note);
                },error->{
                    responseEntity.set(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                    deferredResult.setErrorResult(responseEntity.get());
                },()->{
                    responseEntity.set(new ResponseEntity<>(noteList,HttpStatus.OK));
                    deferredResult.setResult(responseEntity.get());
                });
        return deferredResult;
    }
}
