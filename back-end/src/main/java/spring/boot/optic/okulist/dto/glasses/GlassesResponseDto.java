package spring.boot.optic.okulist.dto.glasses;

import java.util.Set;
import lombok.Data;
import spring.boot.optic.okulist.dto.category.CategoryResponseDto;

@Data
public class GlassesResponseDto {
    private Long id;
    private String glassesName;
    private double price;
    private String identifier;
    private String color;
    private String model;
    private String manufacturer;
    private Set<CategoryResponseDto> categories;
}
