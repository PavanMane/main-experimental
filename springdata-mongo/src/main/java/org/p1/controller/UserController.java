package org.p1.controller;

import org.p1.dto.UserDTO;
import org.p1.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/user")
public class UserController {
	
	@Autowired
	private IUserService userService; 
	
	@RequestMapping(value = "/{loginName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<? extends Object> getUser(@PathVariable String loginName) {
		try {
			return new ResponseEntity<UserDTO>(userService.getUser(loginName), HttpStatus.OK);
		} catch (Exception e) {
			BaseResponse response = new BaseResponse();
			response.setCode(ResponseCode.ERROR);
			response.setMessage(e.getMessage());
			return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<BaseResponse> createUser(@RequestBody UserDTO userDTO) {
		BaseResponse response = new BaseResponse();
		try {
			userService.saveUser(userDTO);
			response.setCode(ResponseCode.SUCCESS);
			response.setMessage("Created User!");
			return new ResponseEntity<BaseResponse>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.ERROR);
			response.setMessage(e.getMessage());
			return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<BaseResponse> update(@RequestBody UserDTO userDTO) {
		BaseResponse response = new BaseResponse();
		try {
			userService.updateUser(userDTO);
			response.setCode(ResponseCode.SUCCESS);
			response.setMessage("Updated user details!");
			return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.ERROR);
			response.setMessage(e.getMessage());
			return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/{loginName}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<BaseResponse> delete(@PathVariable String loginName) {
		BaseResponse response = new BaseResponse();
		try {
			userService.deleteUser(loginName);
			response.setCode(ResponseCode.SUCCESS);
			response.setMessage("Deleted user: " + loginName);
			return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(ResponseCode.ERROR);
			response.setMessage(e.getMessage());
			return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
