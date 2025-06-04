package com.ourmenu.backend.domain.store.unit;

import com.ourmenu.backend.domain.store.util.AddressParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("주소 파싱 단위 테스트")
public class AddressTest {


    @Test
    void 상세주소를_시_구_조합으로_변환_할_수_있다() {
        //given
        String cityDistrict = "서울 광진구";
        String address = "서울 광진구 능동로16길 57 (우)05021";

        //when
        String simpleAddress = AddressParser.parseAddressToCityDistrict(address);

        //then
        Assertions.assertThat(simpleAddress).isEqualTo(cityDistrict);
    }

    @Test
    void 옳지못한_주소에_대해서_예외를_발생시키지_않는다() {
        //given
        String address = "서울";

        //when
        String simpleAddress = AddressParser.parseAddressToCityDistrict(address);

        //then
        Assertions.assertThat(simpleAddress).isEqualTo(address);
    }

}
