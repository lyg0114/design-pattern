package com.designpatternstudy.creational.builder;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.builder
 * @since : 30.05.24
 */

/*
 [빌더 패턴]
 - 복잡한 구성의 객체를 효과적으로 생성하는 패턴
  - 2. 객체 생성 시 여러 단계를 순차적으로 거칠때, 이 단계의 순서를 결정 후
    각 단계를 다양하게 구현할 수 있도록 하는 패턴
 */
public class BuilderPattern_2 {

  public static void main(String[] args) {
    Data data = new Data("Jane", 25);
    Builder builder = getBuilder(data);
    Director director = new Director(builder);
    String result = director.build();
    System.out.println("result = " + result);
  }

  private static Builder getBuilder(Data data) {
//    return new PlainTextBuilder(data);
//    return new JSONBuilder(data);
    return new XMLBuilder(data);
  }

  static class Data {

    private String name;
    private int age;

    public Data(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }
  }

  static class Director {
    private Builder builder;

    public Director(Builder builder) {
      this.builder = builder;
    }

    public String build(){
      StringBuilder sb = new StringBuilder();
      sb.append(builder.head());
      sb.append(builder.body());
      sb.append(builder.foot());
      return sb.toString();
    }
  }

  static abstract class Builder {

    protected Data data;

    public Builder(Data data) {
      this.data = data;
    }

    public abstract String head();
    public abstract String body();
    public abstract String foot();

  }

  static class PlainTextBuilder extends Builder {

    public PlainTextBuilder(Data data) {
      super(data);
    }

    @Override
    public String head() {
      return "";
    }

    @Override
    public String body() {
      StringBuilder sb = new StringBuilder();
      sb.append("Name : ");
      sb.append(data.getName());
      sb.append(", Age: ");
      sb.append(data.getAge());
      return sb.toString();
    }

    @Override
    public String foot() {
      return "";
    }
  }

  static class JSONBuilder extends Builder {

    public JSONBuilder(Data data) {
      super(data);
    }

    @Override
    public String head() {
      return "{";
    }

    @Override
    public String body() {
      StringBuilder sb = new StringBuilder();
      sb.append("\"Name\" : ");
      sb.append("\"" + data.getName() + "\"" + ",");
      sb.append("\"Age\" : ");
      sb.append(data.getAge());
      return sb.toString();
    }

    @Override
    public String foot() {
      return "}";
    }
  }


  static class XMLBuilder extends Builder {

    public XMLBuilder(Data data) {
      super(data);
    }

    @Override
    public String head() {
      StringBuilder sb = new StringBuilder();

      sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
      sb.append("<DATA>");
      return sb.toString();
    }

    @Override
    public String body() {
      StringBuilder sb = new StringBuilder();
      sb.append("<NAME>");
      sb.append(data.getName());
      sb.append("</NAME>");
      sb.append("<AGE>");
      sb.append(data.getAge());
      sb.append("</AGE>");
      return sb.toString();
    }

    @Override
    public String foot() {
      return "</DATA>";
    }
  }
}
