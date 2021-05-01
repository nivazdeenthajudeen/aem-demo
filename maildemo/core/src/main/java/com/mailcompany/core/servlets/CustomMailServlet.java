package com.mailcompany.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

@Component(service = Servlet.class, immediate = true,
property= {
		"sling.servlet.methods=GET",
		"sling.servlet.paths=/custom/mailservlet"
})
public class CustomMailServlet extends SlingAllMethodsServlet{

	private static final long serialVersionUID = -7287396217733304212L;
	
	@Reference
	MessageGatewayService messageGatewayService;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
		if(messageGatewayService != null) {
			MessageGateway<HtmlEmail> gateway = messageGatewayService.getGateway(HtmlEmail.class);
			HtmlEmail htmlEmail = new HtmlEmail();
			htmlEmail.setFrom("no-reply@gmail.com");
			htmlEmail.addTo("javatamilanchennai@gmail.com");
			htmlEmail.setSubject("Test Mail");
			htmlEmail.setContent("Hi Team, <br/>I am sending this mail for your reference<br/>Thanks and Regards,<br/>Name", "text/html");
			gateway.send(htmlEmail);
		}
		response.getWriter().write("CustomMailServlet called");
		} catch (EmailException e) {
		}
	}

}
