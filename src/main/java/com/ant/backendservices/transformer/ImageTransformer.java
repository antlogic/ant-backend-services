package com.ant.backendservices.transformer;

import com.ant.backendservices.model.Image;
import com.ant.backendservices.payload.response.ImageType;
import com.ant.backendservices.payload.response.RetrieveImagesResponse;
import com.ant.backendservices.utils.StorageBucketUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageTransformer {

    @Mappings({
            @Mapping(target = "imageId", source = "id"),
            @Mapping(target = "url", source = "fileName")
    })
    ImageType imageEntityToImageType(Image image);

    List<ImageType> imageEntityListToImageTypeList(List<Image> images);

    default RetrieveImagesResponse imageEntityListToRetrieveImagesResponse(List<Image> images) {

        RetrieveImagesResponse response = new RetrieveImagesResponse();
        response.setImages(imageEntityListToImageTypeList(images));
        return response;
    }
}
