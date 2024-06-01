package com.designpatternstudy.behavioral.Iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.behavioral.Iterator
 * @since : 01.06.24
 */
public class IteratorExample {

  public static void main(String[] args) {

    List<String> lists = getCollection();
    lists.add("data1");
    lists.add("data2");
    lists.add("data3");
    lists.add("data4");

    Iterator<String> itr = lists.iterator();
    while (itr.hasNext()) {
      System.out.println("itr = " + itr.next());
    }
  }

  private static List<String> getCollection() {
//    return new Stack<>();
//    return new ArrayList<>();
    return new LinkedList<>();
  }

}
