package spring.boot.optic.okulist.service.glasses;

import static spring.boot.optic.okulist.service.liquid.LiquidServiceImpl.getStrings;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import spring.boot.optic.okulist.dto.glasses.GlassesRequestDto;
import spring.boot.optic.okulist.dto.glasses.GlassesResponseDto;
import spring.boot.optic.okulist.dto.glasses.GlassesSearchParameter;
import spring.boot.optic.okulist.exception.EntityNotFoundException;
import spring.boot.optic.okulist.mapper.GlassesMapper;
import spring.boot.optic.okulist.model.Glasses;
import spring.boot.optic.okulist.repository.GlassesRepository;
import spring.boot.optic.okulist.specification.glasses.builders.GlassesSpecificationBuilder;

@Service
@RequiredArgsConstructor
public class GlassesServiceImpl implements GlassesService {
    private final GlassesRepository glassesRepository;
    private final GlassesMapper glassesMapper;
    private final GlassesSpecificationBuilder glassesSpecificationBuilder;

    @Override
    public List<GlassesResponseDto> findAll() {
        return glassesRepository.findAll()
                .stream()
                .map(glassesMapper::toDto)
                .toList();
    }

    @Override
    public GlassesResponseDto getById(Long id) {
        Glasses glasses = glassesRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't found Glasses with ID: " + id)
        );
        return getGlassesResponseDto(glasses);
    }

    @Override
    public GlassesResponseDto save(GlassesRequestDto glassesRequestDto) {
        Glasses glasses = glassesMapper.toModel(glassesRequestDto);
        return glassesMapper.toDto(glassesRepository.save(glasses));
    }

    @Override
    public GlassesResponseDto findByIdentifier(String identifier) {
        Glasses glasses = glassesRepository.findByIdentifier(identifier).orElseThrow(
                () -> new EntityNotFoundException("Can't found Glasses with ID: " + identifier)
        );
        return getGlassesResponseDto(glasses);
    }

    @NotNull
    private GlassesResponseDto getGlassesResponseDto(Glasses glasses) {
        GlassesResponseDto result = glassesMapper.toDto(glasses);
        List<GlassesResponseDto.Variation> variations = glassesRepository
                .findAllByModelAndManufacturer(glasses.getModel(),
                        glasses.getManufacturer())
                .stream()
                .filter(variation -> ! variation.getId().equals(glasses.getId()))
                .map(glassesMapper::mapGlassesVariationToDto)
                .toList();
        result.setVariations(variations);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        Glasses glasses = glassesRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't found glasses with ID :" + id)
        );
        glasses.setDeleted(true);
        glassesRepository.save(glasses);
    }

    @Override
    public List<GlassesResponseDto> searchGlassesByParameters(
            GlassesSearchParameter searchParameters) {
        Specification<Glasses> glassSpecification = glassesSpecificationBuilder
                .build(searchParameters);
        return glassesRepository.findAll(glassSpecification)
                .stream()
                .map(glassesMapper::toDto)
                .toList();
    }

    @Override
    public GlassesResponseDto update(Long id, GlassesRequestDto glassesRequestDto) {
        Glasses existingGlasses = glassesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find Glasses with ID: " + id));
        BeanUtils.copyProperties(glassesRequestDto, existingGlasses,
                getNullPropertyNames(glassesRequestDto));
        Glasses updatedGlasses = glassesRepository.save(existingGlasses);
        return glassesMapper.toDto(updatedGlasses);
    }

    private String[] getNullPropertyNames(Object source) {
        return getStrings(source);
    }
}
