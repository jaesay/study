package study;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import domain.Member;

public class ReflectionTests {
	public static void main(String[] args) {
		new ReflectionTests().test();
	}
	
	private void test() {
		Member member = new Member();
		//member.setMemberSeq(1);
		//member.setMemberId("jaesay");
		member.setPassword("1234");
		member.setName("Kim Jae Seong");
		member.setRole("User");
		
		Map<String, Object> tester =beanProperties(member);
		
		tester.forEach((k,v)->System.out.println("Key : " + k + " Value : " + v));
		
	}
	
	public static Map<String, Object> beanProperties(Object bean) {
	    try {
	        Map<String, Object> map = new HashMap<>();
	        Arrays.asList(Introspector.getBeanInfo(bean.getClass(), Object.class)
	                                  .getPropertyDescriptors())
	              .stream()
	              // filter out properties with setters only
	              .filter(pd -> Objects.nonNull(pd.getReadMethod()))
	              .forEach(pd -> { // invoke method to get value
	                  try {
	                      Object value = pd.getReadMethod().invoke(bean);
	                      if (value != null) {
	                          map.put(pd.getName(), value);
	                      }
	                  } catch (Exception e) {
	                      // add proper error handling here
	                  }
	              });
	        return map;
	    } catch (IntrospectionException e) {
	        // and here, too
	        return Collections.emptyMap();
	    }
	}
}
