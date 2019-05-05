package event;

import org.springframework.context.ApplicationEvent;

public class loginEvent extends ApplicationEvent {

	private String name;
	public loginEvent(String name) {
		super(name);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
