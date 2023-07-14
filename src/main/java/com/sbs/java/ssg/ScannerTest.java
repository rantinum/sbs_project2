package com.sbs.java.ssg;
import java.util.*;

public class ScannerTest {
	public static void main (String[] args) {
		
		Scanner sc =  new Scanner(System.in); //스캐너 객체 생성
		String addr = ""; // 주소 만들기
		System.out.println("주소를 입력해주세요 : ") ; //주소 입력하는 명령어 출력
		addr = sc.nextLine(); // 주소 입력 명령어
		String name = ""; // 이름 만들기
		System.out.println("이름을 입력해 주세요 : ");// 이름 입력하는 명령어 출력
		name = sc.nextLine(); // 이름 입력 명령어
		
		
		
		System.out.println(name + " 님은 " + addr + " 에 살고있습니다."); // 입력된 결과 출력
		sc.close(); // 스캐너 사용 종료
	}
	
		
	}
	





