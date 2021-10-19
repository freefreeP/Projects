package engine.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedRepository extends CrudRepository<Completed,Integer> {

    //@Query("Select id,completedAt from Completed where author = name")
//    @Query(
//            value = "Select id,completedAt from Completed where Completed.author = name",
//            nativeQuery = true)

//    @Query(
//            value = "Select * from Complete",
//            nativeQuery = true)


//    @Query("Select u.id , u.completedAt from Completed u where u.author = ?1 ")

    @Query("Select u from Completed u where u.author = ?1 ")
    Page<Completed> getAll(String name, Pageable pageable);

    //      "author": "test@google.com",
}
