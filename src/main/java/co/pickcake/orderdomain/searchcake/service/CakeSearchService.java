package co.pickcake.orderdomain.searchcake.service;


import co.pickcake.orderdomain.entity.item.Cake;
import co.pickcake.orderdomain.entity.item.EventCakeCategory;
import co.pickcake.orderdomain.searchcake.dto.CakeCategorySearch;
import co.pickcake.orderdomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.orderdomain.searchcake.repository.CakeSearchRepository;
import co.pickcake.orderdomain.searchcake.repository.CakeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CakeSearchService {

    private final CakeUserRepository cakeUserRepository;
    private final CakeSearchRepository cakeSearchRepository;

    public List<CakeSimpleSearch> findAll(int offset, int limit) {
        List<Cake> cakeList = cakeUserRepository.findAll(offset, limit);

        List<CakeSimpleSearch> collect = cakeList.stream()
                .map(CakeSimpleSearch::new)
                .collect(Collectors.toList());
        return collect;
    }


    public List<CakeSimpleSearch> findByBrand(int offset, int limit, String brand) {
        List<Cake> cakes = cakeUserRepository.findByBrand(offset, limit, brand);

        List<CakeSimpleSearch> collect = cakes.stream()
                .map(CakeSimpleSearch::new)
                .collect(Collectors.toList());

        return collect;
    }



    public List<CakeCategorySearch> findBySingleCategory(int offset, int limit, String categoryName) {
        List<EventCakeCategory> bySingleCategory = cakeSearchRepository.findBySingleCategory(offset, limit, categoryName);

        return bySingleCategory.stream()
                .map(CakeCategorySearch::new)
                .collect(Collectors.toList());
    }

    /* TODO 더 정확한 이름 검색 기능 개선, 일반 검색 메서드 refactor */
    public List<CakeSimpleSearch> findByNameOnLike(int offset, int limit, String cakeName) {
        List<Cake> byName = cakeUserRepository.findByNameOnLike(offset, limit, cakeName);

        return byName.stream()
                .map(CakeSimpleSearch::new)
                .collect(Collectors.toList());
    }



}
