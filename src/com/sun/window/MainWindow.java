package com.sun.window;

import com.sun.client.ClientSendAndReceive;
import com.sun.domain.Message;
import com.sun.domain.ResultResponse;
import com.sun.domain.User;
import com.sun.window.friend.Friend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame {
    private final User user;      //用户
    private JScrollPane scrollPane; //滑动面板
    private final JPanel jp = new JPanel();//主面板
    private final Map<String, Friend> map = new HashMap<>();
    private final JPanel jPanel = new JPanel();//上面板

    private ResultResponse response;//响应
    private final JButton doClick;    //接收器
    JPanel panel = new JPanel();    //下面板

    public MainWindow(User u) {
        super();

        user = u;

        setSize(500, 800);
        setLocationRelativeTo(this);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setId();
        setNickname();
        setIdField();
        setNickField();

        jp.setLayout(new BorderLayout());
        jPanel.setPreferredSize(new Dimension(500, 300));
        jPanel.setLayout(null);
        jp.add(jPanel, BorderLayout.NORTH);
        setVisible(true);
        init();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ResultResponse<Object> request = new ResultResponse<>(user);
                request.setPath("user/logout");
                ClientSendAndReceive.send(request);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    response = ClientSendAndReceive.receive();
                    doClick.doClick();
                }
            }
        }).start();

        doClick = new JButton();
        doClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(response);
                switch (response.getMsg()) {
                    case "在线好友":
                        updateOnlineUser((List<User>) response.getData());
                        break;
                    case "好友已经上线":
                        updateOnlineUser((User) response.getData());
                        break;
                    case "好友下线了":
                        deleteOnlineUser((User) response.getData());
                        break;
                    case "有新消息":
                        loadMessage((Message) response.getData());
                        break;

                }
            }
        });

    }


    public void init() {

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setSize(450, 600);

        this.setContentPane(jp);
        scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        jp.add(scrollPane, BorderLayout.CENTER);

    }

    private void loadMessage(Message msg) {
        String sender = msg.getSender();
        Friend friend = map.get(sender);

        if (friend.getFlag() && friend.getArea() != null) {
            friend.getArea().append(msg.toString());
        }
    }

    private void updateOnlineUser(List<User> friends) {
        for (User friend : friends) {
            Friend friend1 = new Friend(friend, user);
            map.put(friend.getId(), friend1);
            panel.add(friend1);
        }
        flush();
    }

    private void updateOnlineUser(User friend) {
        Friend friend1 = new Friend(friend, user);
        map.put(friend.getId(), friend1);
        panel.add(friend1);
        flush();
    }

    private void deleteOnlineUser(User friend) {
        Friend friend1 = map.remove(friend.getId());
        panel.remove(friend1);
        flush();
    }

    public void setId() {
        //id
        JLabel id = new JLabel("ID");
        id.setOpaque(false);
        id.setFont(new Font("宋体", Font.PLAIN, 30));
        id.setBounds(160, 20, 50, 50);
        jPanel.add(id);
    }

    public void setNickname() {
        //昵称
        JLabel nickname = new JLabel("昵称");
        nickname.setOpaque(false);
        nickname.setFont(new Font("宋体", Font.PLAIN, 30));
        nickname.setBounds(160, 80, 70, 50);
        jPanel.add(nickname);
    }

    public void setIdField() {
        //id栏
        JTextField idField = new JTextField(user.getId());
        idField.setOpaque(false);
        idField.setFont(new Font("宋体", Font.PLAIN, 30));
        idField.setBounds(260, 20, 200, 50);
        idField.setEditable(false);
        jPanel.add(idField);
    }

    public void setNickField() {
        //昵称栏
        JTextField nickField = new JTextField(user.getNickName());
        nickField.setOpaque(false);
        nickField.setFont(new Font("宋体", Font.PLAIN, 30));
        nickField.setBounds(260, 80, 200, 50);
        nickField.setEditable(false);
        jPanel.add(nickField);
    }

    private void flush() {
        scrollPane.setVisible(false);
        scrollPane.setVisible(true);
    }
}
