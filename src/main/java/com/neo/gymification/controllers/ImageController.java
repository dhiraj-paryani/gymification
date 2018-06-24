package com.neo.gymification.controllers;

import com.neo.gymification.models.UserImage;
import com.neo.gymification.services.UserImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
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
    File imageFile =  ResourceUtils.getFile("classpath:images/"+ partName);
    return userImageService.read(imageFile);
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
    return metadata;
  }

  @RequestMapping(
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public UserImage createUserImage(@RequestBody UserImage userImage) {
    System.out.println("Got a request" + userImage.getBase());
    UserImage createImage =  userImageService.createUserImage(userImage);
    System.out.println("User Image " + userImage.getHead());
    return createImage;
  }

}
