package org.example.handanddomain.domain.member.domain;

import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final String name;
    private String profileImageUrl;

    public static final String DEFAULT_PROFILE_IMAGE_URL = "image://default-profile-image";

    public Member(Long id, String name, String profileImageUrl) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다");
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

}
