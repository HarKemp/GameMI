package Forms;

import Enums.AlgorithmType;
import Enums.Player;
import Listeners.StartGameListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartForm extends JFrame {
    private JPanel MainPanel;
    private JButton QuitButton;
    private JButton StartButton;
    private JPanel ButtonPanel;
    private JLabel FirstPlayerLabel;
    private JComboBox<Player> FirstPlayerComboBox;
    private JLabel AlgorithmTypeLabel;
    private JComboBox<AlgorithmType> AlgorithmTypeComboBox;
    private JLabel StringLengthLabel;
    private JTextField StringLengthTextField;
    private JLabel ErrorLabel;

    public StartForm(StartGameListener listener) {
        listener.setStartForm(this);

        setTitle("Star new game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(MainPanel);

        FirstPlayerComboBox.setModel(new DefaultComboBoxModel<>(Player.values()));
        AlgorithmTypeComboBox.setModel(new DefaultComboBoxModel<>(AlgorithmType.values()));
        StartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lengthText = StringLengthTextField.getText().trim();
                int length;
                try {
                    length = Integer.parseInt(lengthText);
                } catch (NumberFormatException e1) {
                    ErrorLabel.setText(ErrorMessage.INCORRECT_NUMBER_STRING_LENGTH);
                    return;
                }

                if (!listener.isValidLength(length)) {
                    ErrorLabel.setText(ErrorMessage.NUMBER_STRING_LENGTH_OUT_OF_BOUNDS);
                    return;
                }

                ErrorLabel.setText("");

                Player firstPlayer = (Player) FirstPlayerComboBox.getSelectedItem();
                AlgorithmType algorithmType = (AlgorithmType) AlgorithmTypeComboBox.getSelectedItem();

                listener.startNewGame(length, firstPlayer, algorithmType);

                dispose();
            }
        });

        QuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

}