package com.muscleape.springBootDemo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloTests {
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void getHello() throws Exception {
	}
}
