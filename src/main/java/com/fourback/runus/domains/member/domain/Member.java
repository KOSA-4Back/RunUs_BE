package com.fourback.runus.domains.member.domain;

import com.fourback.runus.domains.member.dto.requeset.UpdateMemberRequest;
import com.fourback.runus.domains.member.enumerate.MemberRole;
import com.fourback.runus.domains.running.domain.TodayGoal;
import com.fourback.runus.global.audit.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.fourback.runus.member.domain
 * fileName       : Member
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 * 2024-07-24        김은정            TodayGoal 엔티티 연관관계 설정/Member 설정 시 profileUrl 추가
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String email;
    private String nickName;
    private String password;
    private LocalDate birth;
    private String profileUrl;
    private int height;
    private int weight;

    @Enumerated(EnumType.STRING)
    private MemberRole role;


    // 일별 목표
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // 외래 키를 설정
    private List<TodayGoal> todayGoalList;


    @Builder
    public Member(String email, String nickName, String password,LocalDate birth, String profileUrl,
        int height, int weight) {

        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.birth = birth;
        this.profileUrl = profileUrl;
        this.height = height;
        this.weight = weight;
        this.role = MemberRole.USER;
    }

    public void updateMemberInfo(UpdateMemberRequest request, String profileUrl) {
        this.nickName = request.nickName();
        this.birth = request.birth();
        this.profileUrl = profileUrl;
        this.height = request.height();
        this.weight = request.weight();
    }


    public Member changeRoleToAdmin() {
        this.role = MemberRole.ADMIN;
        return this;
    }

}
