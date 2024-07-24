package com.fourback.runus.domains.member.repository;

import com.fourback.runus.domains.member.domain.Member;
import com.fourback.runus.domains.member.dto.requeset.SendDeleteMemberRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.fourback.runus.member.repository
 * fileName       : MemberRepository
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.deletedAt IS NULL")
    List<Member> findAllActiveMembers();

    @Query("SELECT m FROM Member m WHERE m.userId = :id AND m.deletedAt IS NULL")
    Optional<Member> findActiveMemberById(long id);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.deletedAt = :#{#message.deletedAt()} WHERE m.userId = :#{#message.userId()}")
    void deleteMember(@Param("message") SendDeleteMemberRequest message);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.deletedAt = :deletionTime")
    void deleteAllMembers(@Param("deletionTime") LocalDateTime deletionTime);
}
