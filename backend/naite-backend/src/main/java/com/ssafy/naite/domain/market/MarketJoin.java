package com.ssafy.naite.domain.market;

import com.ssafy.naite.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class MarketJoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int marketJoinNo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "market_no")
    private Market market;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer_no")
    private User buyer;

    @Builder
    public MarketJoin(Market market, User buyer)  {
        this.market = market;
        this.buyer = buyer;
    }
}
