package iface;

import java.awt.Choice;
import java.awt.Font;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class home implements Runnable {
	JFrame frame;
	JPanel p1, p2;
	JButton btnGo;
	JTextPane txtpnAdsa;
	Label lh, l1, l2, l3;
	public Choice c1, c2;
	SpeechRec spk;
	VideoPlayer vp = null;
	Moniter b;
	Thread t=null;
	boolean stopThread = false;
	String sub1[], sub2[], sub3[], file="v12", ta[];
	int arr[] = new int[3];
	int flag = 0, index;
	
	public static void main(String[] args) {
		new home();
	}
	
	home()
	{
		sub1 = new String[4];
		sub1[1] = "Artificial Intelligance";
		sub1[2] = "Artificial Intelligance";
		sub2 = new String[4];
		sub2[1] = "Android Development";
		sub2[2] = "Programming(python)";
		sub3 = new String[4];
		sub3[1] = "Fuzzy Logic";
		sub3[2] = "Machine Learning";
		ta = new String[6];
		ta[0] = "Artificial intelligence (AI) is intelligence exhibited by machines. The field of AI research defines itself as the study of \"intelligent agents\": any device that perceives its environment and takes actions that maximize its chance of success at some goal.";
		ta[1] = "Android software development is the process by which new applications are created for the Android operating system. Applications are usually developed in Java programming language using the Android software development kit (SDK), but other development environments are also available";
		ta[2] = "Fuzzy logic is a form of many-valued logic in which the truth values of variables may be any real number between 0 and 1. By contrast, in Boolean logic, the truth values of variables may only be the integer values 0 or 1.";
		ta[3] = "Python is a widely used high-level programming language for general-purpose programming, created by Guido van Rossum and first released in 1991. An interpreted language, Python has a design philosophy which emphasizes code readability, and a syntax which allows programmers to express concepts in fewer lines of code than possible in languages such as C++";
		ta[4] = "Machine learning explores the study and construction of algorithms that can learn from and make predictions on data – such algorithms overcome following strictly static program instructions by making data-driven predictions or decisions, through building a model from sample inputs.";
		ta[5] = "Please select 'branch' and 'elective subject'";
		
		initAndShowGUI();
		t = new Thread(this);
		t.start();
	}
	
	void go()
	{
		int s1 = c1.getSelectedIndex();
		int s2 = c2.getSelectedIndex();
		if(s1 == 1 && s2 == 1 || s1 == 2 && s2 == 1)
			file = "v11";
		else if(s1 == 1)
		{
			if(s2 == 2)
				file = "v12";
			else if(s2 == 3)
				file = "v13";
		}
		else if(s1 == 2)
		{
			if(s2 == 2)
				file = "v22";
			else if(s2 == 3)
				file = "v23";
		}
		if(s1 != 0 && s2 != 0)
		{
			if (vp == null)	
				vp = new VideoPlayer(file);
		}
	}
	
	void change()
	{
		c2.removeAll();
		c2.add("-- SELECT --");
		if(c1.getSelectedIndex() != 0)
		{
			c2.add(sub1[c1.getSelectedIndex()]);
			c2.add(sub2[c1.getSelectedIndex()]);
			c2.add(sub3[c1.getSelectedIndex()]);
		}
		else
			txtpnAdsa.setText(ta[5]);
	}
	
	void changeTextArea()
	{
		txtpnAdsa.setText("");
		if(c2.getSelectedIndex() == 0)
			index = 5;
		else if(c2.getSelectedIndex() == 1)
			index = 0;
		else
		{
			if(c1.getSelectedIndex() == 1)
			{
				if(c2.getSelectedIndex() == 2)
					index = 1;
				else
					index = 2;
			}
			if(c1.getSelectedIndex() == 2)
			{
				if(c2.getSelectedIndex() == 2)
					index = 3;
				else
					index = 4;
			}
		}
		txtpnAdsa.setText(ta[index]);
	}
	
	private void initAndShowGUI() {
		b=new Moniter();
		spk = new SpeechRec();	
		spk.startSpeechThread(b);
		frame = new JFrame("Institutional Electives");
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1000, 370);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {
				stopThread = true; 
				t=null;
				System.exit(0);
			}
		});
		
		btnGo = new JButton("GO");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				go();
			}
		});
		btnGo.setBounds(835, 94, 104, 26);
		frame.getContentPane().add(btnGo);
		
		txtpnAdsa = new JTextPane();
		txtpnAdsa.setEditable(false);
		txtpnAdsa.setFont(new Font("Calibri", Font.PLAIN, 20));
		txtpnAdsa.setBackground(new Color(245, 245, 245));
		txtpnAdsa.setBounds(38, 158, 901, 153);
		txtpnAdsa.setText(ta[5]);
		frame.getContentPane().add(txtpnAdsa);
		
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		p1 = new JPanel();
		p1.setBounds(0, 0, 994, 62);
		p1.setBackground(SystemColor.controlHighlight);
		frame.getContentPane().add(p1);
		p1.setLayout(null);
		
		lh = new Label("BMS College of Engineering");
		lh.setAlignment(Label.CENTER);
		lh.setBounds(0, 0, 994, 62);
		p1.add(lh);
		lh.setFont(new Font("Calibri", lh.getFont().getStyle() | Font.BOLD, 27));		

		l1 = new Label("Branch : ");
		l1.setFont(new Font("Calibri", Font.PLAIN, 16));
		l1.setBounds(38, 94, 70, 24);
		frame.getContentPane().add(l1);
				
		c1 = new Choice();	
		c1.setFont(new Font("Calibri", Font.PLAIN, 16));
		c1.setBounds(127, 94, 239, 26);
		frame.getContentPane().add(c1);
		c1.add("-- SELECT --");
		c1.add("Computer Science");
		c1.add("Information Science");
		
		l2 = new Label("Elective Subject : ");
		l2.setFont(new Font("Calibri", Font.PLAIN, 16));
		l2.setBounds(422, 96, 121, 24);
		frame.getContentPane().add(l2);

		c2 = new Choice();
		c2.setFont(new Font("Calibri", Font.PLAIN, 16));
		c2.setBounds(560, 94, 231, 26);
		frame.getContentPane().add(c2);
		c2.add("-- SELECT --");
		
		c1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				change();
			}
		});
		
		c2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				changeTextArea();
			}
		});
	}
	
	public void run()
	{
		while(!stopThread)
		{
			arr = b.get();
			System.out.println(arr[0] + " "+arr[1] + " "+arr[2]+ " "+arr[3]);
			c1.select(arr[0]);
			if(c1.getSelectedIndex() != 0)
			{	change();
				c2.select(arr[1]);
				changeTextArea();
			}
			if(arr[2] == 1)
			{
				stopThread = true; 
				t=null;
				System.exit(0);
			}
			else if (arr[2] == 2 && c1.getSelectedIndex() != 0 && c2.getSelectedIndex() != 0)
				go();
			else if (arr[3] == 1)
				System.exit(1);
			else if(arr[3] > 1 && vp!=null)
				vp.perform(arr[3]);
		}
	}
}