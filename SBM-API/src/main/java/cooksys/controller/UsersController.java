package cooksys.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cooksys.dto.CredentialsDto;
import cooksys.dto.TweetDto;
import cooksys.dto.UsersCreationDto;
import cooksys.dto.UsersDto;
import cooksys.entity.embeddable.Credentials;
import cooksys.entity.embeddable.Profile;
import cooksys.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@RequestMapping("users")
@Api(tags = {"public", "users"})
public class UsersController {
	
	private UsersService userService;
	
	public UsersController(UsersService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping
	@ApiOperation(value = "", nickname = "getAllUsers")
	public List<UsersDto> index() {
		return userService.index();
	}
	
	@PostMapping
	@ApiOperation(value = "", nickname = "postUser")
	public UsersDto post(@RequestBody UsersCreationDto usersDto, HttpServletResponse httpResponse) {
		UsersDto post = userService.post(usersDto);
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return post;
	}
	
	@GetMapping("@{username}")
	@ApiOperation(value = "", nickname = "findUser")
	public UsersDto getUser(@PathVariable String username) {
		return userService.getUser(username);
	}
	
	@PatchMapping("@{username}") 
	@ApiOperation(value = "", nickname = "patchUser")
	public UsersDto patchUser(@RequestBody @Validated CredentialsDto cred, @RequestBody Profile prof, HttpServletResponse httpResponse) {
		UsersDto patched = userService.patchUser(cred, prof);
		httpResponse.setStatus(HttpServletResponse.SC_CREATED);
		return patched;
	}
	
	@DeleteMapping("@{username}") 
	@ApiOperation(value = "", nickname = "deleteUser")
	public UsersDto delete(@PathVariable String username, HttpServletResponse httpResponse) {
		return userService.delete(username);
	}
	
	@PostMapping("@{username}/follow") 
	@ApiOperation(value = "", nickname = "followUser")
	public void followUser(@RequestBody @Validated Credentials cred, @PathVariable String username, HttpServletResponse httpResponse) {
		userService.followUser(cred, username);
	}
	
	@PostMapping("@{username}/unfollow") 
	@ApiOperation(value = "", nickname = "unfollowUser")
	public void unfollowUser(@RequestBody @Validated Credentials cred, @PathVariable String username, HttpServletResponse httpResponse) {
		userService.unfollowUser(cred, username);
	}
	
	@GetMapping("@{username}/feed") //TODO
	@ApiOperation(value = "", nickname = "UserFeed")
	public List<TweetDto> getUserFeed(@PathVariable String username) {
		return userService.getUserFeed(username);
	}
	
	@GetMapping("@{username}/tweets") //TODO
	@ApiOperation(value = "", nickname = "UserTweets")
	public List<TweetDto> getUserTweets(@PathVariable String username) {
		return userService.getUserTweets(username);
	}
	
	@GetMapping("@{username}/mentions") //TODO
	@ApiOperation(value = "", nickname = "UserMentions")
	public List<TweetDto> getUserMentions(@PathVariable String username) {
		return userService.getUserMentions(username);
	}
	
	@GetMapping("@{username}/followers") 
	@ApiOperation(value = "", nickname = "Userfollowers")
	public List<UsersDto> getUserFollowers(@PathVariable String username) {
		return userService.getUserFollowers(username);
	}
	
	@GetMapping("@{username}/following") 
	@ApiOperation(value = "", nickname = "UserFollowing")
	public List<UsersDto> getUserFollowing(@PathVariable String username) {
		return userService.getUserFollowing(username);
	}
}
