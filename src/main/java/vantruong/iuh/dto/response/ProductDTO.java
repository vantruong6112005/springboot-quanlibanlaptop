package vantruong.iuh.dto.response;

import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String cpu;
    private String ram;
    private String storage;
    private String screenSize;
    private String thumbnail;
    private Long brandId;
    private String brandName;
    private Long categoryId;
    private String categoryName;
}
