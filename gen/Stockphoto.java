package gen;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/23/15
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Stockphoto {
    // placeholder for common photos for bg images e.g. company, high-tech, office, office workers, skyscraper, outdoors,
    // trees, flowers, lakes, mountains, runners, marathon, birds, computer code, etc.

    private String photoName;

    public Stockphoto (String photoName) {
        this.photoName = photoName;
    }

    public static Stockphoto find (String name) {

        // TODO: fixed list of available ones
        Stockphoto photo = new Stockphoto(name + ".jpg");

        return (photo);
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
}
