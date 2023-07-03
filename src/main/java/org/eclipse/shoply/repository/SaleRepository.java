package org.eclipse.shoply.repository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{

	List<Sale> findByPosts(Post post);


	@Query("SELECT s FROM Sale s WHERE s.dateAndTime BETWEEN ?1 AND ?2")
    List<Sale> findByDateAndTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);


    default List<Sale> findByYear(int year) {
        LocalDateTime startDateTime = LocalDateTime.of(year, Month.JANUARY, 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(year, Month.DECEMBER, 31, 23, 59, 59);
        return findByDateAndTimeBetween(startDateTime, endDateTime);
    }

}
