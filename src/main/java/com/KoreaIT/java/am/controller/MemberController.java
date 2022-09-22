package com.KoreaIT.java.am.controller;

import java.sql.Connection;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MemberController {

	private Connection conn;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public MemberController(Connection conn, HttpServletRequest request, HttpServletResponse response) {
		
		this.conn = conn;
		this.request = request;
		this.response = response;
		
	}
}
