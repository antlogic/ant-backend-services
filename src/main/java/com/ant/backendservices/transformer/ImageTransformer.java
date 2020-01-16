package com.ant.backendservices.transformer;

import com.ant.backendservices.model.Image;
import com.ant.backendservices.payload.response.ImageType;
import com.ant.backendservices.payload.response.RetrieveImagesResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageTransformer {

    @Mappings({
            @Mapping(target = "fileId", source = "id"),
            @Mapping(target = "url", expression = "java(com.ant.backendservices.service.FileStorageService.createBucketFileURL(image.getFileName()))")
    })
    ImageType imageEntityToImageType(Image image);

    List<ImageType> imageEntityListToImageTypeList(List<Image> images);

    default RetrieveImagesResponse imageEntityListToRetrieveImagesResponse(List<Image> images) {
        RetrieveImagesResponse response = new RetrieveImagesResponse();
        response.setImages(imageEntityListToImageTypeList(images));
        return response;
    }
}
