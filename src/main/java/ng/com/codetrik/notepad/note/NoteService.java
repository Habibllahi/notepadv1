package ng.com.codetrik.notepad.note;

import io.reactivex.rxjava3.core.Single;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class NoteService implements INoteService{
    @Autowired
    INoteRepository noteRepository;

    @Override
    public Single<Note> getNoteById(Long id) {
        return Single.just(noteRepository.findById(id).get());
    }

    @Override
    public Single<Note> setNoteById(Note note) {
        return Single.just(noteRepository.save(note));
    }
}
