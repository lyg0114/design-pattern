package com.designpatternstudy.behavioral.Iterator;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.behavioral
 * @since : 01.06.24
 */
/*
 [iterator 패턴]
 - Array, ArryList, linkedlist, tree ... 등과 같은 다양한 형태의 자료구조를 참조할 수 있는 [표준화된 공통 api]를 제공.
 - 개발자는 다양한 자료구조를 표준화된 하나의 api를 활용하여 데이터를 참조할 수 있다.
 */
public class IteratorPattern {

  public static void main(String[] args) {
    Item[] items = {
        new Item("CPU", 1000),
        new Item("Keyboard", 2000),
        new Item("Mouse", 3000),
        new Item("HDD", 50)
    };

    Array array = new Array(items);
    Iterator itr = array.iterator();

    while (itr.next()) {
      Item item = (Item) itr.current();
      System.out.println("item = " + item);
    }
  }

  interface Aggregator {
    Iterator iterator();
  }

  interface Iterator {
    boolean next();
    Object current();
  }

  static class Item {
    private String name;
    private int cost;

    public Item(String name, int cost) {
      this.name = name;
      this.cost = cost;
    }

    @Override
    public String toString() {
      return "Item(" +
          "name='" + name + '\'' +
          ", cost=" + cost +
          ')';
    }
  }

  static class Array implements Aggregator {

    private Item[] items;

    public Array(Item[] items) {
      this.items = items;
    }

    public Item getItem(int index) {
      return items[index];
    }

    public int getCount() {
      return items.length;
    }

    @Override
    public Iterator iterator() {
      return new ArrayIterator(this);
    }
  }

  static class ArrayIterator implements Iterator {

    private Array array;
    private int index;

    public ArrayIterator(Array array) {
      this.array = array;
      this.index = -1;
    }

    @Override
    public boolean next() {
      index ++;
      return index < array.getCount();
    }

    @Override
    public Object current() {
      return array.getItem(index);
    }
  }

}
