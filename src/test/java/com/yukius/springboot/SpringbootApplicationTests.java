package com.yukius.springboot;

import com.yukius.springboot.controller.UserController;
import com.yukius.springboot.entity.User;

import com.yukius.springboot.entity.UserBody;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.MethodOrderer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;


@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)//Ordered Tests
class SpringbootApplicationTests {
	User trueUser = new User("Yukius101", "goodPassword");
	User wrongUser = new User("Yukius101", "badPassword");
	UserBody forChangingPass = new UserBody("Yukius101", "goodPassword", "newGoodPassword");
	User newTrueUser = new User("Yukius101", "newGoodPassword");

	private final UserController userController;
	@Autowired
	public SpringbootApplicationTests(UserController userController) {
		this.userController = userController;
	}



	/**1Positive SignUp
	 * <br>
	 * Добавить Нового Пользователя*/
	@Test
	void aSignUpPositiveTest(){
		assertEquals(trueUser, userController.signUpUser(trueUser));
	}
	/**1Negative SignUp
	 * <br>
	 * Повторно добавить того же Пользователя*/
	@Test
	void bSignUpSameUserNegativeTest(){

		DataIntegrityViolationException thrown = assertThrows(
				DataIntegrityViolationException.class, () -> userController.signUpUser(trueUser));
		assertTrue(thrown.getMessage().contains("нарушает ограничение уникальности"));
	}
	/**Get All UsersLogins
	 * <br>
	 * Shouldn't be empty*/
	@Test
	void cGetAllUsersTest(){
        assertFalse(userController.getAllUsers().isEmpty());
	}
	/**2Positive LogIn
	 * <br>
	 * Войти Новым Пользователя*/
	@Test
	void dLogIntoPositiveTest(){

		assertEquals(trueUser.toString(), userController.logInto(trueUser).toString());
	}
	/**2Negative LogIn
	 * <br>
	 * Войти с неправильным паролем Пользователя*/
	@Test
	void eLogIntoNegativeTest(){
		EntityNotFoundException thrown = assertThrows(
				EntityNotFoundException.class, () -> userController.logInto(wrongUser));
		assertTrue(thrown.getMessage().equals("Please enter a correct login and password"));
	}
	/**3Positive Update
	 * <br>
	 *
	 * */
	@Test
	void fUpdatePositiveTest(){
		assertEquals(newTrueUser.toString(), userController.changePassword(forChangingPass).toString());
	}
	/**3Negative Update
	 * <br>
	 * Обновить пароль с вводом неправильного пароля
	 * */
	@Test
	void gUpdateNegativeTest(){
		EntityNotFoundException thrown = assertThrows(
				EntityNotFoundException.class, () -> userController.changePassword(forChangingPass));
		assertTrue(thrown.getMessage().equals("Please enter a correct login and previous password"));
	}
	/**Get All UsersLogins*/
	@Test
	void hGetAllUsersAfterUpdate(){
		assertFalse(userController.getAllUsers().isEmpty());
	}

	/** Delete*/
	@Test
	void iDeleteTest(){
		assertTrue(userController.deleteUser(new User(1l,"Yukius101", "newGoodPassword")).contains("No Longer"));


	}
	@Test
	void jNegativeDeleteTest(){
		EntityNotFoundException thrown = assertThrows(
				EntityNotFoundException.class, () -> userController.deleteUser(new User(1l,"Yukius101", "newGoodPassword")));
		assertTrue(thrown.getMessage().equals("Please enter a correct login and password"));

	}



}
