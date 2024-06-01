package com.bibliotheque.bibliotheque.services.security;

import org.springframework.context.ApplicationEvent;

import com.bibliotheque.bibliotheque.entities.Client;

public class FailedLogEvent extends ApplicationEvent {
	private static final long serialVersionUID = 33969741564703240L;
	
    private Client client;

	public FailedLogEvent(Object source, Client client) {
		super(source);
		this.client = client;
	}
	
	public Client getClient() {
		return client;
	}    
}
