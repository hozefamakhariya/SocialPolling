package com.techomega.socialpolling.Helper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class IntentUtils {

    private static String FACEBOOK_PAGE_ID = "lanatarekbrows";
    private static String INSTA_ID = "lanatarekbrows";

    public static void shareApp(Context context)
    {
        IntentUtils.share(context,"https://play.google.com/store/apps/details?id=" + context.getPackageName());
    }

    public static void ContentshareApp(Context context, String content)
    {
        IntentUtils.share(context,content);
    }

    public static void openPlayStore(Context context) {
        String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object

        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void fireIntent(Context context, Class cls, boolean clearAll) {
        Intent i = new Intent(context, cls);
        if (clearAll) {
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(i);
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void fireIntentWithData(Intent i, Context context, boolean clearAll) {

        if (clearAll) {
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(i);
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void openInMap(Context context, double latitude, double longitude, String labelName) {
        String newUri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + labelName + ")";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newUri));
        context.startActivity(intent);
    }

    public static void makePhoneCall(Context context, String callNo) {
        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + callNo));
        context.startActivity(dialIntent);
    }

    public static void openBrowser(Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static void share(Context context, String content) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(i, "Share"));
    }

    public static void openFb(Context context) {
        String facebookUrl = "https://www.facebook.com/" + FACEBOOK_PAGE_ID;

        String facebookUrlScheme = "fb://page/" + FACEBOOK_PAGE_ID;

        try {
            int versionCode = context.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;

            if (versionCode >= 3002850) {
                Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } else {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrlScheme)));
            }
        } catch (PackageManager.NameNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));

        }
    }

    public static void openInsta(Context context) {
        Uri uri = Uri.parse("http://instagram.com/_u/" + INSTA_ID);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            context.startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/" + INSTA_ID)));
        }
    }

    public static void OpenWP(Context context, String msg){
        try {
            String headerReceiver = "";// Replace with your message.
            String bodyMessageFormal = "Hello, I need Help";// Replace with your message.
            String whatsappContain = headerReceiver + bodyMessageFormal;
            String trimToNumner = "+919978836605"; //10 digit number
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData (Uri.parse("https://wa.me/" + trimToNumner + "/?text=" + msg));
            context.startActivity ( intent );
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
