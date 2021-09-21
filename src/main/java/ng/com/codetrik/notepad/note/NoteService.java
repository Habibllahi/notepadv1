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

    @Override
    public Single<Note> getNoteById(UUID id) {
        return Single.just(getNoteByIdProcess(id));
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
        return Observable.fromStream(getNotesProcess());
    }

    private Note updateNoteProcess(Note note, UUID id) {
        return noteRepository.findById(id).map(fetchedNote -> {
            fetchedNote.setTitle(note.getTitle());
            fetchedNote.setBody(note.getBody());
            return noteRepository.save(fetchedNote);
        }).get();
    }

    private Stream<Note> getNotesProcess(){
        return noteRepository.findAll().stream().map(
                (Note note) -> {
                    note.setCreationTime(new DateDTO(
                            note.getCreationTime().getDayOfWeek(),
                            note.getCreationTime().getDayOfYear(),
                            note.getCreationTime().getDayOfMonth(),
                            note.getCreationTime().getMonth(),
                            note.getCreationTime().getYear(),
                            note.getCreationTime().getHour(),
                            note.getCreationTime().getMinute()
                    ));
                    note.setUpdateTime(new DateDTO(
                            note.getUpdateTime().getDayOfWeek(),
                            note.getUpdateTime().getDayOfYear(),
                            note.getUpdateTime().getDayOfMonth(),
                            note.getUpdateTime().getMonth(),
                            note.getUpdateTime().getYear(),
                            note.getUpdateTime().getHour(),
                            note.getUpdateTime().getMinute()
                    ));
                    return note;
                }
        );
    }

    private void deleteNoteProcess(UUID id){
        noteRepository.delete(noteRepository.getById(id));
    }

    private Note getNoteByIdProcess(UUID id){
        return noteRepository.findById(id).map((Note note) -> {
            note.setCreationTime(new DateDTO(
                    note.getCreationTime().getDayOfWeek(),
                    note.getCreationTime().getDayOfYear(),
                    note.getCreationTime().getDayOfMonth(),
                    note.getCreationTime().getMonth(),
                    note.getCreationTime().getYear(),
                    note.getCreationTime().getHour(),
                    note.getCreationTime().getMinute()
            ));
            note.setUpdateTime(new DateDTO(
                    note.getUpdateTime().getDayOfWeek(),
                    note.getUpdateTime().getDayOfYear(),
                    note.getUpdateTime().getDayOfMonth(),
                    note.getUpdateTime().getMonth(),
                    note.getUpdateTime().getYear(),
                    note.getUpdateTime().getHour(),
                    note.getUpdateTime().getMinute()
            ));
            return note;
        }).get();
    }

}
