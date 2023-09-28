import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Frames {

    private static class Node {
        static int index = 0;
        int id;

        List<JRadioButton> buttons;
        Map<UI.Orientation, JButton> navigation;
        JFrame frame;
        JPanel panel;
        Node previous;
        Node next;

        public Node() {
        }

        public Node(JFrame frame, JPanel panel) {
            if (frame != null)
                this.id = index++;

            this.frame = frame;
            this.panel = panel;
        }

        public void disableAllExcept(JRadioButton pressed) {
            for (JRadioButton button : buttons) {
                if (button != pressed) {
                    button.setSelected(false);
                }
            }
        }

        public int getIndexOfButton(JRadioButton e) {
            return buttons.indexOf(e);
        }
    }

    private Node header;
    private Node trailer;
    private Node current;
    private int size;

    public Frames() {
        header = new Node();
        trailer = new Node();
        trailer.previous = header;
        header.next = trailer;
    }

    public JFrame add(JFrame frame, JPanel panel) {
        Node node = new Node(frame, panel);

        node.previous = trailer.previous;
        node.next = trailer;
        trailer.previous.next = node;
        trailer.previous = node;

        size++;

        return node.frame;
    }

    public int currentGetButtonId(JRadioButton e) {
        return current.getIndexOfButton(e);
    }

    public void currentDisableAllExcept(JRadioButton e) {
        current.disableAllExcept(e);
    }

    /*
     * DEPRECATED
     */
    public void disableAllExcept(int index, JRadioButton e) {
        Node aux = header.next;
        for (; index > 0; index--) {
            if (aux.next == null || aux.next.frame == null)
                return;
            aux = aux.next;
        }
        aux.disableAllExcept(e);
    }

    public JFrame get(int index) {
        Node aux = header.next;
        for (; index > 0; index--) {
            if (aux.next == null || aux.next.frame == null)
                return null;
            aux = aux.next;
        }
        return aux.frame;
    }

    public JFrame getLast() {
        validateEmptyList();
        return trailer.previous.frame;
    }

    public JPanel getLastPanel() {
        validateEmptyList();
        return trailer.previous.panel;
    }

    public Map<UI.Orientation, JButton> getLastNavigation() {
        validateEmptyList();
        return trailer.previous.navigation;
    }

    public int getLastIndex() {
        validateEmptyList();
        return trailer.previous.id;
    }

    public boolean remove(JFrame frame) {
        Node aux = header.next;
        for (; aux != null; aux = aux.next) {
            if (aux.frame == frame) {
                if (aux.frame == current.frame) {
                    if (current.next != null)
                        current = current.next;
                    else
                        current = current.previous;
                }
                aux.previous.next = aux.next;
                aux.next.previous = aux.previous;
                return true;
            }
        }
        return false;
    }

    public JFrame current() {
        validateEmptyList();
        if (current == header) {
            current = current.next;
        }
        return current.frame;
    }

    public JFrame next() {
        validateEmptyList();
        if (current.next == null || current.next.frame == null)
            return current.frame;
        current = current.next;
        return current.frame;
    }

    public JFrame previous() {
        validateEmptyList();
        if (current.previous == null || current.previous.frame == null)
            return current.frame;
        current = current.previous;
        return current.frame;
    }

    public int size() {
        return size;
    }

    private void validateEmptyList() throws ArrayIndexOutOfBoundsException {
        if (size < 1)
            throw new ArrayIndexOutOfBoundsException("Frames is a empty linked list");
        if (current == null)
            current = header.next;
        if (current.frame == null)
            current = current.next;
    }

    enum Element {
        FRAME, PANEL, ID
    }

    public void lastAddButtonList(List<JRadioButton> buttons) {
        trailer.previous.buttons = buttons;
    }

    public void addButtonList(int index, List<JRadioButton> buttons) {
        Node aux = header.next;
        for (; index > 0; index--) {
            if (aux.next == null || aux.next.frame == null)
                return;
            aux = aux.next;
        }
        aux.buttons = buttons;
    }

    public void lastaddButtonMap(Map<UI.Orientation, JButton> map) {
        trailer.previous.navigation = map;
    }

    public void addButtonMap(int index, Map<UI.Orientation, JButton> map) {
        Node aux = header.next;
        for (; index > 0; index--) {
            if (aux.next == null || aux.next.frame == null)
                return;
            aux = aux.next;
        }
        aux.navigation = map;
    }

}
