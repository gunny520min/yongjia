package com.yongjia.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yongjia.dao.UserMapper;
import com.yongjia.model.User;

public class UserTest {

	private UserMapper userMapper;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:conf/spring.xml",
						"classpath:conf/spring-mybatis.xml" });
		userMapper = (UserMapper) context.getBean("userMapper");
	}

	@Test
	public void addUser() {
		User user = new User();
		user.setNickname("你好");
		user.setState(2);
		System.out.println(userMapper.insert(user));
	}
}