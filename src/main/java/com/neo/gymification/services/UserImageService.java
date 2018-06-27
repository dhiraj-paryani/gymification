package com.neo.gymification.services;

import com.neo.gymification.models.UserImage;
import com.neo.gymification.repositories.UserImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

@Service
public class UserImageService {

  Map<String, String> baseMap = new TreeMap<String, String>();
  Map<String, String> headMap = new HashMap<String, String>();

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
    File baseImage =  ResourceUtils.getFile("classpath:images/" + getBaseMap().get(userImage.getBase()));
    if(userImage.getHead() != null && !userImage.getHead().isEmpty()) {
      File headImage =  ResourceUtils.getFile("classpath:images/" + getHeadMap().get(userImage.getHead()));
      return mergeImage(baseImage, headImage);
    }
    return read(baseImage);
  }

  private byte[] mergeImage(File file1, File file2) throws IOException {
    BufferedImage image = ImageIO.read(file1);
    BufferedImage overlay = ImageIO.read(file2);
    int w = Math.max(image.getWidth(), overlay.getWidth());
    int h = Math.max(image.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(image, 0, 0, null);
    g.drawImage(overlay, 0, 0, null);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(combined, "PNG", baos);
    return baos.toByteArray();
  }
}
