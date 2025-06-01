package org.example.handanddomain.domain.member.domain;

import lombok.Generated;
import lombok.Getter;
import org.example.handanddomain.common.exception.DomainIllegalArgumentException;

import java.util.Objects;

@Getter
public class Member {

    private final Long id;
    private final String name;
    private String profileImageUrl;

    public static final String DEFAULT_PROFILE_IMAGE_URL = "image://default-profile-image";

    public Member(Long id, String name, String profileImageUrl) {
        if (name == null || name.isEmpty()) {
            throw new DomainIllegalArgumentException("이름은 비어있을 수 없습니다");
        }

        this.id = id;
        this.name = name;
        modifyProfileImageUrl(profileImageUrl);
    }

    public void modifyProfileImageUrl(String profileImageUrl) {
        if (profileImageUrl == null || profileImageUrl.isEmpty()) {
            profileImageUrl = DEFAULT_PROFILE_IMAGE_URL;
        }

        this.profileImageUrl = profileImageUrl;
    }


    @Override
    @Generated
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;

        Member member = (Member) o;

        return id != null && id.equals(member.id);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
