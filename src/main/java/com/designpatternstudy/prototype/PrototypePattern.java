package com.designpatternstudy.prototype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.prototype
 * @since : 31.05.24
 */
/*
 [프로토타입 패턴]
 - 실행 중에 생성된 객체로 다른 객체를 생성(Deep Copy : 깊은복사)하는 패턴
  - 원본과, 복사본의 상태값은 완전히 독립(서로다름 메모리에 할당)되있어 서로 영향을 주지 않음
 - Prototype으로 만든 객체의 상태를 변경해도 원본 객체의 상태는 변경되지 않음
 */
public class PrototypePattern {

  public static void main(String[] args) {
    Point pt1 = new Point();
    pt1.setX(0).setY(0);
    System.out.println("pt1.draw() = " + pt1.draw());

    Point pt2 = new Point();
    pt2.setX(100).setY(0);
    System.out.println("pt2.draw() = " + pt2.draw());

    Line line1 = new Line();
    line1.setStartPt(pt1).setEndPt(pt2);
    System.out.println("line1.draw() = " + line1.draw());

    Line lineCopy = (Line) line1.copy();

    Point pt3 = new Point();
    pt3.setX(100).setY(100);
    System.out.println("pt3.draw() = " + pt3.draw());

    Point pt4 = new Point();
    pt4.setX(0).setY(100);
    System.out.println("pt4.draw() = " + pt4.draw());

    Line line2 = new Line();
    line2.setStartPt(pt2).setEndPt(pt3);

    Line line3 = new Line();
    line3.setStartPt(pt3).setEndPt(pt4);

    Line line4 = new Line();
    line4.setStartPt(pt4).setEndPt(pt1);

    /*
     - 사각형 클래스를 따로 선언하지 않고 Group 클래스를 사용
     - 기존 클래스들을 논리적으로 조합하여 사각형 개념을 만들어냄
     - 새로운 클래스를 추가하지 않고, 새로운 개념의 객체를 생성
     */
    Group rect = new Group("RECT");
    rect.addShape(line1)
        .addShape(line2)
        .addShape(line3)
        .addShape(line4)
    ;
    System.out.println("rect.draw() = " + rect.draw());

    Group cloneRect = (Group) rect.copy();
    cloneRect.moveOffset(100, 100);

    System.out.println("cloneRect.draw() = " + cloneRect.draw());
  }

  static class Group implements Shape, Prototype {

    private List<Shape> shapeList = new ArrayList<>();
    private String name;

    public Group(String name) {
      this.name = name;
    }

    public Group addShape(Shape shape) {
      shapeList.add(shape);
      return this;
    }

    @Override
    public Object copy() {
      Group newGroup = new Group(name);
      Iterator<Shape> itr = shapeList.iterator();
      while (itr.hasNext()) {
        Prototype shape = (Prototype) itr.next();
        newGroup.shapeList.add((Shape) shape.copy());
      }
      return newGroup;
    }

    @Override
    public String draw() {
      StringBuffer buffer = new StringBuffer(name);
      buffer.append("(");
      Iterator<Shape> itr = shapeList.iterator();
      while (itr.hasNext()) {
        Shape shape = itr.next();
        buffer.append(shape.draw());
        if (itr.hasNext()) {
          buffer.append(" ");
        }
      }
      buffer.append(")");
      return buffer.toString();
    }

    @Override
    public void moveOffset(int dx, int dy) {
      Iterator<Shape> itr = shapeList.iterator();
      while (itr.hasNext()) {
        Shape shape = itr.next();
        shape.moveOffset(dx, dy);
      }
    }
  }

  static class Line implements Shape, Prototype {

    private Point startPt;
    private Point endPt;

    /*
      - 복사본 객체와 원본 객체의 필드 값들이 서로다른 메로리 공간에 위치하도록 복사 된다.
      - 복사본의 시작점과 끝점을 변경해도 원본의 시작점,끝점의 좌표가 변경되지 않는다. 반대도 동일
     */
    @Override
    public Object copy() {
      Line newLine = new Line();
      newLine.startPt = (Point) startPt.copy();
      newLine.endPt = (Point) endPt.copy();
      return newLine;
    }

    @Override
    public String draw() {
      return "LINE(" + startPt.draw() + "," + endPt.draw() + ")";
    }

    @Override
    public void moveOffset(int dx, int dy) {
      startPt.moveOffset(dx, dy);
      endPt.moveOffset(dx, dy);
    }

    public Point getStartPt() {
      return startPt;
    }

    public Line setStartPt(Point startPt) {
      this.startPt = startPt;
      return this;
    }

    public Point getEndPt() {
      return endPt;
    }

    public Line setEndPt(Point endPt) {
      this.endPt = endPt;
      return this;
    }
  }


  static class Point implements Shape, Prototype {

    int x;
    int y;

    @Override
    public Object copy() {
      Point newPoint = new Point();
      newPoint.x = this.x;
      newPoint.y = this.y;
      return newPoint;
    }

    @Override
    public String draw() {
      return "(" + x + " " + y + ")";
    }

    @Override
    public void moveOffset(int dx, int dy) {
      this.x += dx;
      this.y += dy;
    }

    public Point setX(int x) {
      this.x = x;
      return this;
    }

    public Point setY(int y) {
      this.y = y;
      return this;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }
  }

  interface Prototype {

    Object copy();
  }

  interface Shape {

    String draw();

    void moveOffset(int dx, int dy);
  }


}
