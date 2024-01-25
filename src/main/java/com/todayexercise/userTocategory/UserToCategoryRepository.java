package com.todayexercise.userTocategory;

import com.todayexercise.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public interface UserToCategoryRepository extends JpaRepository<UserToCategory, Integer> {
//    @Modifying // INSERT, DELETE, UPDATE 를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요!!
//    @Query(value = "INSERT INTO user_category(user_id, category_id) VALUES(:user_id, :category_id)", nativeQuery = true)
//    void registCategory(@Param("user_id") Long user_index, @Param("category_id") Long category_id);



//    @Modifying
//    @Query(value = "DELETE FROM subscribe_tb WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
//    void mUnSubscribe(@Param("fromUserId") int fromUserId, @Param("toUserId") int toUserId);

//    @Modifying
//    @Query(value = "SELECT * From category JOIN user_category on category.category = ", nativeQuery = true)
//    void mUnSubscribe(@Param("fromUserId") int fromUserId, @Param("toUserId") int toUserId);
}
