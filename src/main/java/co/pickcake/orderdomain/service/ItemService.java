package co.pickcake.orderdomain.service;

import co.pickcake.orderdomain.entity.item.Item;
import co.pickcake.orderdomain.repository.ItemRepository;
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
        return itemRepository.findOne(itemId);
    }

}
