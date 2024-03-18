package peaksoft.dto.responses;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record SocialMediaResponse(
        String name,
        String logo,
        ZonedDateTime publishedDate
) {
}
