package com.sun.window.friend;

import com.sun.client.ClientSendAndReceive;
import com.sun.domain.Message;
import com.sun.domain.ResultResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Chat extends JFrame {

    private String MyNickname;
    private String MyId;
    private String friendNickname;
    private String friendId;
    private JTextArea area1;
    private JTextArea area2;

    private Boolean flag;

    public Chat(String MyNickname, String MyId,
                String friendNickname, String friendId, Friend friend) {
        super("与 " + friendNickname + " 的聊天");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(this);
        JPanel pane = (JPanel) this.getContentPane();
        pane.setLayout(new BorderLayout());

        this.MyNickname = MyNickname;
        this.MyId = MyId;
        this.friendNickname = friendNickname;
        this.friendId = friendId;
        this.flag = flag;

        area1 = new JTextArea();
        area1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        area1.setEditable(false);
        area2 = new JTextArea();
        area2.setFont(new Font("微软雅黑", Font.PLAIN, 30));


        JScrollPane jScrollPane1 = new JScrollPane(area1);
        JScrollPane jScrollPane2 = new JScrollPane(area2);
        jScrollPane1.setPreferredSize(new Dimension(1200, 500));
        jScrollPane2.setPreferredSize(new Dimension(1200, 200));

        area1.setBorder(BorderFactory.createLineBorder(new Color(222, 224, 216), 5, true));
        area2.setBorder(BorderFactory.createLineBorder(new Color(222, 224, 216), 5, true));

        JButton jButton = new JButton("发送");
        jButton.setFont(new Font("宋体", Font.PLAIN, 30));
        jButton.setPreferredSize(new Dimension(200, 100));
        pane.add(jScrollPane1, BorderLayout.NORTH);

        JPanel panel = new JPanel();

        panel.add(new JLabel());
        panel.add(jButton);
        panel.add(new JLabel());

        pane.add(panel, BorderLayout.SOUTH);

        pane.add(jScrollPane2, BorderLayout.CENTER);

        friend.setArea(area1);

        jButton.addActionListener(x -> {
            String trim = area2.getText().trim();
            Message message = new Message(MyId, friendId, trim, MyNickname);
            ResultResponse request = new ResultResponse(message);
            request.setPath("message/send");
            ClientSendAndReceive.send(request);
            area1.append(message.toString());
            area2.setText("");
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                friend.setArea(null);
                friend.setFlag(false);
            }
        });

        this.setVisible(true);
    }


}

