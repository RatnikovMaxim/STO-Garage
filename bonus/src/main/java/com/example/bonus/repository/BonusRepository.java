package com.example.bonus.repository;

import com.example.bonus.entity.BonusEntity;

import org.springframework.data.jpa.repository.JpaRepository;



public interface BonusRepository extends JpaRepository<BonusEntity, Long> {

}
