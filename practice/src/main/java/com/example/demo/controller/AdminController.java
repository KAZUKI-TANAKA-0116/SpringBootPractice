package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Contact;
import com.example.demo.form.AdminForm;
import com.example.demo.service.AdminService;
import com.example.demo.service.ContactService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("adminForm", new AdminForm());
		return "signup"; // signup.htmlを返す
	}

	@PostMapping("/signup")
	public String signup(@Validated @ModelAttribute("adminForm") AdminForm adminForm, BindingResult result) {
		if (result.hasErrors()) {
			return "signup"; // エラーは再表示
		}
		adminService.saveAdmin(adminForm); // データベースに保存
		return "redirect:/admin/signin"; // ログイン画面
	}

	@GetMapping("/signin")
	public String showSigninForm() {
		return "signin"; // signin.html
	}

	@PostMapping("/signin")
	public String login(String email, String password, Model model) {
		if (adminService.validateAdmin(email, password)) {
			return "redirect:/admin/contacts"; // ログイン成功
		} else {
			model.addAttribute("error", "メールアドレスまたはパスワードが間違っています。");
			return "signin"; // エラーは再表示
		}
	}

	@Autowired
	private ContactService contactService;

	@GetMapping("/contacts")
	public String showContacts(Model model) {
		List<Contact> contacts = contactService.getAllContacts(); // お問い合わせを取得
		model.addAttribute("contacts", contacts);
		return "contacts"; // contacts.html
	}
}
