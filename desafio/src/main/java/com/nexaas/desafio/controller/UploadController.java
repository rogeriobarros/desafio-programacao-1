package com.nexaas.desafio.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nexaas.desafio.config.exceptions.IllegalOperationException;
import com.nexaas.desafio.model.Order;
import com.nexaas.desafio.service.OrderService;
import com.nexaas.desafio.util.PageWrapper;

@Controller
public class UploadController {

	private final Logger logger = LoggerFactory.getLogger(UploadController.class);

	private static final String UPLOADED_FOLDER = System.getProperty("java.io.tmpdir");

	@Autowired
	private OrderService orderService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/upload")
	public String upload() {
		return "upload";
	}

	@PostMapping("/uploadform")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			HttpSession session) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Por favor selecione um arquivo para upload");
			return "redirect:upload";
		} else if (fileHashDuplicate(file, session)) {
			redirectAttributes.addFlashAttribute("message", "Arquivo já utilizado.");
			return "redirect:upload";
		}

		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + File.separator + file.getOriginalFilename());
			orderService.handlerFile(Files.write(path, bytes).toFile());

			redirectAttributes.addFlashAttribute("successMessage",
					"Arquivo processado com sucesso '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			logger.error("Falha no processamento", e);
		} catch (IllegalOperationException e) {
			redirectAttributes.addFlashAttribute("message",
					"Falha ao processar o arquivo '" + file.getOriginalFilename() + "'.");
		}

		return "redirect:orders";
	}

	private boolean fileHashDuplicate(MultipartFile file, HttpSession session) {
		byte[] hash = null;
		try {
			hash = MessageDigest.getInstance("MD5").digest(file.getBytes());
		} catch (NoSuchAlgorithmException | IOException e) {
			logger.error("Falha no processamento", e);
		}

		String stringHash = DatatypeConverter.printHexBinary(hash);

		if (session.getAttribute("fileHash") != null && session.getAttribute("fileHash").equals(stringHash)) {
			return true;
		} else {
			session.setAttribute("fileHash", stringHash);
			return false;
		}
	}

	@GetMapping("/orders")
	public String findPaginated(Model uiModel, Pageable pageable) {
		PageWrapper<Order> page = new PageWrapper<Order>(orderService.findAll(pageable), "/orders");
		DecimalFormat formmater = new java.text.DecimalFormat("#,###,##0.00");
		page.setSalesAmout(formmater.format(orderService.getSalesAmout()));

		uiModel.addAttribute("page", page);
		return "orders";
	}

	@GetMapping("/orderDetails")
	public String findById(Model uiModel, Long id) {
		Order order = orderService.findOne(id);
		uiModel.addAttribute("order", order);
		return "orderDetails";
	}

}