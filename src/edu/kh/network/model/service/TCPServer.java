package edu.kh.network.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	// Server : 서비스를 제공하는 프로그램 또는 컴퓨터
	// Client : 서버에 서비스를 요청하여 사용하는 프로그램 또는 컴퓨터
	
	// TCP Socket 프로그래밍
	// TCP : 데이터 전달의 신뢰성을 최대한 보장하기 위한 방식 (연결 지향형 통신)
	//		순차적으로 데이터를 전달하고 확인 및 오류 시 재전송.
	
	// Socket : 프로세스의 통신에 사용되는 양 끝단.
	//			Server와 Client가 통신을 하기 위한 매개체
	
	// ServerScocket : 서버용 소켓
	// -> Port와 연력되어 외부요청을 기다리는 역할
	// -> 클라이언트 요청이 올 경우 이를 수락 (accept) 하고
	// 클라이언트가 사용할 수 있는 소켓 생성
	// ---> 서버 소켓과 클라이언트 소켓이 연결되어 데이터 통신이 가능해짐
	
	public void serverStart() {
		
		// 1. 서버의 포트번호 정함 
		
		int port = 8500;  // 0~65535까지 가능, 1023 이하는 이미 사용하고 있을 가능성이 높으니 제외
		/* 사용할 변수를 미리 선언 */
		ServerSocket serverSocket = null; // Server Socket 저장 변수
		Socket clientSocket = null; // Client Socket 저장 변수
		
		InputStream is = null; // Client -> Server 입력용 스트림 변수
		BufferedReader br = null; // 입력용 보조 스트림 변수
		
		
		OutputStream os = null; // Server -> Client 출력용 스트림 변수	
		PrintWriter pw = null; // 출력용 보조 스트림 변수
		// println() print printf 등 다양한 출력함수를 지원함으로써 파일 출력을 편하게 해준다
		// BufferedWriter br = null;
		
		try {
			// 2. 서버용 소켓 객체 생성
			serverSocket = new ServerSocket(port); // Server용 소켓을 생성하여 port와 결합
			
			// 3. 클라이언트 쪽에서 접속 요청이 오길 기다림
			// -> 서버용 소켓은 생성되면 클라이언트 요청이 오기전까지
			// 다음 코드를 수행하지 않고 대기하고 있음.
			System.out.println("[Server]");
			System.out.println("클라이언트 요청을 기다리고 있습니다.");
			
			// 4. 접속 요청이 오면 요청 수락 후 해당 클라이언트에 대한 소켓 객체 생성
			// 요청을 수락하면 자동으로 Socket 개체가 얻어와짐
			clientSocket = serverSocket.accept(); 
			
			String clientIP = clientSocket.getInetAddress().getHostAddress();
							  // IP 주소 형태 만들기
			 System.out.println(clientIP + "가 연결을 요청함.");
			
			// 5. 연결된 클라이언트와 입출력 스트림 생성
			is = clientSocket.getInputStream(); // clientSocket에서 제공하는 스트림
			os = clientSocket.getOutputStream(); // clientSocket에서 제공하는 스트림
			
			// 6. 보조 스트림을 통해 성능 개선
			br = new BufferedReader( new InputStreamReader(is) ); 
			// InputStreamReader는 바이트기반 스트림과 문자기반 스트림연결에 사용하는 스트림
			
			pw = new PrintWriter(os);
			
			// 7. 스트림을 통해 읽고 쓰기
			pw.println("[서버 접속 성공]");
			pw.flush(); // 스트림에 있는 내용을 모두 밀어냄
			// close()도 밀어내기가 가능하지만, flush()를 사용하는 까닭
			// ==> stream을 닫지 않은 상태에서 내용을 내보내고 싶은 경우가 있음.
			
			// 7-2 ) 클라이언트 -> 서버에게 입력 (메시지 전송 받기)
			String message = br.readLine(); // 클라이언트 메시지를 한 줄 읽어옴
					System.out.println(clientIP + "가 보낸 메시지 : " + message);
			
		}catch(IOException e) {
			
			e.printStackTrace(); //예외 추적
			
		}finally {
			
			// 8. 통신 종료
			
			// 사용한 스트림, 소켓 자원을 모두 반환 (close)
		try {
			
		
			if(pw != null) pw.close();
			if(br != null) br.close();
			
			if(serverSocket != null) serverSocket.close();
			if(clientSocket != null) clientSocket.close();
		}	catch(IOException e) {
			e.printStackTrace();
		}
		}
		
	}
	
}
