package com.CodeSquad.IssueTracker.user;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Set<User> findAllById(Iterable<String> userIds);

    @Query("SELECT user_id FROM users")
    public List<String> getAllUserIds();
}
