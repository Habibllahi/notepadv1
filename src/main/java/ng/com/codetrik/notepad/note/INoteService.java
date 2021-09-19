package ng.com.codetrik.notepad.note;

import io.reactivex.rxjava3.core.Single;

public interface INoteService {
    Single<Note> getNoteById(Long id);
    Single<Note> setNoteById(Note note);
}
