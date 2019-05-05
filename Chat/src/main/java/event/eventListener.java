package event;

import org.springframework.context.event.EventListener;

public class eventListener {
	@EventListener(loginEvent.class)
	public void onApplicationEvent(loginEvent e){
		System.out.println("..............."+e.getName());
	}
	
}
