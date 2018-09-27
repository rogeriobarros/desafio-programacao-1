package com.nexaas.desafio.util.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.nexaas.desafio.config.exceptions.IllegalOperationException;
import com.nexaas.desafio.model.Order;

@RunWith(SpringRunner.class)
public class ParserTest {
	
	private Parser parser;
	
	@Test
	public void testParserFileSuccesss() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("example_input.tab").getFile());
		
		parser = new Parser();
		
		List<Order> orders = parser.read(file);
		
		assertThat(orders).isNotEmpty();
	}
	
	@Test(expected=IllegalOperationException.class)
	public void testParserFileError() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("example_input_fail.tab").getFile());
		
		parser = new Parser();
		
		List<Order> orders = parser.read(file);
		
		assertThat(orders).isEmpty();
	}

}
