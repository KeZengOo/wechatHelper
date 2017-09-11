package com.nuoxin.virtual.rep.virtualrepapi.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

  public static String encode(String rawPassword) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(rawPassword);
  }
  
  public static boolean checkPassword(String password, String encodedPassword){
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.matches(password, encodedPassword);
  }
  
  public static void main(String[] args) {
    System.out.println(PasswordEncoder.checkPassword("111111","$2a$10$zN76Ssij.qcy58KE/n3Ui.fC6H1igNflJAL9O9L0/mrUilBi0L0cq"));
    
    System.out.println(PasswordEncoder.encode("YGF0"));
  }
  
}
