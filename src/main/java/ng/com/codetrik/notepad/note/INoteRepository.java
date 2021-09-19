package ng.com.codetrik.notepad.note;

import ng.com.codetrik.notepad.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface INoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {

}