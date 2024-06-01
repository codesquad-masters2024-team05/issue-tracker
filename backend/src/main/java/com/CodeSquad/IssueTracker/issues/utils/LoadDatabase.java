/*
package com.CodeSquad.IssueTracker.issues.utils;

import com.CodeSquad.IssueTracker.issues.Issue;
import com.CodeSquad.IssueTracker.issues.IssueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(IssueRepository repository) {
        return args -> {
            // 더미 데이터 추가
            for (int i = 1; i <= 10; i++) {
                repository.save(new Issue("Issue " + i, "Author " + i));
            }
        };
    }
}
*/
