package com.nexaas.desafio.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.nexaas.desafio.model.Buyer;
import com.nexaas.desafio.model.Merchant;
import com.nexaas.desafio.model.Order;
import com.nexaas.desafio.model.Product;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private OrderRepository repository;
	
	@Test(expected=DataIntegrityViolationException.class)
	public void testFailPersistBuyer() throws Exception {
		Order order = createOrder();
		order.setBuyer(null);
		
		this.repository.save(order);
		this.entityManager.flush();
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void testFailPersistMerchant() throws Exception {
		Order order = createOrder();
		order.setMerchant(null);
		
		this.repository.save(order);
		this.entityManager.flush();
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void testFailPersistProduct() throws Exception {
		Order order = createOrder();
		order.setProduct(null);
		
		this.repository.save(order);
		this.entityManager.flush();
	}

	@Test
	public void testPersistAndFind() throws Exception {
		Order order = createOrder();
		
		this.repository.save(order);
		this.entityManager.flush();
		
		List<Order> orders = this.repository.findAllByBuyerId(order.getBuyer().getId());
		
		assertThat(orders.get(0).getBuyer().getName()).isEqualTo("José");
		assertThat(orders.get(0).getMerchant().getName()).isEqualTo("Casas Pedro Comério & Bebidas");
	}

	private Order createOrder() {
		Order order = new Order();
		order.setBuyer(getBuyer());
		order.setMerchant(getMerchant());
		order.setProduct(getProduct());
		order.setAmount(2);
		
		return order;
	}
	
	private Buyer getBuyer() {
		Buyer buyer = new Buyer();
		buyer.setName("José");
		
		this.entityManager.persist(buyer);
		this.entityManager.flush();
		
		return buyer;
	}
	
	private Merchant getMerchant() {
		Merchant merchant = new Merchant();
		merchant.setName("Casas Pedro Comério & Bebidas");
		merchant.setAddress("Av. Brasil, 500, Rio de Janeiro - RJ");
		
		this.entityManager.persist(merchant);
		this.entityManager.flush();
		
		return merchant;
	}

	private Product getProduct() {
		Product product = new Product();
		product.setDescription("Esfirra 500g");
		product.setPrice(new BigDecimal("14.99"));
		
		this.entityManager.persist(product);
		this.entityManager.flush();
		
		return product;
	}

}
