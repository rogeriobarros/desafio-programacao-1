package com.nexaas.desafio.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nexaas.desafio.config.exceptions.IllegalOperationException;
import com.nexaas.desafio.model.Order;
import com.nexaas.desafio.repository.OrderRepository;
import com.nexaas.desafio.util.parser.Parser;

@Service
@Transactional
public class OrderService implements Serializable {

	private static final long serialVersionUID = 4467529006949984306L;

	@Autowired
	private OrderRepository orderRepository;

	private Parser parser;

	public void handlerFile(File file) throws IllegalOperationException {
		parser = new Parser();
		parser.read(file).stream().forEach(order -> {
			orderRepository.save(order);
		});
	}

	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	public Order findOne(Long id) {
		return orderRepository.findById(id).get();
	}

	public BigDecimal getSalesAmout() {
		Order[] orders = StreamSupport.stream(orderRepository.findAll().spliterator(), false).toArray(Order[]::new);
		
		BigDecimal sum = Arrays.stream(orders).map(Order::totalAmount).reduce(BigDecimal.ZERO, (p, q) -> p.add(q)); 
		
		return sum;
	}

}
