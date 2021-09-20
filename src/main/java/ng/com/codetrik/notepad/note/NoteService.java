package ng.com.codetrik.notepad.note;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.Data;
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
        return Single.just(noteRepository.findById(id).get());
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
        return noteRepository.findAll().stream();
    }

    private void deleteNoteProcess(UUID id){
        noteRepository.delete(noteRepository.getById(id));
    }
}
