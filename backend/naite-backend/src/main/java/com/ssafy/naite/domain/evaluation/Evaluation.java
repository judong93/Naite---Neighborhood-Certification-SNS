package com.ssafy.naite.domain.evaluation;

import com.ssafy.naite.domain.market.Market;
import com.ssafy.naite.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evalNo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "market_no")
    private Market market;

    private int evalScore;
    private String evalComment;
    private int evalIsSeller;

    @Builder
    public Evaluation(User user, Market market, int evalScore, String evalComment, int evalIsSeller) {
        this.user = user;
        this.market = market;
        this.evalScore = evalScore;
        this.evalComment = evalComment;
        this.evalIsSeller = evalIsSeller;
    }
}
