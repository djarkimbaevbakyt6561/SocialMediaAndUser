package peaksoft.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import peaksoft.entities.SocialMedia;

public record SocialMediaRequest(
   @NotNull(message = "Name is null") @NotBlank(message = "Name is blank")
   String name,
   String logo,
   @NotNull(message = "User id required to add social media!") @NotBlank(message = "User id required to add social media!")
   Long userId
) {
   public SocialMedia build(){
     SocialMedia socialMedia = new SocialMedia();
     socialMedia.setLogo(this.logo);
     socialMedia.setName(this.name);
     return socialMedia;
   }
}
