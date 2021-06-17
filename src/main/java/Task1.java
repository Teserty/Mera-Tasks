import java.io.IOException;

public class Task1 {
    public void runNotepad(){
        try {
            Process process = new ProcessBuilder("C:\\Program Files (x86)\\Notepad++\\notepad++.exe","--help").start();
            process.waitFor();
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
