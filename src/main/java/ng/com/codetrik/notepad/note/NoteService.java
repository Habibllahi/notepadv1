package ng.com.codetrik.notepad.note;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.Data;
import ng.com.codetrik.notepad.util.DateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Data
public class NoteService implements INoteService{
    @Autowired
    INoteRepository noteRepository;

    @Override
    public Single<Note> getNoteById(UUID id) {
        return Single.just(getNoteByIdProcess(noteRepository,id));
    }

    @Override
    public Single<Note> createNote(Note note) {
        return Single.just(noteRepository.save(note));
    }

    @Override
    public Single<Note> updateNote(Note note, UUID id) {
        return Single.just(updateNoteProcess(note,id));
    }

    @Override
    public Completable deleteNote(UUID id) {
        return Completable.fromRunnable(()->deleteNoteProcess(id));
    }

    @Override
    public Observable<Note> getNotes() {
        return Observable.fromStream(getNotesProcess(noteRepository));
    }

    private Note updateNoteProcess(Note note, UUID id) {
        return noteRepository.findById(id).map(fetchedNote -> {
            fetchedNote.setTitle(note.getTitle());
            fetchedNote.setBody(note.getBody());
            return noteRepository.save(fetchedNote);
        }).get();
    }

    private Stream<Note> getNotesProcess(INoteRepository noteRepository){
        var noteList = noteRepository.findAll().stream().map(
                note -> {
                    note.setCreationTime(constructCreationTime(note));
                    note.setUpdateTime(constructUpdateTime(note));
                    return note;
                }
        ).collect(Collectors.toList());
        return noteList.stream();
    }

    private void deleteNoteProcess(UUID id){
        noteRepository.delete(noteRepository.getById(id));
    }

    private Note getNoteByIdProcess(INoteRepository noteRepository, UUID id){
        var note = noteRepository.findById(id).get();
        note.setCreationTime(constructCreationTime(note));
        note.setUpdateTime(constructUpdateTime(note));
        return note;
    }

    private DateDTO constructCreationTime(Note note){
        return new DateDTO(
                note.getCreationTime().getDayOfWeek(),
                note.getCreationTime().getDayOfYear(),
                note.getCreationTime().getDayOfMonth(),
                note.getCreationTime().getMonth(),
                note.getCreationTime().getYear(),
                note.getCreationTime().getHour(),
                note.getCreationTime().getMinute()
        );
    }

    private DateDTO constructUpdateTime(Note note){
        return new DateDTO(
                note.getUpdateTime().getDayOfWeek(),
                note.getUpdateTime().getDayOfYear(),
                note.getUpdateTime().getDayOfMonth(),
                note.getUpdateTime().getMonth(),
                note.getUpdateTime().getYear(),
                note.getUpdateTime().getHour(),
                note.getUpdateTime().getMinute()
        );
    }
}
