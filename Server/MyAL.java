package Server;

import java.io.Serializable;
import java.util.ArrayList;

public class MyAL <T> extends ArrayList<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4706791903249948343L;
	public MyAL(int i){
		super(i);
	}
	
	public MyAL(){
		super();
	}
}
