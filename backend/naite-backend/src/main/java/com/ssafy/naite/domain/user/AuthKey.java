package com.ssafy.naite.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Entity
public class AuthKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authNo;

    private String userEmail;

    @Column(nullable = false, length = 200)
    private String authKey;

    @NotNull
    private Integer authType;

    @Builder
    public AuthKey(String userEmail, String authKey, @NotNull Integer authType) {
        this.userEmail = userEmail;
        this.authKey = authKey;
        this.authType = authType;
    }

    public void updateKey(String newKey) {
        this.authKey = newKey;
    }
}
