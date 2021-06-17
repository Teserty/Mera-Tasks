import java.io.IOException;

public class Task1 {
    public void runNotepad(){
        try {
            Process process = new ProcessBuilder("C:\\Program Files (x86)\\Notepad++\\notepad++.exe","--help").start();
            Thread.sleep(50000);
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
