package Forms;

import Enums.Player;
import Listeners.PlayingGameListener;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class PlayingForm extends JFrame {
    private JPanel MainPanel;
    private JPanel GameButtonPanel;
    private JButton SplitFourButton;
    private JButton TakeNumberButton;
    private JButton SplitTwoButton;
    private DefaultListModel<String> HumanHistoryScoreListModel;
    private JList<String> HumanHistoryScoreList;
    private DefaultListModel<String> NumberStringHistoryListModel;
    private JList<String> NumberStringHistoryList;
    private DefaultListModel<String> ComputerHistoryScoreListModel;
    private JList<String> ComputerHistoryScoreList;
    private JPanel HistoryPanel;
    private JLabel HumanHistoryScoreListLabel;
    private JLabel NumberStringHistoryListLabel;
    private JLabel ComputerHistoryScoreListLabel;
    private JPanel GamePanel;
    private JTextField HumanScoreTextField;
    private JTextField NumberStringTextField;
    private JTextField ComputerScoreTextField;
    private JButton ShowHideHistoryButton;
    private JLabel HistoryLabel;
    private JScrollPane HumanHistoryScoreListPane;
    private JScrollPane NumberStringHistoryListPane;
    private JScrollPane ComputerHistoryScoreListPane;
    private JLabel InfoLabel;
    private JList<String> PlayerMoveHistoryList;
    private DefaultListModel<String> PlayerMoveHistoryListModel;
    private JScrollPane PlayerMoveHistoryListPane;
    private JPanel ButtonPanel;
    private JButton QuitButton;
    private JButton StartNewButton;
    private JLabel NumberStringTextLabel;
    private JLabel ComputerScoreTextLabel;
    private JLabel HumanScoreTextLabel;
    private PlayingGameListener listener;
    private Player playerMove = null;

    public PlayingForm(PlayingGameListener listener, StartForm startForm) {
        this.listener = listener;
        listener.setPlayingForm(this);

        setTitle("Playing game");
        setSize(840, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(MainPanel);
        setLocationRelativeTo(startForm);

        HumanHistoryScoreListModel = new DefaultListModel<>();
        HumanHistoryScoreList.setModel(HumanHistoryScoreListModel);

        NumberStringHistoryListModel = new DefaultListModel<>();
        NumberStringHistoryList.setModel(NumberStringHistoryListModel);

        ComputerHistoryScoreListModel = new DefaultListModel<>();
        ComputerHistoryScoreList.setModel(ComputerHistoryScoreListModel);

        PlayerMoveHistoryListModel = new DefaultListModel<>();
        PlayerMoveHistoryList.setModel(PlayerMoveHistoryListModel);

        HistoryPanel.setVisible(false);
        HistoryLabel.setVisible(false);

        ButtonPanel.setVisible(false);

        setVisible(true);

        ShowHideHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistoryLabel.setVisible(!HistoryLabel.isVisible());
                HistoryPanel.setVisible(!HistoryPanel.isVisible());

                ShowHideHistoryButton.setText(HistoryPanel.isVisible() ? "Hide history" : "Show history");
            }
        });

        SplitTwoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerMove != Player.Human)
                    return;

                listener.takeTurn(5);

                //Datora gājiens
                listener.takeTurn(-1);
            }
        });

        SplitFourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerMove != Player.Human)
                    return;

                listener.takeTurn(6);

                //Datora gājiens
                listener.takeTurn(-1);
            }
        });

        TakeNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerMove != Player.Human)
                    return;

                JTextField textField = new JTextField();

                // Show the input dialog with the spinner
                int result = JOptionPane.showConfirmDialog(PlayingForm.this, textField, "Enter a number between 1 and 4:", JOptionPane.OK_CANCEL_OPTION);

                if (result != JOptionPane.OK_OPTION)
                    return;

                try {
                    int move = Integer.parseInt(textField.getText());
                    if (move >= 1 && move <= 4) {
                        if (!listener.takeTurn(move)){
                            JOptionPane.showMessageDialog(PlayingForm.this, ErrorMessage.NUMBER_IN_STRING_DOESNT_EXIST, "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        } else {
                            //Datora gājiens
                            listener.takeTurn(-1);
                        }
                    } else {
                        JOptionPane.showMessageDialog(PlayingForm.this, ErrorMessage.NUMBER_OUT_OF_BOUNDS, "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PlayingForm.this, ErrorMessage.NUMBER_IS_NOT_VALID, "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        HumanHistoryScoreListModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                scrollPaneToBottom(HumanHistoryScoreListPane, HumanHistoryScoreList, HumanHistoryScoreListModel);
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {

            }

            @Override
            public void contentsChanged(ListDataEvent e) {

            }
        });

        NumberStringHistoryListModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                scrollPaneToBottom(NumberStringHistoryListPane, NumberStringHistoryList, NumberStringHistoryListModel);
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {

            }

            @Override
            public void contentsChanged(ListDataEvent e) {

            }
        });

        ComputerHistoryScoreListModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                scrollPaneToBottom(ComputerHistoryScoreListPane, ComputerHistoryScoreList, ComputerHistoryScoreListModel);
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {

            }

            @Override
            public void contentsChanged(ListDataEvent e) {

            }
        });

        PlayerMoveHistoryListModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                scrollPaneToBottom(PlayerMoveHistoryListPane, PlayerMoveHistoryList, PlayerMoveHistoryListModel);
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {

            }

            @Override
            public void contentsChanged(ListDataEvent e) {

            }
        });

        HumanHistoryScoreListPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int value = e.getValue();

                NumberStringHistoryListPane.getVerticalScrollBar().setValue(value);
                ComputerHistoryScoreListPane.getVerticalScrollBar().setValue(value);
                PlayerMoveHistoryListPane.getVerticalScrollBar().setValue(value);
            }
        });

        NumberStringHistoryListPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int value = e.getValue();

                HumanHistoryScoreListPane.getVerticalScrollBar().setValue(value);
                ComputerHistoryScoreListPane.getVerticalScrollBar().setValue(value);
                PlayerMoveHistoryListPane.getVerticalScrollBar().setValue(value);
            }
        });

        ComputerHistoryScoreListPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int value = e.getValue();

                HumanHistoryScoreListPane.getVerticalScrollBar().setValue(value);
                NumberStringHistoryListPane.getVerticalScrollBar().setValue(value);
                PlayerMoveHistoryListPane.getVerticalScrollBar().setValue(value);
            }
        });

        PlayerMoveHistoryListPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int value = e.getValue();

                HumanHistoryScoreListPane.getVerticalScrollBar().setValue(value);
                NumberStringHistoryListPane.getVerticalScrollBar().setValue(value);
                ComputerHistoryScoreListPane.getVerticalScrollBar().setValue(value);
            }
        });

        QuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        StartNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startForm.setVisible(true);
                dispose();
            }
        });
    }

    public void scrollPaneToBottom(JScrollPane pane, JList<String> list, DefaultListModel<String> model) {
        JViewport viewport = pane.getViewport();
        viewport.scrollRectToVisible(list.getCellBounds(model.getSize() - 1, model.getSize() - 1));
    }

    public void setStatus(String numberString, int humanScore, int computerScore, Player playerMove) {
        PlayerMoveHistoryListModel.addElement(this.playerMove == null ? " " : String.valueOf(this.playerMove));

        this.playerMove = playerMove;

        HumanScoreTextField.setText(String.valueOf(humanScore));
        ComputerScoreTextField.setText(String.valueOf(computerScore));
        NumberStringTextField.setText(numberString);

        InfoLabel.setText(playerMove + " turn!");

        HumanHistoryScoreListModel.addElement(String.valueOf(humanScore));
        ComputerHistoryScoreListModel.addElement(String.valueOf(computerScore));
        NumberStringHistoryListModel.addElement(numberString.isEmpty() ? " " : numberString);
    }

    public void setResults(Player[] winners) {
        if (winners.length > 1)
            InfoLabel.setText("It's a tie!");
        else
            InfoLabel.setText(winners[0] + " wins!");

        ButtonPanel.setVisible(true);
        HistoryPanel.setVisible(true);
        HistoryLabel.setVisible(true);

        GameButtonPanel.setVisible(false);
        ShowHideHistoryButton.setVisible(false);
        NumberStringTextField.setVisible(false);
        NumberStringTextLabel.setVisible(false);
    }

}
