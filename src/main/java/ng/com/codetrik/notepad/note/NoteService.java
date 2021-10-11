/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.note;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ng.com.codetrik.notepad.util.DateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class NoteService implements INoteService{
    @Autowired
    INoteRepository noteRepository;

    @Autowired
    DateDTO dateDTO;

    @Override
    public Single<Note> getNoteById(UUID id) {

        return Single.just(noteRepository.findById(id).get()).map(note->{
            note.setUpdateTime(constructUpdateTime(note,dateDTO));
            note.setCreationTime(constructCreationTine(note,dateDTO));
            return note;
        });
    }

    @Override
    public Single<Note> createNote(Note note) {
        return Single.just(noteRepository.save(note)).map(savedNote->{
            savedNote.setUpdateTime(constructUpdateTime(savedNote,dateDTO));
            savedNote.setCreationTime(constructCreationTine(savedNote,dateDTO));
            return savedNote;
        });
    }

    @Override
    public Single<Note> updateNote(Note note, UUID id) {
        return Single.just(updateNoteProcess(note,id)).map(updatedNote ->{
            updatedNote.setUpdateTime(constructUpdateTime(updatedNote,dateDTO));
            updatedNote.setCreationTime(constructCreationTine(updatedNote,dateDTO));
            return updatedNote;
        });
    }

    @Override
    public Completable deleteNote(UUID id) {
        return Completable.fromRunnable(()->deleteNoteProcess(id));
    }

    @Override
    public Observable<Note> getNotes() {
        return Observable.fromStream(noteRepository.findAll().stream()).map(note->{
            note.setUpdateTime(constructUpdateTime(note,dateDTO));
            note.setCreationTime(constructCreationTine(note,dateDTO));
            return note;
        });
    }

    @Override
    public DateDTO constructUpdateTime(Note note, DateDTO dateDTO) {
        var localDateTime = note.getUpdateTimestamp();
        dateDTO.setMinute(localDateTime.getMinute());
        dateDTO.setHour(localDateTime.getHour());
        dateDTO.setYear(localDateTime.getYear());
        dateDTO.setMonth(localDateTime.getMonth());
        dateDTO.setDayOfMonth(localDateTime.getDayOfMonth());
        dateDTO.setDayOfWeek(localDateTime.getDayOfWeek());
        dateDTO.setDayOfYear(localDateTime.getDayOfYear());
        return dateDTO;
    }

    @Override
    public DateDTO constructCreationTine(Note note, DateDTO dateDTO) {
        var localDateTime = note.getCreationTimestamp();
        dateDTO.setMinute(localDateTime.getMinute());
        dateDTO.setHour(localDateTime.getHour());
        dateDTO.setYear(localDateTime.getYear());
        dateDTO.setMonth(localDateTime.getMonth());
        dateDTO.setDayOfMonth(localDateTime.getDayOfMonth());
        dateDTO.setDayOfWeek(localDateTime.getDayOfWeek());
        dateDTO.setDayOfYear(localDateTime.getDayOfYear());
        return dateDTO;
    }

    private Note updateNoteProcess(Note note, UUID id) {
        return noteRepository.findById(id).map(fetchedNote -> {
            fetchedNote.setTitle(note.getTitle());
            fetchedNote.setBody(note.getBody());
            return noteRepository.save(fetchedNote);
        }).get();
    }
    private void deleteNoteProcess(UUID id){
        noteRepository.delete(noteRepository.getById(id));
    }



}
