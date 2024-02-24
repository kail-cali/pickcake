package co.pickcake.common.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    @DisplayName("데이터검증[success]: address 를 스트링으로 출력시 제대로 되는지 테스트")
    public void toStringAddress() {
        // given
        Address address = Address.createAddress("서울", "연세로", "31222");
        // then
        Assertions.assertThat(address.toString()).isEqualTo("서울 연세로 31222");
    }

}