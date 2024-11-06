package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	@Autowired
	private ContactService contactService;

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("adminForm", new AdminForm());
		return "signup"; // signup.html
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

	@GetMapping("/contacts")
	public String showContacts(Model model) {
		List<Contact> contacts = contactService.getAllContacts(); // すべてのお問い合わせを取得
		model.addAttribute("contacts", contacts);
		return "contacts"; // contacts.html
	}

	@GetMapping("/contact/{id}")
	public String showContactDetail(@PathVariable Long id, Model model) {
		Contact contact = contactService.getContactById(id); // IDに基づいてDBからデータ取得
		model.addAttribute("contact", contact);
		return "contactdetail"; // contactdetail.html
	}

	@GetMapping("/contacts/{id}/edit")
	public String showEditContactForm(@PathVariable Long id, Model model) {
		Contact contact = contactService.getContactById(id); //IDに基づいてDBからデータ取得
		if (contact == null) {
			return "error"; // 
		}
		model.addAttribute("contact", contact);
		return "contactedit"; //contactedit.html
	}

	@PostMapping("/contacts/{id}/edit")
	public String updateContact(@PathVariable Long id, @ModelAttribute Contact updatedContact) {
		contactService.updateContact(id, updatedContact); // 更新
		return "redirect:/admin/contacts"; // 一覧画面
	}

	@PostMapping("/contacts/{id}/delete")
	public String deleteContact(@PathVariable Long id) {
		contactService.deleteContactById(id); // contact削除
		return "redirect:/admin/contacts"; // 一覧画面
	}

}
