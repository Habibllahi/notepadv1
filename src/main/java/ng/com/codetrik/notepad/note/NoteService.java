package ng.com.codetrik.notepad.note;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.Data;
import ng.com.codetrik.notepad.util.DateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Data
public class NoteService implements INoteService{
    @Autowired
    INoteRepository noteRepository;

    @Autowired
    DateDTO dateDTO;

    @Override
    public Single<Note> getNoteById(UUID id) {
        return Single.just(getNoteByIdProcess(id,dateDTO));
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
        return Observable.fromStream(getNotesProcess(dateDTO));
    }

    private Note updateNoteProcess(Note note, UUID id) {
        return noteRepository.findById(id).map(fetchedNote -> {
            fetchedNote.setTitle(note.getTitle());
            fetchedNote.setBody(note.getBody());
            return noteRepository.save(fetchedNote);
        }).get();
    }

    private Stream<Note> getNotesProcess(DateDTO dateDTO){
        return noteRepository.findAll().stream().map(
                note -> {
                    note.setCreationTime(constructCreationTime(note,dateDTO));
                    note.setUpdateTime(constructUpdateTime(note,dateDTO));
                    return note;
                }
        );
    }

    private void deleteNoteProcess(UUID id){
        noteRepository.delete(noteRepository.getById(id));
    }

    private Note getNoteByIdProcess(UUID id, DateDTO dateDTO){
        return noteRepository.findById(id).map(note -> {
            note.setCreationTime(constructCreationTime(note,dateDTO));
            note.setUpdateTime(constructUpdateTime(note,dateDTO));
            return note;
        }).get();
    }

    private DateDTO constructCreationTime(Note note, DateDTO dateDTO){
        dateDTO.setDayOfWeek(note.getCreationTimestamp().getDayOfWeek());
        dateDTO.setDayOfYear(note.getCreationTimestamp().getDayOfYear());
        dateDTO.setDayOfMonth(note.getCreationTimestamp().getDayOfMonth());
        dateDTO.setMonth(note.getCreationTimestamp().getMonth());
        dateDTO.setYear(note.getCreationTimestamp().getYear());
        dateDTO.setHour(note.getCreationTimestamp().getHour());
        dateDTO.setMinute(note.getCreationTimestamp().getMinute());
        return dateDTO;
    }

    private DateDTO constructUpdateTime(Note note, DateDTO dateDTO){
        dateDTO.setDayOfWeek(note.getUpdateTime().getDayOfWeek());
        dateDTO.setDayOfYear(note.getUpdateTime().getDayOfYear());
        dateDTO.setDayOfMonth(note.getUpdateTime().getDayOfMonth());
        dateDTO.setMonth(note.getUpdateTime().getMonth());
        dateDTO.setYear(note.getUpdateTime().getYear());
        dateDTO.setHour(note.getUpdateTime().getHour());
        dateDTO.setMinute(note.getUpdateTime().getMinute());
        return dateDTO;
    }
}
