/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<Task, UUID>, JpaSpecificationExecutor<Task> {
}