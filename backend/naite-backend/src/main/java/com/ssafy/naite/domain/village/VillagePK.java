package com.ssafy.naite.domain.village;

import com.ssafy.naite.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class VillagePK implements Serializable {
    private int userNo;

    public VillagePK(int userNo) {
        this.userNo = userNo;
    }
}
