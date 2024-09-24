import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.WindowConstants;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UI {

    // Get the screen size
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    static UI instance;
    static Quiz quiz;

    Frames frames;

    public static void start(Quiz instance) {
        quiz = instance;
        getInstance();
    }

    private UI() {
        frames = new Frames();
    }

    public Frames getFrames() {
        return frames;
    }

    public static UI getInstance() {
        if (instance == null)
            instance = new UI();
        return instance;
    }

    public JPanel adicionarTela(String title) {
        JFrame frame = new JFrame("PUCRS - Open Campus 2023: " + title);
        JPanel root = new JPanel(new BorderLayout());
        frames.add(frame, root);

        int id = frames.getLastIndex();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(false);

        frame.add(root);

        Map<Orientation, JButton> map = addNavegation(root, frames.getLastIndex());
        frames.addButtonMap(id, map);
        resize(frame);

        return root;
    }

    public JPanel adicionarPergunta(String question) {
        JPanel panel = frames.getLastPanel();
        JPanel questionPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel(question);
        questionPanel.add(label);

        panel.add(questionPanel, BorderLayout.PAGE_START);

        return panel;
    }

    public JPanel adicionarRespostas() {
        JFrame frame = new JFrame("PUCRS - Open Campus 2023: Resultado");
        JPanel root = new JPanel(new BorderLayout());
        Map<Orientation, JButton> navigation = frames.getLastNavigation();
        frames.add(frame, root);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(false);

        frame.add(root);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        JLabel congratulations = new JLabel("Parabéns!", SwingConstants.CENTER);
        JLabel score = new JLabel("", SwingConstants.CENTER);

        congratulations.setHorizontalAlignment(JLabel.CENTER);
        congratulations.setVerticalAlignment(JLabel.CENTER);
        score.setHorizontalAlignment(JLabel.CENTER);
        score.setVerticalAlignment(JLabel.CENTER);

        panel.add(congratulations);
        panel.add(score);

        root.add(panel, BorderLayout.CENTER);

        resize(frame);

        navigation.get(Orientation.NEXT).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int[] answares = ManageActions.getInstance().getAnswares();
                int correct = Quiz.respostas(answares);
                int total = frames.size() - 1;
                if (correct > (total / 2))
                    congratulations.setText("Parabéns");
                else
                    congratulations.setText("Dá pra melhorar :D");

                score.setText(
                        "Você acertou " + correct + " de "
                                + (total) + " perguntas");
            }
        });
        return null;
    }

    /**
     * @param questions
     * @return
     */
    public JPanel adicionarOpcoes(String... questions) {
        JPanel panel = frames.getLastPanel();
        int id = frames.getLastIndex();

        JPanel questionPanel = new JPanel(new GridLayout(0, 1));

        List<JRadioButton> buttons = Arrays.stream(questions)
                .map(e -> new JRadioButton(e, false))
                .map(e -> {
                    questionPanel.add(e);
                    return e;
                })
                .map(e -> {
                    e.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            ManageActions.getInstance()
                                    .updateSelection(id, e)
                                    .setCurrentSelection(id, e);
                        }
                    });
                    return e;
                })
                .collect(Collectors.toList());

        frames.addButtonList(id, buttons);

        panel.add(questionPanel, BorderLayout.CENTER);
        return panel;
    }

    public void start() {
        frames.get(0).setVisible(true);
    }

    public void browse(Orientation direction) {
        JFrame hide = frames.current();
        JFrame show = null;
        if (direction == Orientation.NEXT)
            show = frames.next();
        else
            show = frames.previous();

        show.setLocation(hide.getLocation());
        show.setVisible(true);
        if (show != hide)
            hide.setVisible(false);
    }

    private Map<Orientation, JButton> addNavegation(JPanel panel, int id) {
        JPanel buttonPane = new JPanel(new FlowLayout());
        Map<Orientation, JButton> map = new HashMap<>();
        JButton previous = new JButton("Anterior");
        JButton next = new JButton("Próximo");
        map.put(Orientation.PREVIOUS, previous);
        map.put(Orientation.NEXT, next);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageActions.getInstance().browse(Orientation.NEXT);
            }
        });
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageActions.getInstance().browse(Orientation.PREVIOUS);
            }
        });

        buttonPane.add(previous);
        buttonPane.add(next);

        panel.add(buttonPane, BorderLayout.PAGE_END);

        return map;
    }

    private void resize(JFrame frame) {
        frame.setSize(500, 300);
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;

        // Set the new frame location
        frame.setLocation(x, y);
    }

    enum Orientation {
        PREVIOUS, NEXT
    }

}