package com.ssafy.naite.domain.evaluation;

import com.ssafy.naite.domain.market.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    Evaluation findByMarketAndEvalIsSeller(Market market, int evalIsSeller);
}
