package com.neo.gymification.services;

import com.neo.gymification.models.BadgeName;
import com.neo.gymification.models.GUser;
import com.neo.gymification.models.UserBadge;
import com.neo.gymification.repositories.UserBadgeRepository;
import com.neo.gymification.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserBadgeServiceImpl {

  private UserRepository userRepository;
  private UserBadgeRepository userBadgeRepository;

  @Autowired
  public UserBadgeServiceImpl(UserRepository userRepository,
                              UserBadgeRepository userBadgeRepository) {
    this.userRepository = userRepository;
    this.userBadgeRepository = userBadgeRepository;

    // Create Badges here, save in the DB
    for (BadgeName badgeName: BadgeName.values()) {
      createUserBadge(badgeName);
    }
  }

  private void createUserBadge(BadgeName badgeName) {
    UserBadge userBadge = new UserBadge();
    userBadge.setId(UUID.randomUUID());
    userBadge.setBadgeName(badgeName);
    String badgeNameUrl = String.format("/badges/%s",badgeName.name());
    userBadge.setBadgeUrl(badgeNameUrl);
    userBadgeRepository.save(userBadge);
  }

  public void assignBadgeToUser(String userName, UserBadge userBadge) {
    GUser user = userRepository.findById(userName)
        .orElseThrow(() -> new RuntimeException("User is not present in the DB."));

    userBadge = userBadgeRepository.findByBadgeName(userBadge.getBadgeName())
        .orElseThrow(() -> new RuntimeException("User Badge is not present in the DB."));

    List<UserBadge> userBadges = user.getUserBadges();
    if (userBadges.contains(userBadge)) {
      throw new RuntimeException("User Already has this badge");
    }

    userBadges.add(userBadge);
    user.setUserBadges(userBadges);

    userRepository.save(user);
  }

  public List<UserBadge> getBadgesByUserName(String userName) {
    GUser user = userRepository.findById(userName)
        .orElseThrow(() -> new RuntimeException("User is not present in the DB."));
    return user.getUserBadges();
  }

  public List<UserBadge> getAllBadges() {
    List<UserBadge> userBadges = new ArrayList<>();
    userBadgeRepository.findAll().forEach(userBadges::add);
    return userBadges;
  }

  public byte[] getBadgeImage(BadgeName badgeName) throws IOException {
    File imageFile =  ResourceUtils.getFile("classpath:images/badges/"+ badgeName.getFileName());
    return UserBadgeServiceImpl.read(imageFile);
  }

  public static byte[] read(File file) throws IOException {

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
}
