package com.ant.backendservices.service;

import com.ant.backendservices.exception.AppException;
import com.ant.backendservices.model.Company;
import com.ant.backendservices.model.Image;
import com.ant.backendservices.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CompanyService companyService;

    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public List<Image> getImagesByCompanyId(Long companyId) {
        return imageRepository.findByCompanyId(companyId).orElse(null);
    }

    public Image uploadImage(MultipartFile file, Long companyId) {
        if (file == null) {
            return null;
        }

        Company company = companyService.getCompanyById(companyId);

        if (company == null) {
            throw new AppException("Invalid companyId: " + companyId);
        }

        String fileName = fileStorageService.storeFile(file);

        Image image = new Image();

        image.setCompany(company);
        image.setFileName(fileName);
        image.setSize(file.getSize());
        try {
            image.setWidth(Integer.toUnsignedLong(getWidth(file.getInputStream())));
            image.setHeight(Integer.toUnsignedLong(getHeight(file.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageRepository.save(image);

    }

    private Integer getWidth(InputStream inputStream) {
        try(ImageInputStream in = ImageIO.createImageInputStream(inputStream)){
            final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(in);
                    return reader.getWidth(0);
                } finally {
                    reader.dispose();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integer getHeight(InputStream inputStream) {
        try(ImageInputStream in = ImageIO.createImageInputStream(inputStream)){
            final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(in);
                    return reader.getHeight(0);
                } finally {
                    reader.dispose();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
