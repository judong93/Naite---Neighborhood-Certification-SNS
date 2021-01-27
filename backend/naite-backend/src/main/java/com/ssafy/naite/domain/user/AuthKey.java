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

//    @Column(name = "user_no")
//    private Integer userNo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_no")
    private User user;

    @Column(nullable = false, length = 200)
    private String authKey;

    @NotNull
    private Integer authType;

    @Builder
    public AuthKey(User user, String authKey, @NotNull Integer authType) {
//        this.userNo = userNo;
        this.user = user;
        this.authKey = authKey;
        this.authType = authType;
    }

    public void updateKey(String newKey) {
        this.authKey = newKey;
    }
}
