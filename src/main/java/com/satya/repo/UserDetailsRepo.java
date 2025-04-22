package com.satya.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.entity.UserDetails;

public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer> {
	public Optional<UserDetails> findByUserId(Integer id);

	public UserDetails findByEmail(String email);

	public UserDetails findByEmailAndPwd(String email, String pwd);
}
