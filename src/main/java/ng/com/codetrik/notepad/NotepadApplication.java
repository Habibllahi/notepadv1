/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class NotepadApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotepadApplication.class, args);
	}

}
