package com.wbd.Instagram.service;

import com.wbd.Instagram.model.Image;
import com.wbd.Instagram.repository.ImageRepository;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    public Image saveImageFile(MultipartFile file)throws IOException {
        if(file.getSize() > 100*1024*1024){
            throw new RuntimeException("File too large");
        }
        String imageFormat= FilenameUtils.getExtension(file.getOriginalFilename());
        assert imageFormat != null;
        if(!Arrays.asList("png", "jpg", "bmp", "jpeg").contains(imageFormat.toLowerCase())){
            throw new RuntimeException("Invalid File Format");
        }
//        //Converting img to jpg
//        BufferedImage originalImage= ImageIO.read(file.getInputStream());
//        BufferedImage resizedImage= Scalr.resize(originalImage, 600);
//
//        //Storing image as jpg
//        String fileName= UUID.randomUUID().toString() + ".jpg";
//        File outputFile= new File("images/"+fileName);
//        ImageIO.write(resizedImage, "jpg", outputFile);
//        return "/images/" + fileName;
        Image imageFile= new Image();
        imageFile.setImageName(file.getOriginalFilename());
        imageFile.setImageType(file.getContentType());
        imageFile.setImageData(file.getBytes());
        return imageRepository.save(imageFile);
    }
}
