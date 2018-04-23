package Data;

import org.junit.Test;

public class InputPatchData {

    String idv;
    String action;

    public InputPatchData() {

    }

    public InputPatchData(String action) {
        this.idv = "5adca761a71f98039b8979b7";
        this.action = action;
    }

    //getters and setters

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}