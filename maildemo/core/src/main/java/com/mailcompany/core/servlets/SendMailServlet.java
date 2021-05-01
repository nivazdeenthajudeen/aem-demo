package com.mailcompany.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

@Component(service = Servlet.class, immediate = true, property = {
		"sling.servlet.paths=/custom/sendmail",
		"sling.servlet.methods=GET"
})
public class SendMailServlet extends SlingSafeMethodsServlet{

	private static final long serialVersionUID = 823399496571746147L;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Reference
	MessageGatewayService messageGatewayService;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
		MessageGateway<HtmlEmail> gateway = messageGatewayService.getGateway(HtmlEmail.class);
		HtmlEmail htmlEmail =  new HtmlEmail();
		htmlEmail.addTo("javatamilanchennai@gmail.com");
		htmlEmail.setFrom("javatamilanchennai@gmail.com");
		htmlEmail.setSubject("Sending Mail");
		htmlEmail.setContent("Sending Message", "text/html");
		gateway.send(htmlEmail);
		response.getWriter().write("Called Send Mail Servlet");
		} catch (EmailException e) {
			logger.error(e.getMessage());
		}
	}

}
