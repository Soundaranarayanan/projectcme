package Controller;

import java.util.Scanner;

import Model.Database;

public class Login {

	
	public static void main(String [] args) {
		new DeleteStudent().oper(new Database(), new Scanner(System.in));
	}
}
