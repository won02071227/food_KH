package com.kh.food.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import org.openide.awt.DropDownButtonFactory;

import com.kh.food.controller.UserController;
import com.kh.food.model.vo.Food;

public class InitPageFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static UserController controller;

	private static final int WIDTH = 900;
	private static final int HEIGHT = 900;
	
	private JPanel topPanel, bottomPanel, rightPanel, leftPanel, subPanel1, subPanel2;
	
	private JButton logoBtn, menuDropDownBtn, myPageBtn, orderViewBtn;
	private JButton signInBtn1, signInBtn2, signUpBtn1, signUpBtn2;
	private JButton logOffBtn1, logOffBtn2;
	private JButton orderBtn;
	
	private JTextField phoneTextField;

	public InitPageFrame(String title, UserController controller) throws Exception {
		super(title);
		InitPageFrame.controller = controller;
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null); //center window
		setLayout(new BorderLayout());
		
		/* 상단 메뉴 바 */
		this.createTopMenuBar();

		/* 화면 split */
		splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane1.setDividerLocation(300 + splitPane1.getInsets().top);

		phoneTextField = new JTextField("", 11); //핸드폰 11자리
		signInBtn1 = new JButton("로그인");
		signUpBtn1 = new JButton("회원가입");
		logOffBtn1 = new JButton("로그아웃");
		orderBtn = new JButton("주문하기");

		leftPanel = new JPanel();
		rightPanel = new JPanel(new GridLayout(4,1));
		subPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
		subPanel1.add(new JLabel("핸드폰 번호"));
		subPanel1.add(phoneTextField);
		subPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		subPanel2.add(signInBtn1);
		subPanel2.add(signUpBtn1);
		subPanel2.add(logOffBtn1);
		rightPanel.add(new JLabel());
		rightPanel.add(subPanel1);
		rightPanel.add(subPanel2);

		topPanel = new JPanel(new GridLayout(1,2));
		topPanel.add(leftPanel);
		topPanel.add(rightPanel);
		bottomPanel = new JPanel(new GridLayout(2,1));
		bottomPanel.add(new JPanel());
		bottomPanel.add(orderBtn);
		splitPane1.setTopComponent(topPanel);
		splitPane1.setBottomComponent(bottomPanel);
		splitPane1.setEnabled(false);

		/* 네비게이션 메뉴 */
		JToolBar navBar = this.createNavBar();

		splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane2.setDividerLocation(70 + splitPane2.getInsets().top);

		topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(navBar);
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(splitPane1);
		splitPane2.setTopComponent(topPanel);
		splitPane2.setBottomComponent(bottomPanel);
		splitPane2.setEnabled(false);

		/* 맨위 패널 */
		JPanel topMostPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		signInBtn2 = new JButton("로그인");
		signUpBtn2 = new JButton("회원가입");
		logOffBtn2 = new JButton("로그아웃");
		topMostPanel.add(signInBtn2);
		topMostPanel.add(signUpBtn2);
		topMostPanel.add(logOffBtn2);
		splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane3.setDividerLocation(30 + splitPane3.getInsets().top);

		topPanel = topMostPanel;
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(splitPane2);
		splitPane3.setTopComponent(topPanel);
		splitPane3.setBottomComponent(bottomPanel);
		splitPane3.setEnabled(false);

		add(splitPane3);
		
		
		/* 각 component에 이벤트 추가 */
		signInBtn1.addActionListener(new SignInEventHandler(this.phoneTextField));
		signInBtn2.addActionListener(new SignInEventHandler(this.phoneTextField));

//		p1.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				String name=((JPanel)e.getSource()).getName();
//				if(Integer.parseInt(name)==1) {
//					card.next(p2.getParent());
//					flag=false;
//				}
//			}
//		});

		setVisible(true);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void signInBtnAddMouseListener(JButton signInBtn) {
		signInBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String phoneNum = phoneTextField.getSelectedText().replaceAll("\\s+", "");
			}
		});


	}

	private void createTopMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("File");
		JMenuItem menuItemExit = new JMenuItem("Exit");
		
		menuFile.add(menuItemExit);
		
		menuBar.add(menuFile);
	}

	private JToolBar createNavBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		
		//logo메뉴버튼
		ImageIcon icon = new ImageIcon(getClass().getResource("images/burger.png"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(100, 50, Image.SCALE_SMOOTH));

		logoBtn = new JButton(icon);
		logoBtn.setPreferredSize(new Dimension(100, 50));

		JPanel panel = new JPanel();
        panel.add(logoBtn); //add button to panel

        toolbar.add(panel);//add panel to toolbar
		toolbar.add(new JSeparator());

		//menu dropdown
		menuDropDownBtn = createDropDownButton();
		menuDropDownBtn.setPreferredSize(new Dimension(70, 50));
		
		panel = new JPanel();
		panel.add(menuDropDownBtn);

		toolbar.add(panel);
		toolbar.add(new JSeparator());

		//order view
		icon = new ImageIcon(getClass().getResource("images/orderView.png"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(65, 45, Image.SCALE_SMOOTH));
		orderViewBtn = new JButton(icon);
		orderViewBtn.setPreferredSize(new Dimension(100, 50));

		panel = new JPanel();
        panel.add(orderViewBtn); //add button to panel

		toolbar.add(panel);
		toolbar.add(new JSeparator());
		
        //mypage
		icon = new ImageIcon(getClass().getResource("images/mypage.png"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		myPageBtn = new JButton(icon);
		myPageBtn.setPreferredSize(new Dimension(100, 50));

		panel = new JPanel();
        panel.add(myPageBtn); //add button to panel

		toolbar.add(panel);

//		setLayout(new FlowLayout(FlowLayout.LEFT));
//		add(toolbar);

		return toolbar;
	}

	private JButton createDropDownButton() {
		JPopupMenu popupMenu = createDropDownMenu();
		
		ImageIcon icon = new ImageIcon(getClass().getResource("images/menu2.png"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		
		JButton menuDropDownBtn = DropDownButtonFactory.createDropDownButton(icon, popupMenu);
		menuDropDownBtn.addActionListener(this);
		
		return menuDropDownBtn;
	}

	private JPopupMenu createDropDownMenu() {
		JPopupMenu popupMenu = new JPopupMenu();

		List<Food> foodMenu = controller.getFoodMenu();
		Iterator<Food> itr = foodMenu.iterator();
		Food food = null;
		while(itr.hasNext()) {
			food = itr.next();
			JMenuItem menuItem = new JMenuItem(food.toString());
			popupMenu.add(menuItem);
			menuItem.addActionListener(this);
		}
		
		return popupMenu;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
//		System.out.println(source);
		if (source instanceof JMenuItem) {
			JMenuItem clickedMenuItem = (JMenuItem) source;
			String menuText = clickedMenuItem.getText();
			System.out.println(menuText+ "를 추가합니다.");
		} else if (source instanceof JButton) {
			System.out.println("메뉴를 선택합니다.");
		}
	}

	public JTextField getPhoneTextField() { return phoneTextField; }
	public void setPhoneTextField(JTextField phoneTextField) { this.phoneTextField = phoneTextField; }
	private JSplitPane splitPane1, splitPane2, splitPane3;
	public JButton getLogoBtn() { return logoBtn; } 
	public void setLogoBtn(JButton logoBtn) { this.logoBtn = logoBtn; } 
	public JButton getMenuDropDownBtn() { return menuDropDownBtn; } 
	public void setMenuDropDownBtn(JButton menuDropDownBtn) { this.menuDropDownBtn = menuDropDownBtn; } 
	public JButton getOrderViewBtn() { return orderViewBtn; } 
	public void setOrderViewBtn(JButton orderViewBtn) { this.orderViewBtn = orderViewBtn; } 
	public JButton getSignInBtn1() { return signInBtn1; } 
	public void setSignInBtn1(JButton signInBtn1) { this.signInBtn1 = signInBtn1; } 
	public JButton getSignInBtn2() { return signInBtn2; } 
	public void setSignInBtn2(JButton signInBtn2) { this.signInBtn2 = signInBtn2; } 
	public JButton getSignUpBtn1() { return signUpBtn1; } 
	public void setSignUpBtn1(JButton signUpBtn1) { this.signUpBtn1 = signUpBtn1; } 
	public JButton getSignUpBtn2() { return signUpBtn2; } 
	public void setSignUpBtn2(JButton signUpBtn2) { this.signUpBtn2 = signUpBtn2; } 
	public JButton getLogOffBtn1() { return logOffBtn1; } 
	public void setLogOffBtn1(JButton logOffBtn1) { this.logOffBtn1 = logOffBtn1; } 
	public JButton getLogOffBtn2() { return logOffBtn2; } 
	public void setLogOffBtn2(JButton logOffBtn2) { this.logOffBtn2 = logOffBtn2; } 
	public JButton getOrderBtn() { return orderBtn; } 
	public void setOrderBtn(JButton orderBtn) { this.orderBtn = orderBtn; }
}