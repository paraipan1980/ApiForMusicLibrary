package Data;

public class InputPlaylistData {

        String desc;
        String title;

        public InputPlaylistData() {

        }

        public InputPlaylistData(String desc,String title){
            this.desc = desc;
            this.title = title;
        }

    //getters and setters

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
