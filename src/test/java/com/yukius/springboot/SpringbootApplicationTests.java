package com.yukius.springboot;

import com.yukius.springboot.controller.UserController;
import com.yukius.springboot.entity.User;

import com.yukius.springboot.entity.UserBody;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.Assert.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)//Ordered Tests
class SpringbootApplicationTests {
	User trueUser = new User("Yukius101", "goodPassword");
	User wrongUser = new User("Yukius1", "badPassword");
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
		Assertions.assertEquals(trueUser, userController.signUpUser(trueUser));
	}
	/**1Negative SignUp
	 * <br>
	 * Повторно добавить того же Пользователя*/
	@Test
	void bSignUpSameUserNegativeTest(){

		DataIntegrityViolationException thrown = assertThrows(
				DataIntegrityViolationException.class, () -> userController.signUpUser(trueUser));
		Assertions.assertTrue(thrown.getMessage().contains("нарушает ограничение уникальности"));
	}
	/**Get All UsersLogins
	 * <br>
	 * Shouldn't be empty*/
	@Test
	void cGetAllUsersTest(){
        Assertions.assertFalse(userController.getAllUsers().isEmpty());
	}
	/**2Positive LogIn
	 * <br>
	 * Войти Новым Пользователя*/
	@Test
	void dLogIntoPositiveTest(){

		Assertions.assertEquals(trueUser.toString(), userController.logInto(trueUser).toString());
	}
	/**2Negative LogIn
	 * <br>
	 * Войти с неправильным паролем Пользователя*/
	@Test
	void eLogIntoNegativeTest(){
		EntityNotFoundException thrown = assertThrows(
				EntityNotFoundException.class, () -> userController.logInto(wrongUser));
	}
	/**3Positive Update
	 * <br>
	 *
	 * */
	@Test
	void fUpdatePositiveTest(){
		Assertions.assertEquals(newTrueUser.toString(), userController.changePassword(forChangingPass).toString());
	}
	/**3Negative Update
	 * <br>
	 * Обновить пароль с вводом неправильного пароля
	 * */
	@Test
	void gUpdateNegativeTest(){
		EntityNotFoundException thrown = assertThrows(
				EntityNotFoundException.class, () -> userController.changePassword(forChangingPass));
	}
	/**Get All UsersLogins*/
	@Test
	void hGetAllUsersAfterUpdate(){
		Assertions.assertFalse(userController.getAllUsers().isEmpty());
	}

	/** Delete*/
	@Test
	void iDeleteTest(){
		Assertions.assertTrue(userController.deleteUser(new User(1L,"Yukius101", "newGoodPassword")).contains("No Longer"));

	}
	@Test
	void jNegativeDeleteTest(){
		EntityNotFoundException thrown = assertThrows(
				EntityNotFoundException.class, () -> userController.deleteUser(new User(1L,"Yukius101", "newGoodPassword")));

	}



}
