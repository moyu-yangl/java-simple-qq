package com.sun.window;

import com.sun.client.ClientSendAndReceive;
import com.sun.domain.ResultResponse;
import com.sun.domain.User;
import com.sun.system.Constant;
import com.sun.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private final JButton loginButton = new JButton("登录");    //登录按钮
    private final JButton register = new JButton("注册");       //注册按钮
    private final JPasswordField passwordField = new JPasswordField();   //密码框
    private final JTextField idTextField = new JTextField();     //账号框
    private final JPanel panel = new JPanel();           //面板

    private User user;

    public boolean login() {
        String id = idTextField.getText();
        String password = new String(passwordField.getPassword());
        if (!(StringUtils.hasText(id) && StringUtils.hasText(password))) {
            return false;
        }
        User u = new User();
        u.setId(id);
        u.setPassword(password);
        ResultResponse response = new ResultResponse(u);
        response.setPath("user/login");
        ClientSendAndReceive.send(response);
        ResultResponse receive = ClientSendAndReceive.receive();
        if (receive.getMsg().equals(Constant.SUCCESS.getMsg())) {
            user = (User) receive.getData();
            return true;
        }
        return false;
    }

    public Login() {
        super("登录");
        setBounds(400, 300, 700, 500);
        setResizable(false);    //不可调整大小
        this.setLocationRelativeTo(this.getOwner());    //相对位置
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//关闭方式
        panel.setLayout(null);
        setIdTextField();
        setPasswordField();
        setRegister();
        setLoginButton();
        this.setContentPane(panel);
        this.setVisible(true);
    }

    public void setLoginButton() {
        JFrame frame = this;
        loginButton.setFont(new Font("宋体", Font.PLAIN, 30));
        loginButton.setOpaque(false);
        loginButton.setBounds(400, 300, 100, 60);
        panel.add(loginButton);
        loginButton.addActionListener(e -> {
            if (login()) {
                frame.dispose();
                MainWindow main = new MainWindow(user);
            } else {
                JOptionPane.showMessageDialog(frame, "账号或密码错误", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void setRegister() {
        register.setFont(new Font("宋体", Font.PLAIN, 30));
        register.setBounds(200, 300, 100, 60);
        panel.add(register);
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toRegister();
            }
        });
    }

    private void toRegister() {
        new Register(this, "注册", true);
    }

    public void setPasswordField() {
        passwordField.setFont(new Font("宋体", Font.PLAIN, 50));
        passwordField.setOpaque(false);
        passwordField.setBounds(200, 160, 300, 60);
        panel.add(passwordField);
    }

    public void setIdTextField() {
        idTextField.setFont(new Font("宋体", Font.PLAIN, 50));
        idTextField.setOpaque(false);
        idTextField.setBounds(200, 100, 300, 60);
        panel.add(idTextField);
    }
}
