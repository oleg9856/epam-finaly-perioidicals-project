package com.gmail.fursovych20.web.command;


import com.gmail.fursovych20.web.command.exception.CommandExeption;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption;

}
