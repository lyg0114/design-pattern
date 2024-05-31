package com.designpatternstudy.structural.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.composite
 * @since : 25.05.24
 */
/*
 [컴포짓 패턴]
 - 단일체(file)와, 집합체(폴더)를 하나의 동일한 개념으로 처리하기 위한 패턴
 - ex) 폴더, 파일 처리
    - 파일과, 폴더를 Unit으로 추상화 해서 동일한 개념으로 처리
 */
public class CompositePattern {

  public static void main(String[] args) {
    Folder root = new Folder("root");
    root.add(new File("a.txt", 1000));
    root.add(new File("b.txt", 2000));

    Folder sub1 = new Folder("sub1");
    sub1.add(new File("sa1.txt", 100));
    sub1.add(new File("sb1.txt", 4000));
    root.add(sub1);

    Folder sub2 = new Folder("sub2");
    sub2.add(new File("sa2.txt", 250));
    sub2.add(new File("sb2.txt", 340));
    root.add(sub2);

    root.list();
  }

  static abstract class Unit {

    private String name;

    public Unit(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return name + " { " + getSize() + " }";
    }

    // 파일, 폴더의 용량을 구하는 로직이 다르기 때문에 추상클래스로 생성
    public abstract int getSize();
  }

  static class File extends Unit {

    private int size;

    public File(String name, int size) {
      super(name);
      this.size = size;
    }

    @Override
    public int getSize() {
      return size;
    }
  }

  static class Folder extends Unit {

    private final List<Unit> unitList = new ArrayList<>();

    public Folder(String name) {
      super(name);
    }

    @Override
    public int getSize() {
      int totalSize = 0;
      Iterator<Unit> itr = unitList.iterator();
      while (itr.hasNext()) {
        Unit next = itr.next();
        totalSize += next.getSize();
      }

      return totalSize;
    }

    public boolean add(Unit unit) {
      this.unitList.add(unit);
      return true;
    }

    private void list(String indent, Unit unit) {
      if (unit instanceof File) {
        System.out.println(indent + unit);
      } else {
        Folder dir = (Folder) unit;
        Iterator<Unit> itr = dir.unitList.iterator();
        System.out.println(indent + "+ " + unit.getName());
        while (itr.hasNext()) {
          list(indent + "     ", itr.next());
        }
      }
    }

    public void list() {
      list("", this);
    }
  }
}


