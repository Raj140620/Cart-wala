package com.vendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendor.model.UserData;

public interface UserRepository extends JpaRepository<UserData, Integer>{

}
