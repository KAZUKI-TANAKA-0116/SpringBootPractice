package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public List<Contact> getAllContacts() {
		return contactRepository.findAll();
	}

	@Override
	public Contact getContactById(Long id) {
		return contactRepository.findById(id).orElse(null);
	}

	@Override
	public void saveContact(ContactForm contactForm) {
		Contact contact = new Contact();
		contact.setLastName(contactForm.getLastName());
		contact.setFirstName(contactForm.getFirstName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhone(contactForm.getPhone());
		contact.setZipCode(contactForm.getZipCode());
		contact.setAddress(contactForm.getAddress());
		contact.setBuildingName(contactForm.getBuildingName());
		contact.setContactType(contactForm.getContactType());
		contact.setBody(contactForm.getBody());

		contactRepository.save(contact);
	}

	@Override
	public void updateContact(Long id, Contact updatedContact) {
		Optional<Contact> contactOpt = contactRepository.findById(id);
		contactOpt.ifPresent(existingContact -> {
			existingContact.setLastName(updatedContact.getLastName());
			existingContact.setFirstName(updatedContact.getFirstName());
			existingContact.setEmail(updatedContact.getEmail());
			existingContact.setPhone(updatedContact.getPhone());
			existingContact.setZipCode(updatedContact.getZipCode());
			existingContact.setAddress(updatedContact.getAddress());
			existingContact.setBuildingName(updatedContact.getBuildingName());
			existingContact.setContactType(updatedContact.getContactType());
			existingContact.setBody(updatedContact.getBody());
			contactRepository.save(existingContact);
		});
	}

	@Override
	public void deleteContactById(Long id) {
		contactRepository.deleteById(id);
	}
}
