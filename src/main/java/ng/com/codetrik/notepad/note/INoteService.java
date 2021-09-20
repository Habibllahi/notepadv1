package ng.com.codetrik.notepad.note;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.UUID;

public interface INoteService {
    Single<Note> getNoteById(UUID id);
    Single<Note> createNote(Note note);
    Single<Note> updateNote(Note note, UUID id);
    Completable deleteNote(Note note, UUID id);
    Observable<Note> getNotes();
}
