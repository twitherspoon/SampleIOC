package commons;

import java.nio.file.Paths;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
	}

}
