# Pick Cake App

-----
###  케이크 및 이벤트 상품 큐레이션 서비스

> 크리스마스, 발렌타인.. 특별한 날, 특별한 케이크로 기념하고 싶은 사람들을 위한 
> 호텔 케이크와 각종 이벤트 그리고 팝업 상품을 소개하는 앱 서비스


| 기술 스택             | 기간| 역할|            |
|-------------------|--|---|------------|
| java, spring boot | 2024.01.01~2024.03.15| 백엔드 개발| @hail-cali |


---------
[개발 내역]
1. 조회 서비스 관련 api
- 상품 조회, 필터 별 조회, 상품 상세 조회 api 개발
- 예외처리 핸들러, 에러 코드 등 추가 반영

2. 이미지 서버 api

`이미지 로딩에 대해 api 분리 개발 (이미지 카피와 메타데이터 관련 로직 분리)`
- 이미지에 대한 메타데이터는 상품이 업로드 될 때 함께 dml 을 날리도록 설계
- file-system 에 이미지가 카피하는 api 로 분리 설계

3. 테스트 유틸 클래스 모듈
- 테스트의 유닛 단위 정합성은 보장
- 반복되는 테스트에 대한 코드 재사용성은 높일 수 있도록 반영

----
[추후 개선 사항]
- 프론트 관련 코드 추가 반영 예정
- 예약 서비스 관련 로직은 디팬던시를 가지는 다른 로직과 함께 반영 예정
- 추천 시스템 관련 로직은 테스트 강화 및 안정화 후 반영 예정



