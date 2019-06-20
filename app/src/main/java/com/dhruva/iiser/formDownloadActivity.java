package com.dhruva.iiser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class formDownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_download);
        initialize();
    }
    private void initialize(){
        Button abssub = findViewById(R.id.abssub);
        Button conRem = findViewById(R.id.conRem);
        Button fortra = findViewById(R.id.fortra);
        Button leave = findViewById(R.id.leave);
        Button ltcform = findViewById(R.id.ltcform);
        Button medform = findViewById(R.id.medform);
        Button proposal = findViewById(R.id.proposal);
        Button tele = findViewById(R.id.tele);
        Button stat = findViewById(R.id.stat);
        Button strindent = findViewById(R.id.strindent);
        Button purchase = findViewById(R.id.purchase);
        Button ta = findViewById(R.id.ta);
        Button vehreq = findViewById(R.id.vehreq);
        Button vishost = findViewById(R.id.vishost);
        Button aparform = findViewById(R.id.aparform);
        Button visfac = findViewById(R.id.visfac);
        Button email = findViewById(R.id.email);
        final Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);

        abssub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/abstract_submission.pdf"));
                startActivity(i);
            }
        });
        conRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/Conveyance_Allowance.odt"));
                startActivity(i);
            }
        });
        fortra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/Foreign_Travel.docx"));
                startActivity(i);
            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/leave_form.pdf"));
                startActivity(i);
            }
        });
        ltcform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/LTC_Form.docx"));
                startActivity(i);
            }
        });
        medform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/Medical_reimbursement_form.pdf"));
                startActivity(i);
            }
        });
        proposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/conferenceform.pdf"));
                startActivity(i);
            }
        });
        tele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/tel_reim.pdf"));
                startActivity(i);
            }
        });
        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/stationeryform.pdf"));
                startActivity(i);
            }
        });
        strindent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/indentform.pdf"));
                startActivity(i);
            }
        });
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/storesandpurchasemanualform.pdf"));
                startActivity(i);
            }
        });
        ta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/Travel_form.pdf"));
                startActivity(i);
            }
        });
        vehreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/Vehicle.pdf"));
                startActivity(i);
            }
        });
        vishost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/faculty-staff-students/front-page-articles/apar-form-details"));
                startActivity(i);
            }
        });
        aparform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/faculty-staff-students/front-page-articles/apar-form-details"));
                startActivity(i);
            }
        });
        visfac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/VisitorFacultyForm.odt"));
                startActivity(i);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                i.setData(Uri.parse("http://www.iisermohali.ac.in/files/pdf/forms/email_form.pdf"));
                startActivity(i);
            }
        });
    }
}
