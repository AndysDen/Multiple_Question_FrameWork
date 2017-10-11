package com.colgorithmic.isqtbquestionbank;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.colgorithmic.isqtbquestionbank.helper.ApplicationConstants;
import com.colgorithmic.isqtbquestionbank.ui.FrameworkActivity;

public class ContactUs extends FrameworkActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        //This will load the application information from configuration file.
        TextView view=(TextView) findViewById(R.id.appDetail);
        view.setText(applicationContextData.getValue(ApplicationConstants.APP_DETAILS));



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.upper_right_menu_collection, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.closeApplication) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
        if(id==R.id.faceBookUs){
            String url = applicationContextData.getValue(ApplicationConstants.FACEBOOK_URL);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }
        if(id==R.id.aboutExam){
            Intent i = new Intent(this,AboutTest.class);
            startActivity(i);

        }

        if(id==R.id.productInfo){
            Intent i = new Intent(this,ProductInfo.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

}
