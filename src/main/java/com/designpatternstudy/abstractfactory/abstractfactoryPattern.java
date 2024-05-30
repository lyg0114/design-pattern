package com.designpatternstudy.abstractfactory;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.abstractfactory
 * @since : 30.05.24
 */
/*
 [앱스트렉트 패턴]
 - 어떤 특정한 상황에 대해서 그 상황에 맞는 컴포넌트들을 생성해 내는 패턴
 - Abstract Factory = 추상적인 것들을 만드는 공장
 - 먼저 만들어야할 컴포넌트들을 추상적으로 정하고 어떤 구체적인 상황이
   주어지면 각 컴포넌트들을 구체적으로 만드는 패턴
 */
public class abstractfactoryPattern {

  public static void main(String[] args) {
    // 생성되는 factory가 어떤 OS에 종속적인지 관심 없다.
    ComponentFactory factory = getComponentFactory();

    Button button = factory.createButton("확인");
    CheckBox checkBox = factory.createCheckBox(false);
    TextEdit textEdit = factory.createTextEdit("디자인 패턴");

    button.render();
    checkBox.render();
    textEdit.render();

    button.clickEvent();
    checkBox.setbChecked(true);
    textEdit.setValue("GoF의 디자인 패턴");
  }

  private static ComponentFactory getComponentFactory() {
    return new WindowsFactory();
//    return new LinuxFactory();
  }

  static abstract class Button {

    protected String caption;

    public Button(String caption) {
      this.caption = caption;
    }

    public void clickEvent() {
      System.out.println(caption + " 버튼을 클릭했습니다.");
    }

    abstract void render();
  }


  static abstract class CheckBox {

    protected boolean bChecked;

    public CheckBox(boolean bChecked) {
      this.bChecked = bChecked;
    }

    public void setbChecked(boolean bChecked) {
      this.bChecked = bChecked;
      render();
    }

    abstract void render();
  }

  static abstract class TextEdit {

    protected String value;

    public TextEdit(String value) {
      this.value = value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    abstract void render();
  }

  // interface로 정의해도 상관 없음
  static abstract class ComponentFactory {

    public abstract Button createButton(String cattion);

    public abstract CheckBox createCheckBox(boolean bChecked);

    public abstract TextEdit createTextEdit(String value);
  }

  static class WindowsButton extends Button {

    public WindowsButton(String caption) {
      super(caption);
    }

    @Override
    void render() {
      System.out.println("windows 렌더링 api를 이용해 "
          + this.caption
          + " 버튼을 그립니다.");
    }
  }

  static class LinuxButton extends Button {

    public LinuxButton(String caption) {
      super(caption);
    }

    @Override
    void render() {
      System.out.println("Linux 렌더링 api를 이용해 "
          + this.caption
          + " 버튼을 그립니다.");
    }
  }

  static class WindowsCheckBox extends CheckBox {

    public WindowsCheckBox(boolean bChecked) {
      super(bChecked);
    }

    @Override
    void render() {
      System.out.println(""
          + "windows 렌더링 api를 이용해"
          + (this.bChecked ? "체크된" : "체크 안된")
          + " 체크 박스를 그립니다.");
    }
  }

  static class LinuxCheckBox extends CheckBox {

    public LinuxCheckBox(boolean bChecked) {
      super(bChecked);
    }

    @Override
    void render() {
      System.out.println(
          "linux 렌더링 api를 이용해"
              + (this.bChecked ? "체크된" : "체크 안된")
              + " 체크 박스를 그립니다.");
    }
  }


  static class WindowsTextEdit extends TextEdit {

    public WindowsTextEdit(String value) {
      super(value);
    }

    @Override
    void render() {
      System.out.println(
          "windoes 렌더링 api를 이용해"
              + (this.value + " 값을 가진 ")
              + " 텍스트에디트를 그립니다.");
    }
  }

  static class LinuxTextEdit extends TextEdit {

    public LinuxTextEdit(String value) {
      super(value);
    }

    @Override
    void render() {
      System.out.println(
          "linux 렌더링 api를 이용해"
              + (this.value + " 값을 가진 ")
              + " 텍스트에디트를 그립니다.");
    }
  }

  static class WindowsFactory extends ComponentFactory {

    @Override
    public Button createButton(String cattion) {
      return new WindowsButton(cattion);
    }

    @Override
    public CheckBox createCheckBox(boolean bChecked) {
      return new WindowsCheckBox(bChecked);
    }

    @Override
    public TextEdit createTextEdit(String value) {
      return new WindowsTextEdit(value);
    }
  }

  static class LinuxFactory extends ComponentFactory {

    @Override
    public Button createButton(String cattion) {
      return new LinuxButton(cattion);
    }

    @Override
    public CheckBox createCheckBox(boolean bChecked) {
      return new LinuxCheckBox(bChecked);
    }

    @Override
    public TextEdit createTextEdit(String value) {
      return new LinuxTextEdit(value);
    }
  }
}
