package org.p1.controller;

import java.time.Instant;
import java.util.Date;

import org.p1.dto.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/user")
public class UserController {
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
	public UserDTO getUser(@PathVariable String name) {
		UserDTO usr = new UserDTO();
		usr.setDob(Date.from(Instant.now()).toString());
		usr.setFirstName(name);
		usr.setMiddleName("V");
		usr.setLastName("Mane");
		return usr;
	}
}
