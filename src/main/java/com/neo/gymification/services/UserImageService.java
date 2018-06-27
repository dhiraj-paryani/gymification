package com.neo.gymification.services;

import com.neo.gymification.models.UserImage;
import com.neo.gymification.repositories.UserImageRepository;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

@Service
public class UserImageService {

  Map<String, String> baseMap = new TreeMap<String, String>();
  Map<String, String> maskMap = new TreeMap<String, String>();
  Map<String, String> glassMap = new TreeMap<String, String>();
  Map<String, String> headMap = new TreeMap<>();

  private UserImageRepository userImageRepository;

  @Autowired
  public  UserImageService(UserImageRepository userImageRepository) {
    this.userImageRepository = userImageRepository;
    baseMap.put("male_basic", "char_male_basic.png");
    baseMap.put("male_doctor", "char_male_doctor.png");
    baseMap.put("male_emo", "char_male_emo.png");
    baseMap.put("male_programmer", "char_male_programmer.png");

    baseMap.put("female_basic", "char_female_basic.png");
    baseMap.put("female_doctor", "char_female_doctor.png");
    baseMap.put("female_emo", "char_female_emo.png");
    baseMap.put("female_programmer", "char_female_programmer.png");

    maskMap.put("skull", "addon_mask_alien.png");
    maskMap.put("demon", "addon_mask_demon.png");
    maskMap.put("alien", "addon_mask_alien1.png");
    maskMap.put("gamer", "addon_mask_gamer.png");

    glassMap.put("glass", "addon_glass_glass.png");

    headMap.put("hat", "addon_head_hat.png");
    headMap.put("crown", "addon_head_crown.png");
  }

  public UserImage createUserImage(UserImage userImage) {
    return userImageRepository.save(userImage);
  }

  public UserImage getUserByUserName(String userName) {
    return userImageRepository.findByUserName(userName).get();
  }

  public Map<String, String> getBaseMap() {
    return baseMap;
  }

  public Map<String, String> getHeadMap() {
    return headMap;
  }


  public byte[] read(File file) throws IOException {

    byte[] buffer = new byte[(int) file.length()];
    InputStream ios = null;
    try {
      ios = new FileInputStream(file);
      if (ios.read(buffer) == -1) {
        throw new IOException(
            "EOF reached while trying to read the whole file");
      }
    } finally {
      try {
        if (ios != null)
          ios.close();
      } catch (IOException e) {
      }
    }
    return buffer;
  }

  public byte[] imageFromUserImage(UserImage userImage) throws IOException {
    ArrayList<InputStream> layers = new ArrayList<InputStream>();
    if(userImage.getBase() == null) {
      return new byte[1];
    }
    layers.add(imageUrlToInputStream(baseMap.get(userImage.getBase())));
    if(userImage.getMask() != null && !userImage.getMask().isEmpty()) {
      layers.add(imageUrlToInputStream(maskMap.get(userImage.getMask())));
    }
    if(userImage.getGlass() != null && !userImage.getGlass().isEmpty()) {
      layers.add(imageUrlToInputStream(glassMap.get(userImage.getGlass())));
    }
    if(userImage.getHead() != null && !userImage.getHead().isEmpty()) {
      layers.add(imageUrlToInputStream(headMap.get(userImage.getHead())));
    }
    return mergeImage(layers);
  }

  private InputStream imageUrlToInputStream(String imageUrl) throws IOException {
    ClassPathResource classPathResource = new ClassPathResource("images/" + imageUrl);
    return classPathResource.getInputStream();
  }

  private byte[] mergeImage(ArrayList<InputStream> images) throws IOException {
    BufferedImage baseImage = ImageIO.read(images.get(0));
    BufferedImage combined = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(baseImage, 0, 0, null);
    for(int i = 1; i < images.size(); i++) {
      BufferedImage overlay = ImageIO.read(images.get(i));
      g.drawImage(overlay, 0, 0, null);
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(combined, "PNG", baos);
    return baos.toByteArray();
  }

  public Map<String, String> getMaskMap() {
    return maskMap;
  }

  public Map<String, String> getGlassMap() {
    return glassMap;
  }
}
