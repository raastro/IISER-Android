package com.dhruva.iiser;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Info extends Activity {


    private TextView info;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_info);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        info = findViewById(R.id.info);
        Button imap = findViewById(R.id.imap);
        Button data = findViewById(R.id.data);
        Button wifi = findViewById(R.id.wifi);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

            }
        });

        imap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", "webmail.iisermohali.ac.in:143"));
                Toast.makeText(getApplicationContext(), "Copied!", Toast.LENGTH_SHORT).show();
            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", "webmail.iisermohali.ac.in:25"));
                Toast.makeText(getApplicationContext(), "Copied!", Toast.LENGTH_SHORT).show();
            }
        });

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", "172.16.2.63:25"));
                Toast.makeText(getApplicationContext(), "Copied!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeLogic() {
        info.setText("Due to the old setup of the Webmail that IISERM uses, your Email account DOESN'T automatically get added to your Email clients (Windows Mail, Gmail Android App, for example). So, it takes a little more effort to add it, but the advantage of being able to send emails from your client directly is worth it. The following steps help you to add your webmail account to the Android Gmail App (those who understand Email clients and IMAP/SMTP icon_setting can jump to TL;DR).\n\n1. Open Gmail App, tap on your profile picture at the top right, and tap \"Add another Account.\"\n2. Tap Other\n3. Enter your mail address as ms1****@iisermohali.ac.in\n4. Tap Manual Setup\n5. Tap \"Personal (IMAP)\"\n6. Enter your webmail password. Tap Next\n7. In \"Incoming Server Settings,\" Change username to your roll no (ms1****)\n8. Set the server to webmail.iisermohali.ac.in, Port to 143, Security Type to None\n9. If any error occurs, please recheck step 7 and 8, and make sure you are connected to the internet. On Validation, Outgoing server icon_setting will show up. \n10. Make sure Require sign-in is checked\n11. Username should be the roll number(ms1****) and Password your webmail password.\n12. Set SMTP server to webmail.iisermohali.ac.in or 172.16.2.63 depending on if you are on Cellular data or Student's WiFi respectively.\n13. Tap next. An \"Email Security not Guaranteed\" warning pops up. Click Edit Settings.\n14. You should be taken back to the Outgoing server icon_setting page with \"Try updating port\" Error.\n15. Change your port to 25 and security type to None\n16. If any error occurs, please recheck step 14 and 15, and make sure you are connected to the internet and server icon_setting are according to that. On Validation, Account options will show up.\n17. Set according to your preferences, or leave default icon_setting.\n18. Tap Next. Set an account name and Your Name. Please set your actual name as you may send emails to professors.\n19. You are done!\n\nIf you see that there are some issues, tap on the burger on the top left, and tap \"Settings.\" Tap your webmail account, scroll down to see Incoming and Outgoing icon_setting. Check if everything is matching.\n\nTL;DR-\n*DO NOT PUT @iisermohali.ac.in in username*\nIncoming (IMAP) Server Settings\nServer: webmail.iisermohali.ac.in \nPort : 143\nsecurity : none\n\nOutgoing (SMTP) Server Settings\nServer: 172.16.2.63 (OR) webmail.iisermohali.ac.in (according to whether on Student's WiFi or another internet connection respectively)\nPort: 25\nSecurity: none\n*DO NOT PUT @iisermohali.ac.in in username*");
    }
}
