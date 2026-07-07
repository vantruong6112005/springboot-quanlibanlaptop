package vantruong.iuh.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BrandResponse {
    private Long id;
    private String name;
    private String description;
}
