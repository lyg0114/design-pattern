package com.designpatternstudy.structural.decorator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.decorator
 * @since : 19.05.24
 */
/*
 [데코레이터 패턴]
 - Decorator의 뜻은 "장식하는 사람"
 - 기능을 마치 장식처럼 계속 추가할 수 있는 패턴
 - 데코레이터 패턴은 객체에 새로운 기능을 동적으로 추가하는 것이 주 목적.
 - 데코레이터는 원래 객체(Concrete Component)를 감싸서 새로운 행동을 부여.
 */
public class DecoratorPattern {

  public static void main(String[] args) {
    Strings strings = new Strings();
    strings.add("Hell~");
    strings.add("Hell~ Nice to meet you");
    strings.add("Nice to meet you");
    strings.add("killing voice");

    SideDecorator sideDecorator = new SideDecorator(strings, '#');
    LineNumberDecorator lineNumberDecorator = new LineNumberDecorator(sideDecorator);
    BoxDecorator boxDecorator = new BoxDecorator(lineNumberDecorator);
    boxDecorator.print();
  }

  static abstract class Item {

    public abstract int getLinesCount();

    public abstract int getMaxLength();

    public abstract int getLength(int index);

    public abstract String getString(int index);

    public void print() {
      int cntLines = getLinesCount();
      for (int i = 0; i < cntLines; i++) {
        String strings = getString(i);
        System.out.println("strings = " + strings);
      }
    }
  }

  static class Strings extends Item {

    private List<String> strings = new ArrayList<>();

    public void add(String item) {
      strings.add(item);
    }

    @Override
    public int getLinesCount() {
      return strings.size();
    }

    @Override
    public int getMaxLength() {
      return strings.stream()
          .mapToInt(String::length)
          .max()
          .orElse(0);
    }

    @Override
    public int getLength(int index) {
      return strings.get(index).length();
    }

    @Override
    public String getString(int index) {
      return strings.get(index);
    }
  }

  static abstract class Decorator extends Item {

    protected Item item;

    public Decorator(Item item) {
      this.item = item;
    }
  }

  static class SideDecorator extends Decorator {

    private Character ch;

    public SideDecorator(Item item, Character ch) {
      super(item);
      this.ch = ch;
    }

    @Override
    public int getLinesCount() {
      return item.getLinesCount();
    }

    @Override
    public int getMaxLength() {
      return item.getMaxLength() + 2;
    }

    @Override
    public int getLength(int index) {
      return item.getLength(index) + 2;
    }

    @Override
    public String getString(int index) {
      return ch + " " + item.getString(index) + " " + ch;
    }
  }

  static class BoxDecorator extends Decorator {

    public BoxDecorator(Item item) {
      super(item);
    }

    @Override
    public int getLinesCount() {
      return item.getLinesCount() + 2;
    }

    @Override
    public int getMaxLength() {
      return item.getMaxLength() + 2;
    }

    @Override
    public int getLength(int index) {
      return item.getLength(index) + 2;
    }

    @Override
    public String getString(int index) {
      int maxWidth = getMaxLength();
      if (index == 0 || index == (getLinesCount() - 1)) {
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for (int i = 0; i < maxWidth - 2; i++) {
          sb.append("-");
        }
        sb.append("+");
        return sb.toString();
      } else {

        return '|' + item.getString(index - 1) +
            makeTailString(maxWidth - getLength(index - 1));

      }
    }

    private String makeTailString(int count) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < count; i++) {
        sb.append(' ');
      }
      sb.append('|');

      return sb.toString();
    }
  }

  static class LineNumberDecorator extends Decorator {

    public LineNumberDecorator(Item item) {
      super(item);
    }

    @Override
    public int getLinesCount() {
      return item.getLinesCount();
    }

    @Override
    public int getMaxLength() {
      return item.getMaxLength() + 4;
    }

    @Override
    public int getLength(int index) {
      return item.getLength(index) + 4;
    }

    @Override
    public String getString(int index) {
      return String.format("%02d", index) + ": " + item.getString(index);
    }
  }


}
