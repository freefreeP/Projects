package engine.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {

//    @Query("Select s from Quiz s")



    @Query("Select s from Quiz s")
    Page<Quiz> getAll(Pageable pageable);
}
