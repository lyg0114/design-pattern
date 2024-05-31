package com.designpatternstudy.creational.singleton;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.singleton
 * @since : 22.05.24
 */
/*
 [싱글톤 패턴]
 - 하나의 클래스 타입에 대해 오직 하나의 객체만 생성되도록 보장
 - 동시성 이슈를 고려해서 사용해야할 패턴
 */
public class SingletonPattern {

  public static void main(String[] args) {
    King king1 = King.getInstance();
    King king2 = King.getInstance();
    System.out.println("king1.getClass() = " + king1.getClass());
    System.out.println("king2.getClass() = " + king2.getClass());
    System.out.println("king1 == king2 = " + (king1 == king2));
  }

  static class King {

    private static King self = null;

    private King() {
    }

    public synchronized static King getInstance() {
      if (self == null) {
        self = new King();
      }
      return self;
    }

    public void say() {
      System.out.println("I am only one");
    }
  }
}
