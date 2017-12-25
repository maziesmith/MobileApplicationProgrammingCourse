package mtuci.nikitakutselay.mobileapplicationprogrammingcourse;

public class ProcessesListLoader {
    static {
        System.loadLibrary("processes-list");
    }

    public native String getProcessesList();
}
