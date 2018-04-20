package Data;

public class InputData {

    String artist;
    String song;
    String publishDate;

    public InputData() {

    }

    public InputData(String artist,String song,String publishDate){
        this.artist = artist;
        this.song = song;
        this.publishDate = publishDate;
    }

    //getters and setters

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
