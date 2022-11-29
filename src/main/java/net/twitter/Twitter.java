package net.twitter;

import net.twitter.TwitterApp;
import net.twitter.infra.App;

/**
 * Hello world!
 *
 */
public class Twitter
{
    public static void main( String[] args )
    {
        App app = new TwitterApp();
        app.init(6003);
        app.start();
    }
}
