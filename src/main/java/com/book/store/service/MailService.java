package com.book.store.service;

import javax.servlet.http.HttpServletRequest;

import com.book.store.model.User;

public interface MailService {

	void confirmationMail(HttpServletRequest request, User user);

	void forgotPasswordMail(HttpServletRequest request, User user);
}
