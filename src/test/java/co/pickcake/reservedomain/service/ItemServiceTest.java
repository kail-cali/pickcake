package co.pickcake.reservedomain.service;

import co.pickcake.reservedomain.depreciated.ItemService;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.entity.item.Item;
import co.pickcake.reservedomain.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired ItemRepository itemRepository;


    @Test
    @DisplayName("상품 단건 등록")
    @Transactional
    public void addItem() throws  Exception {

        Item item = new Cake();
        item.setName("red velvet cake");

        //when
        Long saveId = itemService.save(item);

        //then
        Assertions.assertThat(item).isEqualTo(itemRepository.findById(saveId));

    }

//    @Test
//    @DisplayName("상품 등록 시 동일 품목 예외처리 ")
//    @Transactional
//    public void addItemExceptionCase() throws Exception {
//
//    }
//
//
//    @Test
//    @DisplayName("상품 조회")
//    public void findItems() {
//
//
//    }





}