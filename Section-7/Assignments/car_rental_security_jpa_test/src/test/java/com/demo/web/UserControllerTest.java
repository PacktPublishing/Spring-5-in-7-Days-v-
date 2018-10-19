package com.demo.web;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.demo.model.Car;
import com.demo.model.User;
import com.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;
	
	User user;
	
	Car car;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		user = new User();
		user.setUserId(1);
		user.setUserName("Sherlock");
		user.setPhone("000000000");
		user.setAddress("UK");
		user.setWallet(1000);
	}

	public static String toJson(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void a_create() throws Exception {
		doNothing().when(userService).createUser(user);
		mockMvc.perform(post("/create").with(httpBasic("admin", "adminPassword"))
				.contentType(MediaType.APPLICATION_JSON).content(toJson(user)))
				.andExpect((redirectedUrl("/")));
	}

	@Test
	public void b_findAll() throws Exception {
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userService.findAll()).thenReturn(users);
		
		mockMvc.perform(get("/all").with(httpBasic("employee", "employeePassword")))
		.andExpect(status().isOk());
		
		verify(userService, times(1)).findAll();
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void c_findOne() throws Exception {
		when(userService.findOne(1)).thenReturn(user);
		
		mockMvc.perform(get("/{id}", 1).with(httpBasic("employee", "employeePassword")))
		.andExpect(status().isOk());		
		verify(userService, times(1)).findOne(1);
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void d_update() throws Exception {
		when(userService.findOne(user.getUserId())).thenReturn(user);
		doNothing().when(userService).update(user);
			
		mockMvc.perform(post("/update")
						.with(httpBasic("admin", "adminPassword"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(user)))
						.andExpect(status().is3xxRedirection())
						.andExpect((redirectedUrl("/")));
	}

	@Test
	public void e_delete() throws Exception {
		when(userService.findOne(user.getUserId())).thenReturn(user);
		doNothing().when(userService).delete(user.getUserId());
		
		mockMvc.perform(get("/delete/{id}", user.getUserId()).with(httpBasic("admin", "adminPassword")))
		.andExpect(status().isOk());
	}
}
