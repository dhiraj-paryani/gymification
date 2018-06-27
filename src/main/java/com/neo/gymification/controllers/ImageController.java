package com.neo.gymification.controllers;

import com.neo.gymification.models.UserImage;
import com.neo.gymification.services.UserImageService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/image")
public class ImageController {

  private UserImageService userImageService;

  @Autowired
  public ImageController(UserImageService userImageService) {
    this.userImageService = userImageService;
  }

  @RequestMapping(
      method = RequestMethod.GET,
      produces = MediaType.IMAGE_PNG_VALUE
  )
  @ResponseBody
  public byte[] getImage(@RequestParam("userName") String userName) throws IOException {
    UserImage userImage = userImageService.getUserByUserName(userName);

    return userImageService.imageFromUserImage(userImage);
  }

  @RequestMapping(
      path = "parts",
      method = RequestMethod.GET,
      produces = MediaType.IMAGE_PNG_VALUE
  )
  @ResponseBody
  public byte[] getImagePart(@RequestParam("partName") String partName) throws IOException {
    ClassPathResource classPathResource = new ClassPathResource("images/" + partName);
    System.out.println("Partname " + partName);
    InputStream is = classPathResource.getInputStream();
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    int nRead;
    byte[] data = new byte[16384];

    while ((nRead = is.read(data, 0, data.length)) != -1) {
      buffer.write(data, 0, nRead);
    }

    buffer.flush();
    return buffer.toByteArray();
  }

  @RequestMapping(
      path = "metadata",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public Map<String, Map<String, String>> getImagePartMetadata() throws IOException {
    Map<String, Map<String, String>> metadata = new HashMap<String, Map<String, String>>();
    metadata.put("base", userImageService.getBaseMap());
    metadata.put("head", userImageService.getHeadMap());
    metadata.put("mask", userImageService.getMaskMap());
    metadata.put("glass", userImageService.getGlassMap());
    return metadata;
  }

  @RequestMapping(
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public UserImage createUserImage(@RequestBody UserImage userImage) {
    UserImage existingImage = null;
    try {
       existingImage = userImageService.getUserByUserName(userImage.getUserName());

    } catch ( Exception e) {

    }

    if(existingImage != null) {
      existingImage.setBase(userImage.getBase() == null ? existingImage.getBase(): userImage.getBase());
      existingImage.setHead(userImage.getHead() == null ? existingImage.getHead(): userImage.getHead());
      existingImage.setGlass(userImage.getGlass() == null ? existingImage.getGlass(): userImage.getGlass());
      existingImage.setMask(userImage.getMask() == null ? existingImage.getMask(): userImage.getMask());

      System.out.println("EH" + existingImage.getHead());
      System.out.println("UIH" + userImage.getHead());
    } else {
      existingImage = userImage;
    }
    UserImage createImage =  userImageService.createUserImage(existingImage);
    return createImage;
  }



}
