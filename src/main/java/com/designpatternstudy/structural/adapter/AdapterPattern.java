package com.designpatternstudy.structural.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.adapter
 * @since : 28.05.24
 */

/*
 [어뎁터 페턴]
  - 클래스에 대한 코드를 변경할 수 없는 상황에서 어뎁터 페턴을 사용해서 사용할 수 있다.
 */
public class AdapterPattern {

	public static void main(String[] args) {
		List<Animal> animals = new ArrayList<>();
		animals.add(new Dog("댕이"));
		animals.add(new Cat("솜털이"));
		animals.add(new Cat("휴휴"));
		//    animals.add(new Tiger("휴휴"));
		animals.add(new TigerAdapter("타이온"));

		for (Animal animal : animals) {
			animal.sound();
		}
	}

	static abstract class Animal {

		protected String name;

		public Animal(String name) {
			this.name = name;
		}

		public abstract void sound();
	}

	static class Dog extends Animal {

		public Dog(String name) {
			super(name);
		}

		@Override
		public void sound() {
			System.out.println(name + " barking");
		}
	}

	static class Cat extends Animal {

		public Cat(String name) {
			super(name);
		}

		@Override
		public void sound() {
			System.out.println(name + " meeow");
		}
	}

	static class TigerAdapter extends Animal {
		private Tiger tiger;

		public TigerAdapter(String name) {
			super(name);
			tiger = new Tiger();
			tiger.setName(name);
		}

		@Override
		public void sound() {
			System.out.print(tiger.getName() + " ");
			tiger.roar();
		}
	}

	static class Tiger {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		void roar() {
			System.out.println("growl");
		}
	}

}
