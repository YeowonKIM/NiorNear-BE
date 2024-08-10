package nior_near.server.domain.letter.repository;

import nior_near.server.domain.letter.entity.Letter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {

    @Query("SELECT l FROM Letter l WHERE l.createdAt >= :startDate AND l.receiver.id = :receiverId ORDER BY l.createdAt DESC")
    List<Letter> findALlByReceiverId(@Param("receiverId") long receiverId,
                                     @Param("startDate") LocalDateTime startDate,
                                     Pageable pageable);
}
