package com.CodeSquad.IssueTracker.user.gitLogin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("githubUsers")
public class GitHubUser implements Persistable<String> {
        @Id
        String githubId;
        String userId;
        String accessToken;

        @Transient
        private boolean isNew = true;
        @Override
        public String getId() {
                return githubId;
        }
        @Override
        public boolean isNew() {
                return isNew;
        }

        public GitHubUser(String githubId, String userId, String accessToken) {
                this.githubId = githubId;
                this.userId = userId;
                this.accessToken = accessToken;
        }
}
