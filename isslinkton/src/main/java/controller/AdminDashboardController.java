package com.islinkton.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.io.IOException;

import com.islinkton.model.UserModel;
import com.islinkton.service.AdminService;


@WebServlet(asyncSupported = true, urlPatterns = { "/admin/dashboard" })
public class AdminDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminDashboardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service = new AdminService();
        List<UserModel> students = service.getAllStudents();

        request.setAttribute("students", students);
		
		request.getRequestDispatcher("/pages/admin/admin-dashboard.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
