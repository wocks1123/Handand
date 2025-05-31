package org.example.handanddomain.domain.post.infra.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostJpaEntity, Long> {
}
