package com.CodeSquad.IssueTracker.user.gitLogin;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface GitHubUserRepository extends CrudRepository<GitHubUser, String> {

    @Modifying
    @Query("INSERT INTO githubUsers (github_id, user_id) VALUES (:githubId, :userId)")
    void insertGitHubUser(String githubId, String userId);
}
