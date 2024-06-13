package com.designpatternstudy.structural.flyweight;

import static java.lang.Character.*;
import static java.lang.String.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.flyweight
 * @since : 23.05.24
 */
/*
 [플라이웨이트 패턴]
 - 어떤 무거운 객체를 사용하기 위해 매번 생성하지 않고 한번만 생성하고
   다시 필요해 질때는 이전에 생성된 객체를 재사용할 수 있음
 - 객체를 생성할 떄 많은 자원이 소모될 경우 플라이웨이트 패턴을 적용하여
   훨씬 적은 자원만으로 더 빠르게 필요한 객체를 재사용할 수 있음
 */
public class flyweightPattern {

	public static void main(String[] args) {
		Number number = new Number(3212);
		number.print();
		System.out.println();
		System.out.println();
		System.out.println();
	}

	static class Number {
		private List<Digit> digits = new ArrayList<>();

		public Number(int number) {
			String strNum = Integer.toString(number);
			int len = strNum.length();
			DigitFactory digitFactory = new DigitFactory();

			for (int i = 0; i < len; i++) {
				int n = getNumericValue(strNum.charAt(i));
				Digit digit = digitFactory.getDigit(n);
				digits.add(digit);
			}
		}

		public void print() {
			int cntDigits = digits.size();
			for (int i = 0; i < cntDigits; i++) {
				Digit digit = digits.get(i);
				digit.print();
			}
		}
	}

	static class DigitFactory {
		private Digit[] pool = new Digit[] {
			null, null, null, null, null, null, null, null, null, null, null
		};

		private static String getFormat(int n) {
			return format("/Users/iyeong-gyo/Desktop/my-project/design-pattern-study/src/main/java/com/designpatternstudy/flyweight/data/%d.txt", n);
		}

		/**
		 * @param n
		 * @return - Digit 객체 재활용
		 * - pool 배열에 값이 존재하면 해당 값을 반환
		 * - 값이 존재하지 않으면
		 * - 파일로부터 데이터를 read
		 * - pool 배열에 데이터를 저장
		 * - Digit 반환
		 */
		public Digit getDigit(int n) {
			if (pool[n] != null) {
				return pool[n];
			} else {
				Digit digit = new Digit(getFormat(n));
				pool[n] = digit;
				return digit;
			}
		}
	}

	static class Digit {
		private List<String> data = new ArrayList<>();

		public Digit(String fileName) {
			FileReader fr = null;
			BufferedReader br = null;
			try {
				for (int i = 0; i < 1; i++) {
					fr = new FileReader(fileName);
					br = new BufferedReader(fr);
					data.add(br.readLine());
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (fr != null)
						fr.close();
					if (br != null)
						br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public void print() {
			String str = data.get(0);
			System.out.println(str);
		}
	}
}
