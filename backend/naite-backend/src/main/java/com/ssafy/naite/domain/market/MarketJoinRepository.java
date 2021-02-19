package com.ssafy.naite.domain.market;

import com.ssafy.naite.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarketJoinRepository extends JpaRepository<MarketJoin, Integer> {
    List<MarketJoin> findByMarket(Market market);
    Optional<MarketJoin> findByMarketAndBuyer(Market market, User buyer);
}
