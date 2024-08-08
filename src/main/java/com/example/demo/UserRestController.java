package com.example.demo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*" /*{"http://app:8080", "http://localhost:3000"}*/, allowedHeaders="*", exposedHeaders = {"Access-Control-Allow-Origin"})
@RestController
@RequestMapping(value= "/test")
public class UserRestController extends JdbcDaoSupport {
	@GetMapping("/index")
	public String index() {
		return "Docker Test Successful";
	}
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	  UserRepository userRepository;

	@jakarta.annotation.PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	

	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String id) {
	    try {
	      List<User> users = new ArrayList<User>();

	      if (id == null)
	    	  userRepository.findAll().forEach(users::add);
	      else
	        userRepository.findById(Integer.parseInt(id)).forEach(users::add);

	      if (users.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(users, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	@GetMapping("/user/{id}")
	  public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
	    Optional<User> userData = userRepository.findById(id);

	    if (userData.isPresent()) {
	      return new ResponseEntity<>(userData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	

	@PostMapping("/user")
	  public ResponseEntity<User> createUser(@RequestBody User user) {
	    try {
	    	 System.out.println(user.getName()+" "+user.getPassword());
	     User _user = userRepository
	          .save(new User(user.getName(), user.getPassword()));
	      System.out.println(_user.getName()+" "+_user.getPassword());
	      return new ResponseEntity<>(user, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	@PutMapping("/user/{id}")
	  public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
	    Optional<User> userData = userRepository.findById(id);

	    if (userData.isPresent()) {
	      User _user = userData.get();
	      _user.setName(user.getName());
	      _user.setPassword(user.getPassword());
	      
	      return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
	@DeleteMapping("/user/{id}")
	  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
	    try {
	      userRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
}
