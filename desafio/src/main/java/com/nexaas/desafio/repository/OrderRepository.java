package com.nexaas.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nexaas.desafio.model.Order;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
	
	@Query(value = "SELECT o FROM Order o WHERE o.buyer.id = ?1")
	List<Order> findAllByBuyerId(Long buyerId);
	
}
