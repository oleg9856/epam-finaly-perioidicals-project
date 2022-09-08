package com.gmail.fursovych20.web.command;


import com.gmail.fursovych20.web.command.exception.CommandExeption;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An interface that provides a method {@code command} to be implemented <br/>
 * by other classes to implement various commands
 *
 * @author O.Fursovych
 */
public interface Command {
	String execute(HttpServletRequest request, HttpServletResponse response) throws CommandExeption;

}
