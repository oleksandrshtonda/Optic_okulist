package spring.boot.optic.okulist.service.glasses;

import java.util.List;
import spring.boot.optic.okulist.dto.glasses.GlassesRequestDto;
import spring.boot.optic.okulist.dto.glasses.GlassesResponseDto;
import spring.boot.optic.okulist.dto.glasses.GlassesSearchParameter;

public interface GlassesService {
    List<GlassesResponseDto> findAll();

    GlassesResponseDto getById(Long id);

    GlassesResponseDto save(GlassesRequestDto glassesRequestDto);

    void deleteById(Long id);

    GlassesResponseDto update(Long id, GlassesRequestDto glassesRequestDto);

    List<GlassesResponseDto> searchGlassesByParameters(GlassesSearchParameter searchParameters);

    GlassesResponseDto findByIdentifier(String identifier);
}
