package co.pickcake.reservation.searchcake.service;


import co.pickcake.aop.util.exception.NoDataException;
import co.pickcake.reservation.domain.item.Cake;
import co.pickcake.reservation.domain.item.EventCakeCategory;
import co.pickcake.reservation.searchcake.cache.SearchCakeRedisService;
import co.pickcake.reservation.searchcake.dto.CakeCategorySearch;
import co.pickcake.reservation.searchcake.dto.CakeDetailSearch;
import co.pickcake.reservation.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservation.searchcake.repository.CakeRepository;
import co.pickcake.reservation.searchcake.repository.CakeSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private final CakeRepository cakeRepository;
    private final CakeSearchRepository cakeSearchRepository;
    private final SearchCakeRedisService searchCakeRedisService;


    private List<CakeSimpleSearch> redisPaging(List<CakeSimpleSearch> list, int offset, int limit) {
        int end = Math.min(offset + limit, list.size());
        return list.subList(offset, end);
    }

    public CakeSimpleSearch findById(Long id) {
        Optional<Cake> byId = cakeRepository.findById(id);
        return byId.map(CakeSimpleSearch::new).orElseThrow(NoDataException::new);
    }

    public CakeDetailSearch findBySingleDetail(Long id) {
        Optional<Cake> byId = Optional.ofNullable(cakeRepository.findByIdDetails(id));
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

        PageRequest request = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "brand"));
        Page<Cake> pageResult = cakeRepository.findAllByPaging(request);
        List<Cake> result = pageResult.getContent();
        List<CakeSimpleSearch> collect = result.stream()
                .map(CakeSimpleSearch::new)
                .collect(Collectors.toList());

        collect.forEach(searchCakeRedisService:: save);
        return collect;
    }
    public List<CakeSimpleSearch> findByBrand(int offset, int limit, String brand) {
        PageRequest request = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "brand"));
        Page<Cake> pageResut = cakeRepository.findByBrand(brand, request);
        List<Cake> content = pageResut.getContent();

        List<CakeSimpleSearch> collect = content
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
//   /* JPA 로 변경하면서 이런 동적 쿼리 다시 고민 필요 */
//    public List<CakeSimpleSearch> findByNameOnLike(int offset, int limit, String cakeName) {
//        List<Cake> byName = cakeUserRepository.findByNameOnLike(offset, limit, cakeName);
//
//        return byName.stream()
//                .map(CakeSimpleSearch::new)
//                .collect(Collectors.toList());
//    }
}
