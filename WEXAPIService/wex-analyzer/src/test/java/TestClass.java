import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class TestClass {

	@Test
	public void test() {
		LocalDateTime time = LocalDateTime.parse("2016-10-31 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println(time);
	}
}
