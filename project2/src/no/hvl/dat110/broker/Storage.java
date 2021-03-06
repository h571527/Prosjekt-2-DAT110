package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	protected ConcurrentHashMap<String, ClientSession> clients;
	protected ConcurrentHashMap<String, List<Message>> offlineMessageBuffer; //key=user, Messages=List<Message>

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
		offlineMessageBuffer = new ConcurrentHashMap<String, List<Message>>();

	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {
		Set<String> midl = subscriptions.get(topic);
		return midl;

	}

	public void addClientSession(String user, Connection connection) {
		ClientSession sesjon = new ClientSession(user, connection);
		clients.put(user, sesjon);
	}

	public void removeClientSession(String user) {
		clients.remove(user);
	}

	public void createTopic(String topic) {
		subscriptions.put(topic, new HashSet<String>());
	
	}

	public void deleteTopic(String topic) {
		subscriptions.remove(topic);
		
	}

	public void addSubscriber(String user, String topic) {
		subscriptions.get(topic).add(user);
	}

	public void removeSubscriber(String user, String topic) {
		subscriptions.get(topic).remove(user);
	}
}
