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
import org.eclipse.swt.widgets.Text;

import chatclient.ClientMain;
import chatclient.ClientMethod;
/*
 * Client的UI类 注册界面
 * 2020.3.17 zjj_zut
 * 
 */
public class RegisterUi {

	private Socket s;
	private Display display;
	public RegisterUi(Socket s, Display display) {
		super();
		// TODO Auto-generated constructor stub
		this.s=s;
		this.display=display;
		init(display);
		
	}
	public void init(Display display)
	{
		ClientMethod cm =new ClientMethod(s);
		
		Shell shell = new Shell(display,SWT.MIN);
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
						String register_message = "register//"+name_register_Text.getText()+"//"+paw_register_Text.getText()+"//";
						
						if(sex_woman_Button.getSelection())
						{
							System.out.println(sex_woman_Button.getSelection());
							register_message += "女";
						}
						else
							register_message += "男";
						
						
						cm.sentMessage(register_message);
						System.out.println(register_message);
					}
        	 
        		 });
		
        while(!shell.isDisposed()){ 
        	
        	if(!display.readAndDispatch())
        	display.sleep();
        	//判断是否注册成功
        	if(ClientMain.ct.isSuccess_register())
        	{
        		ClientMain.ct.setSuccess_register(false);
        		shell.dispose();
        	}
        	}
        	
	}
       
	

}
