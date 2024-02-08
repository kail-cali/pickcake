package co.pickcake.reservedomain.depreciated;

import co.pickcake.reservedomain.entity.item.Item;
import co.pickcake.reservedomain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    @Transactional
    public Long save(Item item) {
        itemRepository.save(item);
        return  item.getId();
    }
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price ,int stock ) {
        Item findItem = itemRepository.findById(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
//        findItem.setStockQuantity(stock);
    }



}
