package org.p1.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.p1.base.BaseSpringTest;
import org.p1.dao.IUserDAO;
import org.p1.dao.User;
import org.p1.dao.impl.UserDAOImplTest;
import org.p1.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
public class UserControllerTest extends BaseSpringTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private IUserDAO userDAO;
	
	@Before
	public void init() {
		Assert.notNull(userDAO);
		Query query = new Query();
		query.addCriteria(Criteria.where("loginName").regex(".*"));
		mongoTemplate.remove(query, User.class);
	}

	@Test
	public void testGet() throws Exception {
		String firstName = "Pavan";
		String middleName = "Vasant";
		String loginName = "P1_" + UUID.randomUUID();
		userDAO.save(UserDAOImplTest.getUser(loginName, firstName, middleName));

		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/v1/user/" + loginName).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println("Response ---> " + content);
		ObjectMapper om = new ObjectMapper();
		UserDTO userDTO = om.readValue(content, UserDTO.class);
		Assert.notNull(userDTO);
		Assert.isTrue(loginName.equals(userDTO.getLoginName()));

		userDAO.deleteByLoginName(loginName);
	}

	@Test
	public void testSave() throws Exception {
		String firstName = "Pavan";
		String middleName = "Vasant";
		String loginName = "P1_" + UUID.randomUUID();
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(UserDAOImplTest.getUser(loginName, firstName, middleName), userDTO);

		ObjectMapper om = new ObjectMapper();
		String userJSON = om.writeValueAsString(userDTO);

		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post("/v1/user").contentType(MediaType.APPLICATION_JSON).content(userJSON))
				.andExpect(status().isCreated()).andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println("Response ---> " + content);

		User user = userDAO.getByLoginName(loginName);
		Assert.isTrue(loginName.equals(user.getLoginName()));

		userDAO.deleteByLoginName(loginName);
	}
	
	@Test
	public void testUpdate() throws Exception {
		String firstName = "Pavan";
		String middleName = "Vasant";
		String loginName = "P1_" + UUID.randomUUID();
		User user = UserDAOImplTest.getUser(loginName, firstName, middleName);
		userDAO.save(user);
		
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		
		// UPDATE MIDDLE NAME
		String updatedMiddleName = "_middle_updated";
		userDTO.setMiddleName(updatedMiddleName);

		ObjectMapper om = new ObjectMapper();
		String userJSON = om.writeValueAsString(userDTO);

		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.put("/v1/user").contentType(MediaType.APPLICATION_JSON).content(userJSON))
				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println("Response ---> " + content);

		user = userDAO.getByLoginName(loginName);
		Assert.isTrue(updatedMiddleName.equals(user.getMiddleName()));

		userDAO.deleteByLoginName(loginName);
	}
	
	@Test
	public void testDelete() throws Exception {
		String firstName = "Pavan";
		String middleName = "Vasant";
		String loginName = "P1_" + UUID.randomUUID();
		User user = UserDAOImplTest.getUser(loginName, firstName, middleName);
		userDAO.save(user);
		

		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.delete("/v1/user/" + loginName))
				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println("Response ---> " + content);

		Assert.isNull(userDAO.getByLoginName(loginName));
	}
}
