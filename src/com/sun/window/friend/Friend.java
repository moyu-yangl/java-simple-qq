package com.sun.window.friend;

import com.sun.domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Friend extends JPanel {
    private JLabel label;
    private User friend;
    private User me;
    private JTextArea area;
    private Boolean flag = false;


    public Friend(User friend, User me) {
        this.friend = friend;
        this.me = me;
        this.setMaximumSize(new Dimension(450, 70));
        this.setMinimumSize(new Dimension(450, 70));
        setLabel();
        this.add(label);
    }

    private void setLabel() {
        label = new JLabel(friend.getNickName());
        label.setPreferredSize(new Dimension(450, 60));
        label.setFont(new Font("宋体", Font.PLAIN, 50));
        label.setBorder(BorderFactory.createTitledBorder(""));


        JPanel jp = this;
        Friend f = this;

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setToolTipText(friend.getNickName());
                jp.setBackground(new Color(222, 224, 216));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jp.setBackground(null);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                int clickCount = e.getClickCount();
                if (clickCount >= 2 && !flag) {
                    new Chat(me.getNickName(), me.getId(), friend.getNickName(), friend.getId(), f);
                    flag = true;
                }
            }
        });
    }

    public JTextArea getArea() {
        return area;
    }

    public void setArea(JTextArea area) {
        this.area = area;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
