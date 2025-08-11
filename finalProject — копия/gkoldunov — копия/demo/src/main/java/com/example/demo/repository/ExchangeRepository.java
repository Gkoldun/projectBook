package com.example.demo.repository;

import com.example.demo.models.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    List<Exchange> findByFromUserIdOrToUserId(Long fromUserId, Long toUserId);
}
