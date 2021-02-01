package com.ssafy.naite.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<BoardLike, LikePK> {
}
