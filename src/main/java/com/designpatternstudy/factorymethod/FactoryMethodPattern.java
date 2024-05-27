package com.designpatternstudy.factorymethod;

import java.util.HashMap;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.factorymethod
 * @since : 27.05.24
 */
/*
 [팩토리메서드 패턴]
 - 객채 생성을 위한 패턴
 - 객체 생성에 대한 인터페이스와 구현의 분리
 - 객체 생성에 필요한 과정을 템플릿처럼 정해 놓고 각 과정을 다양하게 구현이 가능함.
 - 구체적으로 생성할 클래스를 유연하게 정의할 수 있음
 */
public class FactoryMethodPattern {

  public static void main(String[] args) {
    Factory factory = new ItemFactory();

    Item item1 = factory.create("sword");
    if (item1 != null) { item1.use(); }

    Item item2 = factory.create("sword");
    if (item2 != null) { item2.use(); }

    Item item3 = factory.create("sword");
    if (item3 != null) { item3.use(); }

    Item item4 = factory.create("sword");
    if (item4 != null) { item4.use(); }

    Item item5 = factory.create("sword");
    if (item5 != null) { item5.use(); }

    factory.create("shield");
    factory.create("shield");
    factory.create("shield");
    factory.create("shield");

    factory.create("bow");
    factory.create("bow");
    factory.create("bow");
    factory.create("bow");
  }

  /*
    - 생성에 대한 인터페이스 부분과, 생성에 대한 구현 부분이 완전히 분리되어 있다.
      - 인터페이스 부분이 구현 부분을 전혀 모른다 (구체적인 구현 방법을 모른다.)
        - 인터페이스 패키지, 구현 패키지 분리 가능
        - 새로운 구현을 추가해 시스템을 확장할 수 있다.
         ex) 음식에 대한 생성기능을 추가한다면??
    - 생성에 대한 인터페이스
      - Factory, Item
    - 생성에 대한 구현체
      - ItemFactory, Sword, Shield, Bow
   */
  static abstract class Factory {

    public Item create(String name) {
      boolean bCreatable = this.isCreatable(name);
      if (bCreatable) {
        Item item = this.createItem(name);
        postProcessItem(name);
        return item;
      }
      return null;
    }

    public abstract boolean isCreatable(String name);
    public abstract Item createItem(String name);
    public abstract void postProcessItem(String name);
  }

  static class ItemFactory extends Factory {

    static class ItemData {
      int maxCount;
      int currentCount;
      ItemData(int maxCount) {
        this.maxCount = maxCount;
      }
    }

    private HashMap<String, ItemData> repository;

    public ItemFactory() {
      this.repository = new HashMap<>();
      this.repository.put("sword", new ItemData(3));
      this.repository.put("shield", new ItemData(2));
      this.repository.put("bow", new ItemData(1));
    }

    @Override
    public boolean isCreatable(String name) {
      ItemData itemData = repository.get(name);
      if (itemData == null) {
        System.out.println(name + "은 알 수 없는 아이템 입니다.");
        return false;
      }

      if (itemData.currentCount >= itemData.maxCount) {
        System.out.println(name + "은 품절 아이템 입니다.");
        return false;
      }
      return true;
    }

    @Override
    public Item createItem(String name) {
      Item item = null;
      if (name.equals("sword")) { item = new Sword(); }
      if (name.equals("shield")) { item = new Shield(); }
      if (name.equals("bow")) { item = new Bow(); }

      return item;
    }

    @Override
    public void postProcessItem(String name) {
      ItemData itemData = repository.get(name);
      if (itemData != null) {
        itemData.currentCount++;
      }
    }
  }



  interface Item {
    void use();
  }

  static class Sword implements Item {
    @Override
    public void use() {
      System.out.println("칼로 삭 베었다.");
    }
  }

  static class Shield implements Item {
    @Override
    public void use() {
      System.out.println("방패로 공격을 막았다.");
    }
  }

  static class Bow implements Item {
    @Override
    public void use() {
      System.out.println("화살로 멀리서 쐈다.");
    }
  }



}
