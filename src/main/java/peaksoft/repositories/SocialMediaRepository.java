package peaksoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.entities.SocialMedia;

import java.util.Optional;

public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {
    @Query("select s from SocialMedia s where  s.user.id = :userId")
    Optional<SocialMedia> findSocialMediaByUserId(Long userId);
}