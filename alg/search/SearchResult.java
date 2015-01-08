package alg.search;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/8/15
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResult {

    private URL url;
    private String snippet;
    private double score;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
