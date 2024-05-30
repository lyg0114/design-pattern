package com.designpatternstudy.builder;

import java.util.Random;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.builder
 * @since : 30.05.24
 */

/*
 [빌더 패턴]
 - 복잡한 구성의 객체를 효과적으로 생성하는 패턴
  - 1. 생성시 지정해야할 인자가 많을때 사용하는 패턴
 */
public class BuilderPattern_1 {

  public static void main(String[] args) {
    Car car1 = new Car("V7", true, "Black", true, false);

    //Method Chaining
    Car car2 = new CarBuilder()
        .AEB(false)
        .airbag(false)
        .color("white")
        .engine("v9")
        .build();

    //Method Chaining
    CarBuilder builder = new CarBuilder()
        .AEB(false)
        .airbag(false)
        .color("white")
        .engine("v9");

    Random random = new Random();
    Car car3 = builder.airbag(random.nextInt(2) == 0)
        .build();

    System.out.println("car1 = " + car1);
    System.out.println("car2 = " + car2);
    System.out.println("car3 = " + car3);

  }

  static class CarBuilder {
    private String engine;
    private Boolean airbag;
    private String color;
    private Boolean cameraSensor;
    private Boolean AEB;

    public CarBuilder engine(String engine) {
      this.engine = engine;
      return this;
    }

    public CarBuilder airbag(Boolean airbag) {
      this.airbag = airbag;
      return this;
    }

    public CarBuilder color(String color) {
      this.color = color;
      return this;
    }

    public CarBuilder cameraSensor(Boolean cameraSensor) {
      this.cameraSensor = cameraSensor;
      return this;
    }

    public CarBuilder AEB(Boolean AEB) {
      this.AEB = AEB;
      return this;
    }

    public Car build() {
      return new Car(engine, airbag, color, cameraSensor, AEB);
    }
  }

  static class Car {
    private String engine;
    private Boolean airbag;
    private String color;
    private Boolean cameraSensor;
    private Boolean AEB;

    public Car(String engine, Boolean airbag, String color, Boolean cameraSensor, Boolean AEB) {
      this.engine = engine;
      this.airbag = airbag;
      this.color = color;
      this.cameraSensor = cameraSensor;
      this.AEB = AEB;
    }

    @Override
    public String toString() {
      return "Car{" +
          "engine='" + engine + '\'' +
          ", airbag=" + airbag +
          ", color='" + color + '\'' +
          ", cameraSensor=" + cameraSensor +
          ", AEB='" + AEB + '\'' +
          '}';
    }
  }
}
