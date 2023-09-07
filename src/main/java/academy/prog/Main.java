package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String login = null;
		String password;
		try {
			System.out.println("Enter your login: ");
			login = scanner.nextLine();
			System.out.println("Enter your password: ");
			password = scanner.nextLine();

			User user = new User(login, password, true);
			int resLogin = user.login(Utils.getURL() + "/login");
			if (resLogin == 401){
				System.out.println("Wrong password!");
				return;
			}
			if (resLogin != 200) { // 200 OK
				System.out.println("HTTP error occurred: " + resLogin);
				return;
			}

	
			Thread th = new Thread(new GetThread(login));
			th.setDaemon(true);
			th.start();

            System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) break;

				// users
				// @test Hello
				String to = TextProcessing.getTo(text);
				text = TextProcessing.getText(text);

				Message m = new Message(login, to, text);
				int res = m.send(Utils.getURL() + "/add");

				if (res != 200) { // 200 OK
					System.out.println("HTTP error occurred: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
			Message m = new Message("SERVER", "All", "User @" + login + " left the chat.");
			int res = 0;
			try {
				res = m.send(Utils.getURL() + "/add");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			if (res != 200) { // 200 OK
				System.out.println("HTTP error occurred: " + res);
				return;
			}
		}
	}
}
