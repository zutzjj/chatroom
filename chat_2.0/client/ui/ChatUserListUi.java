package client.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import client.ClientMethod;
import client.DataBuffer;
import common.Request;
import common.RequestHead;


/*
 * Client的UI类 在线用户列表界面
 * 2020.3.23 zjj_zut
 * 
 */
public class ChatUserListUi {
	
	public ChatUserListUi() {
		super();
		
		init();
	}
	public void init()
	{
		ClientMethod cm = new ClientMethod();
		Shell shell = new Shell(DataBuffer.display,SWT.MIN);
		shell.setSize(350, 700);
		shell.setText(DataBuffer.username);
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
	    
	    user_id.setText("账号");
	    user_name.setText("用户名");
        
		user_id.setWidth(130);
		user_name.setWidth(110);
		
		user_id.setResizable(false);
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
		
		for(int i = 0; i < DataBuffer.all_user_num; i++)
		{
			TableItem table_item = new TableItem(user_table,SWT.NONE);
			String id = Long.toString(DataBuffer.all_user.get(i).getId());
			table_item.setText(new String[] {id,DataBuffer.all_user.get(i).getName()});
			
			
		}
		
		DataBuffer.all_user.clear();
		DataBuffer.ct.receive_all_user=false;
		
		refresh_all_user.addMouseListener(new MouseAdapter()
				{

					@Override
					//刷新用户
					public void mouseDown(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseDown(e);
						
						Request request = new Request();
						request.setRequesthead(RequestHead.REFRESH_ALL_USER_REQUEST);
						cm.sendRequest(request);
						while(true)
						{
							if(DataBuffer.ct.receive_all_user)
							{   //得到所有的tableItem
								TableItem tableItems[] = user_table.getItems();
						        for(int i = 0; i<tableItems.length; i++)
						        {
						            tableItems[i].dispose();//释放
						        }
						        for(int i = 0; i < DataBuffer.all_user_num; i++)
								{
									TableItem table_item = new TableItem(user_table,SWT.NONE);
									String id = Long.toString(DataBuffer.all_user.get(i).getId());
									table_item.setText(new String[] {id,DataBuffer.all_user.get(i).getName()});
									
								}
						        DataBuffer.all_user.clear();
								DataBuffer.ct.receive_all_user=false;
								
								break;
							}
							
						}
						
					}
			
				});
		user_table.addMouseListener(new MouseAdapter()
				{
			//弹出聊天框

					@Override
					public void mouseDoubleClick(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseDoubleClick(e);
						TableItem[] item = user_table.getItems();
						int user_table_index = user_table.getSelectionIndex();
						
							
						ChatWindowsUi cwui ;
							//开线程
						DataBuffer.display.asyncExec(cwui= new ChatWindowsUi(shell,item[user_table_index].getText(0)));
						
					}
			
				});
         logout.addMouseListener(new MouseAdapter()
        		 {
        	 //登出

					@Override
					public void mouseDown(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseDown(e);
						Request request = new Request();
						request.setRequesthead(RequestHead.LOGOUT_REQUEST);
						cm.sendRequest(request);
						shell.close();
					}
        	 
        		 });
		shell.open();
        while(!shell.isDisposed()){ 
        	
        	if(!DataBuffer.display.readAndDispatch())
        		DataBuffer.display.sleep();
        	if(DataBuffer.ct.login_clash)
        	{
        		MessageBox box = new MessageBox(shell,SWT.OK|SWT.ICON_WARNING);
        		box.setMessage("登录冲突");
        		DataBuffer.ct.login_clash = false;
        		box.open();
        	}
        	
        	if(DataBuffer.exit)
        	{
        		cm.closeThread();

        	}
        	
        }
       
		cm.closeThread();
	}
	

}
