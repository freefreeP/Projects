package engine.quiz;

import engine.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final CompletedRepository completedRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, CompletedRepository completedRepository) {
        this.quizRepository = quizRepository;
        this.completedRepository = completedRepository;
    }

    public void delete(int id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();


        if (!quizRepository.existsById((long) id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ""
            );
        }


        if (!quizRepository.findById((long) id).get().getAuthor().equals(currentPrincipalName)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, ""
            );

        }


        quizRepository.deleteById((long) id);
        throw new ResponseStatusException(
                HttpStatus.NO_CONTENT, ""
        );


    }


    public Page<Completed> completedQuizzes(int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return completedRepository.getAll(currentPrincipalName,PageRequest.of(page,10, Sort.Direction.DESC,"completedAt"));

    }


    public Quiz getSingleQuiz(int id) {


        Optional<Quiz> student = quizRepository.findById((long) id);
        if (!student.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }

        return student.get();


    }

    public Page<Quiz> getAllQuiz(int id) {
        return quizRepository.getAll(PageRequest.of(id, 10));
    }

    public Quiz addQuiz(@Valid @RequestBody Quiz quiz) {
        quizRepository.save(quiz);
        return quiz;
    }

    public ServerResponse solveQuiz(int id, quizSolution answer) {
        System.out.println("ich löse " + id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();



        Optional<Quiz> quizOptional = quizRepository.findById((long) id);
        if (!quizOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }

        Quiz quiz = quizOptional.get();



        if (answer.getAnswer() != null && quiz.getAnswer() != null) {
            if (answer.getAnswer().length != quiz.getAnswer().length) {
                return ServerResponse.FAIL;
            }
        } else { //HIER WAR DER KNACKPUNKT
            if (answer.getAnswer() == null && quiz.getAnswer() == null || answer.getAnswer() != null && answer.getAnswer().length == 0 && quiz.getAnswer() == null
                    || quiz.getAnswer() != null && quiz.getAnswer().length == 0 && answer.getAnswer() == null) {

                completedRepository.save(new Completed(currentPrincipalName,Math.toIntExact(quizOptional.get().getId()), LocalDateTime.now()));
                return ServerResponse.SUCCES;
            } else {
                return ServerResponse.FAIL;
            }
        }


        for (int i = 0; i < quiz.getAnswer().length; i++) {
            if (quiz.getAnswer()[i] != answer.getAnswer()[i]) {
                return ServerResponse.FAIL;
            }
        }


        completedRepository.save(new Completed(currentPrincipalName,Math.toIntExact(quizOptional.get().getId()), LocalDateTime.now()));
        return ServerResponse.SUCCES;

    }

}
