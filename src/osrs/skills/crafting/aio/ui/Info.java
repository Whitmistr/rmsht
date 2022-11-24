package osrs.skills.crafting.aio.ui;

public class Info {
    public String xpHr;
    public String runTime, currentTask;

    public Info() {
        this.xpHr = "";
        this.runTime = "";
        this.currentTask = "";
    }

    public Info(String xpHr, String runTime, String currentTask) {
        this.xpHr = xpHr;
        this.runTime = runTime;
        this.currentTask = currentTask;
    }
}
