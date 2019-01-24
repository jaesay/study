package test;

import domain.enums.Role;

public class EnumTests {

	public static void main(String[] args) {
		new EnumTests().test();
	}

	private void test() {
		/*Mode mode = Mode.SIGNIN;
		System.out.println(mode);
		System.out.println(mode.getName());
		
		for(Mode m : Mode.values()) {
			System.out.println("name: " + m.getName() + ", uiName: " + m.getUiName() + ", toString: " + m.toString());
		}
		
		System.out.println("sign up".equals(Mode.SIGNUP.getName()));
		System.out.println(Mode.SIGNUP.toString());*/

		System.out.println(Role.ADMIN);
		
	}
	
}