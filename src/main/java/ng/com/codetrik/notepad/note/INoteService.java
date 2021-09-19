package ng.com.codetrik.notepad.note;

import io.reactivex.rxjava3.core.Single;

import java.util.UUID;

public interface INoteService {
    Single<Note> getNoteById(UUID id);
    Single<Note> setNoteById(Note note);
}
