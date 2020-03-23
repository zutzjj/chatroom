package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import common.User;
/*
 * Client的全局变量
 * 2020.3.23 zjj_zut
 * 
 */
public class DataBuffer {

	public static ObjectInputStream ois;
	public static ObjectOutputStream oos;
	public static String ip = "localhost";
	public static final int port = 9001;
	public static Socket s;
	public static ClientThread ct;
	public static Display display;
	public static List<User> all_user ;
	public static int all_user_num;
	public static String username;
	public static long userid;
	public static CacheMessage message;
	public static CacheMessage localmessage;
	public static boolean exit = false;
	
}
