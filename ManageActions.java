import javax.swing.JRadioButton;

public class ManageActions {

    private static ManageActions instance;
    private Frames frames;
    private UI ui;

    private int[] answers;

    private ManageActions() {
        this.ui = UI.getInstance();
        frames = ui.getFrames();

        answers = new int[frames.size()];
    }

    public static ManageActions getInstance() {
        if (instance == null)
            instance = new ManageActions();
        return instance;
    }

    public ManageActions updateSelection(int id, JRadioButton e) {
        frames.currentDisableAllExcept(e);
        return this;
    }

    public ManageActions browse(UI.Orientation orientation) {
        ui.browse(orientation);
        return this;
    }

    public ManageActions setCurrentSelection(int id, JRadioButton e) {
        answers[id] = frames.currentGetButtonId(e);
        return this;
    }
}
