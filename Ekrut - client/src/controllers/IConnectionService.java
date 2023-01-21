package controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

import Entities.User;

public interface IConnectionService {
	public void connect(String userId, String pass) throws IOException;
	public void setUser(User user);
	public void appConnector(int id);
}