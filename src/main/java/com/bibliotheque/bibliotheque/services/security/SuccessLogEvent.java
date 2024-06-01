package com.bibliotheque.bibliotheque.services.security;

import org.springframework.context.ApplicationEvent;

import com.bibliotheque.bibliotheque.entities.Client;

public class SuccessLogEvent extends ApplicationEvent {
	private static final long serialVersionUID = 3396974156470324088L;
	
    private Client client;

	public SuccessLogEvent(Object source, Client client) {
		super(source);
		this.client = client;
	}
	
	public Client getClient() {
		return client;
	}    
}
