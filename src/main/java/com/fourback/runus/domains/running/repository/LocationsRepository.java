package com.fourback.runus.domains.running.repository;

import com.fourback.runus.domains.running.domain.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * packageName    : com.fourback.runus.domains.running.repository
 * fileName       : LocationsRepository
 * author         : 강희원
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        강희원            최초 생성
 * 2024-07-26        김은정            명칭 수정
 */
@Repository
public interface LocationsRepository extends JpaRepository<Locations, Long> {

}

