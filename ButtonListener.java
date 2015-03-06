import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonListener implements ActionListener {
  ButtonListener() {
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("End turn")) {
      System.out.println("WE BE ENDING TURNS");
    }
  }
}