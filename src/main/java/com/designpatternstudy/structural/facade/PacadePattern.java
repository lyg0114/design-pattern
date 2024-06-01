package com.designpatternstudy.structural.facade;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.structural.facade
 * @since : 01.06.24
 */
/*
 [Facade 패턴]
 - 어떤 기능을 처리하기 위해 여러 객체들 사이의 복잡한 매서드 사용을 감춰서 단순화 시키는 패턴
 - 복잡한 시스템이나 서브시스템의 내부 구현을 감추고, 단순한 인터페이스를 제공하여
   사용자가 복잡한 내부 구조를 알 필요 없이 쉽게 시스템을 사용할 수 있도록 하는 패턴
 - 어원 :
   - 건축 용어로서의 파사드는 건물의 정면, 특히 외관을 가리키며, 건물의 외관을 아름답고 단순하게 보이도록 설계된 부분을 의미한다.
     건물의 내부 구조와 복잡한 요소들은 파사드 뒤에 감춰져 있어, 외부에서는 그 복잡성을 알 수 없고 단순하고 깔끔한 모습만 보이게 된다.
 */
public class PacadePattern {

  public static void main(String[] args) {
    String name = "lizzy";
    Facade facade = new Facade();
    facade.run(name);
  }

  static class Facade {

    private DBMS dbms = new DBMS();
    private Cache cache = new Cache();

    public void run(String name) {
      Row row = cache.get(name);
      if (row == null) {
        row = dbms.query(name);
        if (row != null) {
          cache.put(row);
        }
      }

      if (row != null) {
        Message message = new Message(row);
        System.out.println(message.makeName());
        System.out.println(message.makeBirthday());
        System.out.println(message.makeEmail());
      } else {
        System.out.println(name + " is not exists");
      }
    }
  }

  static class Row {

    private String name;
    private String birthday;
    private String email;

    public Row(String name, String birthday, String email) {
      this.name = name;
      this.birthday = birthday;
      this.email = email;
    }

    public String getName() {
      return name;
    }

    public String getBirthday() {
      return birthday;
    }

    public String getEmail() {
      return email;
    }
  }

  static class DBMS {

    private Map<String, Row> db = new HashMap<>();

    public DBMS() {
      db.put("jane", new Row("Jane", "1990-02-14", "jane@google.com"));
      db.put("robert", new Row("robert", "1992-02-14", "robert@google.com"));
      db.put("kyle", new Row("kyle", "1993-02-14", "kyle@google.com"));
    }

    public Row query(String name) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

      return db.get(name.toLowerCase());
    }
  }

  static class Cache {

    private Map<String, Row> cache = new HashMap<>();

    public void put(Row row) {
      cache.put(row.getName(), row);
    }

    public Row get(String name) {
      return cache.get(name);
    }
  }

  static class Message {

    private Row row;

    public Message(Row row) {
      this.row = row;
    }

    public String makeName() {
      return "Name : \"" + row.getName() + "\"";
    }

    public String makeBirthday() {
      return "Birthday: " + row.getBirthday();
    }

    public String makeEmail() {
      return "Email:" + row.getEmail();
    }

  }
}












