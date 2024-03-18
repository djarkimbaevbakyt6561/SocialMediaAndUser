package peaksoft.services;

import peaksoft.dto.requests.SocialMediaRequest;
import peaksoft.dto.responses.*;

public interface SocialMediaService {
    SocialMediaResponse findById(Long socialMediaId);
    SocialMediaResponse findSocialMediaByUserId(Long userId);
    SimpleResponse save(SocialMediaRequest socialMediaRequest);
    SimpleResponse update(Long socialMediaId, SocialMediaRequest socialMediaRequest);
    SimpleResponse delete(Long socialMediaId);
}
