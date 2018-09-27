package com.nexaas.desafio.util.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexaas.desafio.config.exceptions.IllegalOperationException;
import com.nexaas.desafio.model.Buyer;
import com.nexaas.desafio.model.Merchant;
import com.nexaas.desafio.model.Order;
import com.nexaas.desafio.model.Product;

public class Parser {

	private final Logger logger = LoggerFactory.getLogger(Parser.class);

	public List<Order> read(File fileParse) throws IllegalOperationException {
		File file = fileParse;
		Scanner reader = null;

		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e1) {
			logger.error("Falha ao manipular arquivo.", e1);
		}
		reader.nextLine();
		
		List<Order> orders = null;
		try {
			orders = builderOrders(reader);
		} catch (Exception e) {
			logger.error("Falha ao manipular linha.", e);
			throw new IllegalOperationException("Não foi possível processar os pedidos.");
		}

		reader.close();

		return orders;
	}

	private List<Order> builderOrders(Scanner reader) {
		List<Order> orders = new ArrayList<Order>();
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			Scanner lineReader = new Scanner(line);
			lineReader.useDelimiter("\t");
			int i = 1;
			Order order = new Order();
			Buyer buyer = new Buyer();
			Product product = new Product();
			Merchant merchant = new Merchant();
			while (lineReader.hasNext()) {
				switch (i) {
				case 1:
					buyer.setName(lineReader.next());
					break;
				case 2:
					product.setDescription(lineReader.next());
					break;
				case 3:
					product.setPrice(new BigDecimal(lineReader.next()));
					break;
				case 4:
					order.setAmount(Integer.valueOf(lineReader.next()));
					break;
				case 5:
					merchant.setAddress(lineReader.next());
					break;
				case 6:
					merchant.setName(lineReader.next());
					break;

				default:
					break;
				}
				i++;
			}
			order.setBuyer(buyer);
			order.setMerchant(merchant);
			order.setProduct(product);
			orders.add(order);
			lineReader.close();
		}
		return orders;
	}

}
