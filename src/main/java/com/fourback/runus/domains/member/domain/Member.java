package com.fourback.runus.domains.member.domain;

import java.time.LocalDate;

import com.fourback.runus.domains.member.dto.requeset.UpdateMemberRequest;
import com.fourback.runus.domains.member.enumerate.MemberRole;
import com.fourback.runus.global.audit.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.fourback.runus.member.repository
 * fileName       : Member
 * author         : Yeong-Huns
 * date           : 2024-07-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-22        Yeong-Huns       최초 생성
 */
@Data
@Entity
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
    

    @Builder
    public Member(String email, String nickName, String password, LocalDate birth, int height, int weight) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.birth = birth;
        this.height = height;
        this.weight = weight;
        this.role = MemberRole.USER;
    }

    public Member updateMemberInfo(UpdateMemberRequest request) {
        this.nickName = request.nickName();
        this.birth = request.birth();
        this.height = request.height();
        this.weight = request.weight();
        return this;
    }

    public Member updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
        return this;
    }

    public Member changeRoleToAdmin() {
        this.role = MemberRole.ADMIN;
        return this;
    }

}
