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
import org.eclipse.swt.widgets.Text;

import client.ClientMethod;
import client.DataBuffer;
import common.Request;
import common.RequestHead;

/*
 * Client的UI类 注册界面
 * 2020.3.23 zjj_zut
 * 
 */

public class RegisterUi {

	public RegisterUi() {
		super();
		// TODO Auto-generated constructor stub
		
		init();
		
	}
	public void init()
	{
		ClientMethod cm =new ClientMethod();
		
		Shell shell = new Shell(DataBuffer.display,SWT.MIN);
		shell.setSize(535,400);
		FormLayout layout = new FormLayout();
        shell.setLayout(layout);
        
        
        Button register_Button = new Button(shell,SWT.PUSH);
        register_Button.setText("注册");
        FormData formData1 = new FormData();
        formData1.right = new FormAttachment(100,-120);
        formData1.left = new FormAttachment(0,120);
        formData1.bottom = new FormAttachment(100,-60);
        formData1.top = new FormAttachment(0,250);
        register_Button.setLayoutData(formData1);
        
       
        Text paw_register_Text = new Text(shell,SWT.BORDER);
        paw_register_Text.setText("paw");
        FormData formData2 = new FormData();
        formData2.bottom = new FormAttachment(register_Button,-35);
        formData2.top = new FormAttachment(register_Button,-120);
        formData2.left = new FormAttachment(0,120);
        formData2.right = new FormAttachment(100,-120);
        paw_register_Text.setLayoutData(formData2);
        
        Text name_register_Text = new Text(shell,SWT.BORDER);
        name_register_Text.setText("your name");
        FormData formData3 = new FormData();
        formData3.top = new FormAttachment(paw_register_Text,-85);
        formData3.bottom = new FormAttachment(paw_register_Text,-25);
        formData3.left = new FormAttachment(0,120);
        formData3.right = new FormAttachment(100,-120);
        name_register_Text.setLayoutData(formData3);   
        
        Button sex_man_Button = new Button(shell, SWT.RADIO);
        sex_man_Button.setText("男");
        FormData formData4 = new FormData();
        formData4.left = new FormAttachment(0,120);
        formData4.top = new FormAttachment(paw_register_Text,10);
        sex_man_Button.setLayoutData(formData4);
        sex_man_Button.setSelection(true);
        
        Button sex_woman_Button = new Button(shell, SWT.RADIO);
        sex_woman_Button.setText("女");
        FormData formData5 = new FormData();
        formData5.left = new FormAttachment(0,200);
        formData5.top = new FormAttachment(paw_register_Text,10);
        sex_woman_Button.setLayoutData(formData5);
        
	    shell.open();
		
		
         register_Button.addMouseListener(new MouseAdapter()
        		 {

					@Override
					public void mouseDown(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseDown(e);
						String name = name_register_Text.getText();
						String paw = paw_register_Text.getText();
						String sex;
						if(sex_woman_Button.getSelection())
						{
							System.out.println(sex_woman_Button.getSelection());
							sex = "女";
						}
						else
							sex = "男";
						Request request = new Request();
						request.setRequesthead(RequestHead.REGISTER_REQUEST);
						request.setRequestbody("name", name);
						request.setRequestbody("paw", paw);
						request.setRequestbody("sex", sex);
						cm.sendRequest(request);
					}
        	 
        		 });
		
        while(!shell.isDisposed()){ 
        	
        	if(!DataBuffer.display.readAndDispatch())
        		DataBuffer.display.sleep();
        	//判断是否注册成功
        	if(DataBuffer.ct.success_register)
        	{
                MessageBox mb = new MessageBox(shell, SWT.OK);
                mb.setMessage(DataBuffer.ct.newid);
                DataBuffer.ct.newid = null;
                mb.open();
        		DataBuffer.ct.success_register=false;
        		shell.dispose();
        	}
        	}
        	
	}
       
	

}
