package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.SocialMediaRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.SocialMediaResponse;
import peaksoft.services.SocialMediaService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/socialMedia")
public class SocialMediaAPI {
    private final SocialMediaService socialMediaService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid SocialMediaRequest socialMediaRequest) {
        return socialMediaService.save(socialMediaRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{socialMediaId}")
    public SocialMediaResponse findById(@PathVariable Long socialMediaId) {
        return socialMediaService.findById(socialMediaId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{userId}")
    public SocialMediaResponse findSocialMediaByUserId(@PathVariable Long userId) {
        return socialMediaService.findSocialMediaByUserId(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{socialMediaId}")
    public SimpleResponse update(@PathVariable Long socialMediaId, @RequestBody SocialMediaRequest socialMediaRequest) {
        return socialMediaService.update(socialMediaId, socialMediaRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{socialMediaId}")
    public SimpleResponse delete(@PathVariable Long socialMediaId) {
        return socialMediaService.delete(socialMediaId);
    }


}