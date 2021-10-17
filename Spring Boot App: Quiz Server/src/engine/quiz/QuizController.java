package engine.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import engine.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RestController
@Service
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @DeleteMapping("/api/quizzes/{id}")
    public void delete(@PathVariable int id) {
        quizService.delete(id);
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getSingleQuiz(@PathVariable int id) {
        return quizService.getSingleQuiz(id);
    }

    @GetMapping("/api/quizzes")
    public Page<Quiz> getAllQuiz(@RequestParam(defaultValue = "0") int page) {
         return quizService.getAllQuiz(page);
    }

    @GetMapping("/api/quizzes/completed")
    public Page<Completed> completedQuizzes(@RequestParam(defaultValue = "0") int page) {
        return quizService.completedQuizzes(page);
    }

    @PostMapping("/api/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        quiz.setAuthor(currentPrincipalName);
        return quizService.addQuiz(quiz);

    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ServerResponse solveQuiz(@PathVariable int id, @Valid @RequestBody quizSolution answer) {
        return quizService.solveQuiz(id, answer);
    }


}

class quizSolution {
    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    @NotNull
    private int[] answer;


}

