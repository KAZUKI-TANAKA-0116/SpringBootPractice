package com.example.demo.service;

import com.example.demo.form.AdminForm;

public interface AdminService {

    void saveAdmin(AdminForm adminForm);

    boolean validateAdmin(String email, String password);
}