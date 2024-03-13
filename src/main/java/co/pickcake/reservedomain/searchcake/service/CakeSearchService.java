package co.pickcake.reservedomain.searchcake.service;


import co.pickcake.aop.util.exception.NoDataException;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.entity.item.EventCakeCategory;
import co.pickcake.reservedomain.searchcake.cache.SearchCakeRedisService;
import co.pickcake.reservedomain.searchcake.dto.CakeCategorySearch;
import co.pickcake.reservedomain.searchcake.dto.CakeDetailSearch;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.repository.CakeSearchRepository;
import co.pickcake.reservedomain.searchcake.repository.CakeUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CakeSearchService {

    private final CakeUserRepository cakeUserRepository;
    private final CakeSearchRepository cakeSearchRepository;
    private final SearchCakeRedisService searchCakeRedisService;

    private List<CakeSimpleSearch> redisPaging(List<CakeSimpleSearch> list, int offset, int limit) {
        int end = Math.min(offset + limit, list.size());
        return list.subList(offset, end);
    }

    public CakeSimpleSearch findById(Long id) {
        Optional<Cake> byId = Optional.ofNullable(cakeUserRepository.findById(id));
        return byId.map(CakeSimpleSearch::new).orElseThrow(NoDataException::new);
    }
    public CakeDetailSearch findBySingleDetail(Long id) {
        Optional<Cake> byId = Optional.ofNullable(cakeUserRepository.findByIdDetails(id));
        return byId.map(CakeDetailSearch:: new).orElseThrow(NoDataException::new);
    }
    public List<CakeSimpleSearch> findAll(int offset, int limit) {
        // using redis
        List<CakeSimpleSearch> all = searchCakeRedisService.findAll();
        if (!all.isEmpty()) {
            log.info("[paging using redis]: search findAll ");
            return redisPaging(all, offset, limit);
        }
        log.info("[paging using Dao]: search findAll ");
        List<Cake> cakeList = cakeUserRepository.findAll(offset, limit);
        List<CakeSimpleSearch> collect = cakeList.stream()
                .map(CakeSimpleSearch::new)
                .collect(Collectors.toList());

        collect.forEach(searchCakeRedisService:: save);

        return collect;
    }
    public List<CakeSimpleSearch> findByBrand(int offset, int limit, String brand) {
        Optional<List<Cake>> cakes = Optional.ofNullable(cakeUserRepository.findByBrand(offset, limit, brand));

        List<CakeSimpleSearch> collect = cakes.orElseThrow(NoDataException::new)
                .stream()
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
    /* 추천 검색 시스템 용 api */
    public List<CakeCategorySearch> findBySingleCategorySim(int offset, int limit, String categoryName) {
        List<EventCakeCategory> bySingleCategory = cakeSearchRepository.findBySingleCategorySim(offset, limit, categoryName);

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
