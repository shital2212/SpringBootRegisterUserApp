package com.example.demo;
import java.util.*;
 
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	 List<User> findByName(String name);
	  List<User> findById(int id);

}