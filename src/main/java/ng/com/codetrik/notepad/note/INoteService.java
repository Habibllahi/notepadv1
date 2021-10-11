/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.note;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ng.com.codetrik.notepad.util.DateDTO;

import java.util.UUID;

public interface INoteService {
    Single<Note> getNoteById(UUID id);
    Single<Note> createNote(Note note);
    Single<Note> updateNote(Note note, UUID id);
    Completable deleteNote(UUID id);
    Observable<Note> getNotes();
    DateDTO constructUpdateTime(Note note, DateDTO dateDTO);
    DateDTO constructCreationTine(Note note, DateDTO dateDTO);
}
