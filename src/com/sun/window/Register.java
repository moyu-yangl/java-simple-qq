package com.sun.window;

import com.sun.client.ClientSendAndReceive;
import com.sun.domain.ResultResponse;
import com.sun.domain.User;
import com.sun.system.Constant;
import com.sun.utils.StringUtils;

import javax.swing.*;
import java.awt.*;

public class Register extends JDialog {

    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField idField;
    private JTextField pwField;
    private JTextField nicknameField;
    private JPanel newPanel;
    private JButton button;
    private JFrame frame;

    public Register(JFrame frame, String title, boolean model) {
        super(frame, title, model);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setBounds(0, 0, 500, 350);
        this.setLocationRelativeTo(frame);
        this.frame = frame;
        newPanel = new JPanel();
        newPanel.setLayout(null);
        init();
        this.setContentPane(newPanel);
        this.setVisible(true);
    }

    private void init() {

        label1 = new JLabel("账号");
        label2 = new JLabel("密码");
        label3 = new JLabel("昵称");
        idField = new JTextField();
        pwField = new JTextField();
        nicknameField = new JTextField();
        button = new JButton("注册");

        label1.setBounds(140, 50, 50, 30);
        label2.setBounds(140, 90, 50, 30);
        label3.setBounds(140, 130, 50, 30);
        idField.setBounds(200, 50, 150, 30);
        pwField.setBounds(200, 90, 150, 30);
        nicknameField.setBounds(200, 130, 150, 30);
        button.setBounds(190, 200, 90, 60);

//        label1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        label1.setFont(new Font("宋体", Font.PLAIN, 24));
        label2.setFont(new Font("宋体", Font.PLAIN, 24));
        label3.setFont(new Font("宋体", Font.PLAIN, 24));
        button.setFont(new Font("宋体", Font.PLAIN, 28));

        idField.setFont(new Font("宋体", Font.PLAIN, 24));
        pwField.setFont(new Font("宋体", Font.PLAIN, 24));
        nicknameField.setFont(new Font("宋体", Font.PLAIN, 24));

        newPanel.add(label1);
        newPanel.add(label2);
        newPanel.add(label3);
        newPanel.add(idField);
        newPanel.add(pwField);
        newPanel.add(nicknameField);
        newPanel.add(button);

        button.addActionListener(e -> {
                    String id = idField.getText();
                    String password = pwField.getText();
                    String nickname = nicknameField.getText();

                    //注册
                    ResultResponse request = new ResultResponse(new User(password, nickname, id));
                    request.setPath("user/register");
                    if (!(StringUtils.hasText(id) && StringUtils.hasText(password) && StringUtils.hasText(nickname))) {
                        JOptionPane.showMessageDialog(frame, "不允许为空", "错误", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    ClientSendAndReceive.send(request);

                    ResultResponse response = ClientSendAndReceive.receive();

                    if (response.getCode().equals(Constant.SUCCESS.getCode())) {
                        JOptionPane.showMessageDialog(frame, response.getMsg(), "Tips", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, response.getMsg(), "错误", JOptionPane.WARNING_MESSAGE);
                    }
                }
        );

    }

}
