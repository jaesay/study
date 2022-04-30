package study;

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

		
		try {
	        int a = Integer.parseInt("-1");
	        System.out.println("a: " + a);
	    } catch(NumberFormatException e) { 
	    	System.out.println("aaaaaaaaaaaaa");
	    }
		
		System.out.println("max: " + Integer.MAX_VALUE);
		
		
		
		
	}
	
}