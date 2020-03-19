package chatclient.clientui;

import java.net.Socket;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import chatclient.ClientMain;
import chatclient.ClientMethod;
/*
 * Client的UI类 用户列表UI
 * 2020.3.17 zjj_zut
 * 
 */
public class ChatUserListUi {
	private Socket s;
	private Display display;
	private int item_num;
	public ChatUserListUi(Socket s, Display display) {
		super();
		this.s = s;
		this.display = display;
		init(display);
	}
	public void init(Display display)
	{
		ClientMethod cm = new ClientMethod(s);
		Shell shell = new Shell(display,SWT.MIN);
		shell.setSize(350, 700);
		shell.setText(ClientMain.username);
		FormLayout layout = new FormLayout();
        shell.setLayout(layout);
        
		
		Table user_table = new Table(shell,SWT.FULL_SELECTION);
		FormData formdata1 = new FormData();
	    formdata1.top = new FormAttachment(0,230);
	    formdata1.bottom = new FormAttachment(100,-50);
	    formdata1.right = new FormAttachment(100,0);
	    formdata1.left = new FormAttachment(0,0);
	    user_table.setLayoutData(formdata1);
	    
	    TableColumn user_id=new TableColumn(user_table,SWT.CENTER);
	    TableColumn user_name=new TableColumn(user_table,SWT.CENTER);
	    TableColumn user_isonline=new TableColumn(user_table,SWT.CENTER);
	    
	    user_id.setText("账号");
	    user_name.setText("用户名");
	    user_isonline.setText("是否在线");
        
		user_id.setWidth(130);
		user_name.setWidth(110);
		user_isonline.setWidth(110);
		
		user_id.setResizable(false);
		user_isonline.setResizable(false);
		user_name.setResizable(false);
		
		user_table.setHeaderVisible(true);
		user_table.setLinesVisible(true);
		
		Button refresh_all_user = new Button(shell,SWT.PUSH);
		refresh_all_user.setText("刷新列表");
		FormData formdata2 = new FormData();
		formdata2.bottom = new FormAttachment(0,35);
		formdata2.top = new FormAttachment(0,5);
		formdata2.left = new FormAttachment(0,10);
		formdata2.right = new FormAttachment(0,90);
		refresh_all_user.setLayoutData(formdata2);
		
		Button logout = new Button(shell,SWT.PUSH);
		logout.setText("登出");
		FormData formdata3 = new FormData();
		formdata3.bottom = new FormAttachment(0,35);
		formdata3.top = new FormAttachment(0,5);
		formdata3.left = new FormAttachment(100,-60);
		formdata3.right = new FormAttachment(100,-10);
		logout.setLayoutData(formdata3);
		//先读取本地信息
		for(int i = 0; i < ClientMain.ct.getAll_user_num(); i++)
		{
			TableItem table_item = new TableItem(user_table,SWT.NONE);
			table_item.setText(ClientMain.all_user[i]);
			//System.out.println(all_user[i][0]+all_user[i][1]+all_user[i][2]);
			
		}
		item_num = ClientMain.ct.getAll_user_num();
		
		ClientMain.ct.setReceive_all_user(false);
		
		refresh_all_user.addMouseListener(new MouseAdapter()
				{

					@Override
					//刷新用户
					public void mouseDown(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseDown(e);
						TableItem[] item = user_table.getItems();
						cm.sentMessage("refresh_all_user//");
						while(true)
						{
							if(ClientMain.ct.isReceive_all_user())
							{
								if(item_num == ClientMain.ct.getAll_user_num())
								{
									for(int i = 0; i < ClientMain.ct.getAll_user_num(); i++)
									{			
										item[i].setText(ClientMain.all_user[i]);		
										//System.out.println(ClientMain.all_user[i][0]+ClientMain.all_user[i][1]+ClientMain.all_user[i][2]);
									}
								}
								//判断是否增加新用户
								if(item_num < ClientMain.ct.getAll_user_num())
								{
									for(int i = item_num; i < ClientMain.ct.getAll_user_num(); i++)
									{
										System.out.println("i="+i+"item_num"+item_num+"all");
										System.out.println(ClientMain.ct.getAll_user_num());
										TableItem table_item = new TableItem(user_table,SWT.NONE);
										table_item.setText(ClientMain.all_user[i]);
										
									}
								}
								item_num = ClientMain.ct.getAll_user_num();
								ClientMain.ct.setReceive_all_user(false);
								break;
							}
							
						}
						
					}
			
				});
		user_table.addMouseListener(new MouseAdapter()
				{
			//弹出聊天框，只有在线才能聊天

					@Override
					public void mouseDoubleClick(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseDoubleClick(e);
						TableItem[] item = user_table.getItems();
						int user_table_index = user_table.getSelectionIndex();
						if(item[user_table_index].getText(2).equals("true"))
						{
							ChatWindowsUi cwui ;
							//开线程
							display.asyncExec(cwui= new ChatWindowsUi(s,display,item[user_table_index].getText(0)));
						}
					}
			
				});
         logout.addMouseListener(new MouseAdapter()
        		 {
        	 //登出

					@Override
					public void mouseDown(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseDown(e);
						cm.sentMessage("logout//");
						shell.close();
						LoginUi logui = new LoginUi(s,display);
					}
        	 
        		 });
		shell.open();
        while(!shell.isDisposed()){ 
        	
        	if(!display.readAndDispatch())
        	display.sleep(); 
        	
        	}
		cm.closeThread();
	}

}
