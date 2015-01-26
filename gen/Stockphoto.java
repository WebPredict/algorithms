package gen;

import java.util.HashMap;

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

    public static HashMap<String, String> nameToPhotoUrlMap = new HashMap<String, String>();

    static
    {
        nameToPhotoUrlMap.put("office", "http://greenliving4live.com/wp-content/uploads/2013/08/green-office-furniture-classic.jpg");
        nameToPhotoUrlMap.put("high-tech", "http://i.huffpost.com/gen/1476047/thumbs/o-TECHNOLOGY-facebook.jpg");
        nameToPhotoUrlMap.put("office-building", "http://upload.wikimedia.org/wikipedia/commons/f/fc/Skalar_Office_Building_Poznan.jpg");
        nameToPhotoUrlMap.put("nature", "https://chakracenter.files.wordpress.com/2013/02/nature1.jpg");
    }

    public static Stockphoto find (String name) {

        // TODO: fixed list of available ones
        String url = nameToPhotoUrlMap.get(name);
        if (url == null)
            url = "placeholder.jpg"; // TODO

        Stockphoto photo = new Stockphoto(url);

        return (photo);
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
}
