package org.scribe.examples;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import java.util.Scanner;

public class Alfred {

    private static String[] PROTECTED_RESOURCE_URL = new String[6];

    public static void main(String args[]) {
        int TWITTER=0;
        int FACEBOOK=1;
        int GOOGLE=2;
        int MEETUP=3;
        int TUMBLR=4;

        PROTECTED_RESOURCE_URL[TWITTER] = "https://api.twitter.com/1.1/account/verify_credentials.json";
        PROTECTED_RESOURCE_URL[FACEBOOK] = "https://graph.facebook.com/me";
        PROTECTED_RESOURCE_URL[GOOGLE] = "https://docs.google.com/feeds/default/private/full/";
        PROTECTED_RESOURCE_URL[MEETUP] = "http://api.meetup.com/2/member/self";
        PROTECTED_RESOURCE_URL[TUMBLR] = "http://api.tumblr.com/v2/user/info";

        System.out.println("Hello World");

        //Some mode test code here
        String API_KEY =  "M0QtsPiAT92DoFKCZZ7QlRitZ";
        String YOUR_API_SECRET = "99UqDRqGK0Vit1VJspIbKqKCrVhlNYdQvIG99dkFhyNSH0si8f";
        String ACCESS_TOKEN = "2521122996-2Ct0A0YMalz0YbatR2A6xZzMg9NSWLTQUYHVqbD";
        String ACCESS_TOKEN_SECRET = "6gjXNlTTLUZGnKEzbkazMcipNbgmXM5uOhwP5s3LDfHRY";
        OAuthService service = new ServiceBuilder().provider(TwitterApi.class).apiKey(API_KEY).apiSecret(YOUR_API_SECRET).build();
        Scanner in = new Scanner(System.in);

        System.out.println("=== Twitter's OAuth Workflow ===");
        System.out.println();

        // Obtain the Request Token
        System.out.println("Fetching the Request Token...");
        Token requestToken = service.getRequestToken();
        System.out.println("Got the Request Token!");
        System.out.println();

        System.out.println("Now go and authorize Scribe here:");
        System.out.println(service.getAuthorizationUrl(requestToken));
        System.out.println("And paste the verifier here");
        System.out.print(">>");
        Verifier verifier = new Verifier(in.nextLine());
        System.out.println();

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        Token accessToken = service.getAccessToken(requestToken, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if you're curious, it looks like this: " + accessToken + " )");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL[TWITTER]);
        request.addBodyParameter("status", "this is sparta! *");
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getBody());

        System.out.println();
        System.out.println("That's it man! Go and build something awesome with Scribe! :)");

    }
}