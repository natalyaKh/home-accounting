package smilyk.homeacc.service.subcategory;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.CategorySubcategoryConstant;
import smilyk.homeacc.dto.SubcategoryDto;
import smilyk.homeacc.model.Subcategory;
import smilyk.homeacc.repo.SubcategoryRepository;
import smilyk.homeacc.service.user.UserServiceImpl;
import smilyk.homeacc.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    SubcategoryRepository subcategoryRepository;
    @Autowired
    Utils utils;

    @Override
    public Subcategory save(SubcategoryDto subcategoryDto) {
        return getSavedSubcategory(subcategoryDto);
    }

    private Subcategory getSavedSubcategory(SubcategoryDto subcategoryDto) {
        Subcategory subcategory = modelMapper.map(subcategoryDto, Subcategory.class);
        subcategory.setSubcategoryUuid(utils.generateUserUuid().toString());
        subcategory.setDeleted(false);
        Subcategory savedSubcategory = subcategoryRepository.save(subcategory);
        LOGGER.info(CategorySubcategoryConstant.SUBCATEGORY_WITH_NAME + subcategoryDto.getSubcategoryName() +
            CategorySubcategoryConstant.CREATED);
        return savedSubcategory;
    }

    @Override
    public SubcategoryDto createSubcategory(SubcategoryDto subcategoryDto) {
        //        TODO test
        Subcategory savedSubcategory = getSavedSubcategory(subcategoryDto);
        return modelMapper.map(savedSubcategory, SubcategoryDto.class);
    }

    @Override
    public List<SubcategoryDto> getAllSubcategoryByUserUuid(String userUuid) {
//        TODO test
        Optional<List<Subcategory>> subcategoryOptional = subcategoryRepository.findByUserUuid(userUuid);
        return subcategoryOptional.map(subcategories -> subcategories.stream().map(this::subcategoryToSubcategoryDto)
            .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public SubcategoryDto getSubcategoryBySubcategoryUuid(String subcategoryUuid) {
        //        TODO test
        Optional<Subcategory> optionalSubcategory = subcategoryRepository.findBySubcategoryUuid(subcategoryUuid);
        if (optionalSubcategory.isEmpty()) {
            return SubcategoryDto.builder().build();
        }
        return modelMapper.map(optionalSubcategory.get(), SubcategoryDto.class);
    }

    @Override
    public SubcategoryDto deleteSubcategoryBySubcategoryUuid(String subcategoryUuid) {
        //        TODO test
        Optional<Subcategory> optionalSubcategory = subcategoryRepository.findBySubcategoryUuid(subcategoryUuid);
//        dont check optional because checked it in validation
        subcategoryRepository.delete(optionalSubcategory.get());
        LOGGER.info(CategorySubcategoryConstant.SUBCATEGORY_WITH_UUID + subcategoryUuid
            + CategorySubcategoryConstant.DELETED);

        return modelMapper.map(optionalSubcategory.get(), SubcategoryDto.class);
    }

    @Override
    public SubcategoryDto updateSubcategory(SubcategoryDto subcategoryDto) {
        //        TODO test
        Subcategory subcategory = subcategoryRepository.findBySubcategoryNameAndUserUuid(
            subcategoryDto.getSubcategoryName(), subcategoryDto.getUserUuid()
        ).get();
        subcategory.setSubcategoryName(subcategoryDto.getSubcategoryName());
        subcategory.setDescription(subcategoryDto.getDescription());
        Subcategory savedSubcategory = subcategoryRepository.save(subcategory);
        LOGGER.info(CategorySubcategoryConstant.SUBCATEGORY_WITH_UUID + savedSubcategory.getSubcategoryUuid()
            + CategorySubcategoryConstant.UPDATED);
        return modelMapper.map(savedSubcategory, SubcategoryDto.class);
    }

    private SubcategoryDto subcategoryToSubcategoryDto(Subcategory subcategory) {
        return modelMapper.map(subcategory, SubcategoryDto.class);
    }
}
