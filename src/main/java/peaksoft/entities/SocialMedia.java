package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "social_medias")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "soc_media_seq")
    @SequenceGenerator(name = "soc_media_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String logo;
    @Column(name = "published_date")
    private ZonedDateTime publishedDate;
    @ManyToOne
    private User user;
    @PrePersist
    public void prePersist() {
        this.publishedDate = ZonedDateTime.now();
    }
}
