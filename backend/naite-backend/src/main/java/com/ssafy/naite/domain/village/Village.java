package com.ssafy.naite.domain.village;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@IdClass(VillagePK.class)
public class Village {
    @Id
    private int userNo;

    private String villageName;

    @Builder
    public Village(int userNo, String villageName) {
        this.userNo = userNo;
        this.villageName = villageName;
    }

    public void updateVillage(String villageName) {
        this.villageName = villageName;
    }
}
