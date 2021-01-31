package com.ssafy.naite.domain.like;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class LikePK implements Serializable {
    private int userNo;
    private int boardNo;
}
