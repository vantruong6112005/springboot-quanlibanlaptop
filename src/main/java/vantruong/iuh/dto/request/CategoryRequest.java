package vantruong.iuh.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryRequest {
    @NotBlank(message = "Category name cannot be blank")
    private String name;
    private String description;
}
