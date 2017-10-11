package com.colgorithmic.isqtbquestionbank.framework.services;

import com.colgorithmic.isqtbquestionbank.R;
import com.colgorithmic.isqtbquestionbank.exception.XMLFileLoadingException;
import com.colgorithmic.isqtbquestionbank.exception.XMLParsingException;
import com.colgorithmic.isqtbquestionbank.framework.ApplicationContextData;
import com.colgorithmic.isqtbquestionbank.helper.ApplicationConstants;
import com.colgorithmic.isqtbquestionbank.helper.LoadQuestionData;
import com.colgorithmic.isqtbquestionbank.pojo.Questions;
import com.colgorithmic.isqtbquestionbank.ui.FrameworkActivity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by anandsoni on 2017-10-08.
 */
public class QuestionLoadingService {

    //Details of XML which contains the information of xml
    String loadingSource;
    String xmlFileName;
    FrameworkActivity frameworkActivity;

    public QuestionLoadingService(FrameworkActivity frameworkActivity) {
        this.frameworkActivity = frameworkActivity;
        loadingSource = frameworkActivity.getApplicationContextData().getValue(ApplicationConstants.SOURCE);
        if (loadingSource.equals(ApplicationConstants.XML)) {
            initalizeXMLSource(frameworkActivity.getApplicationContextData());
        } else {
            initalizeDBSource(frameworkActivity.getApplicationContextData());
        }
    }


    private void initalizeDBSource(ApplicationContextData applicationContextData) {

    }


    private void initalizeXMLSource(ApplicationContextData applicationContextData) {
        xmlFileName = applicationContextData.getValue(ApplicationConstants.XML_FILE_NAME);

    }

    public Map < String, List < Questions >> loadQuestions() {
        if (loadingSource.equals(ApplicationConstants.XML)) {
          return  loadFromXMLSource();
        } else {
            loadFromDB();
        }
        return null;
    }

    private void loadFromDB() {}

    private Map < String, List < Questions >> loadFromXMLSource() {
        try {
            InputStream is = frameworkActivity.getResources().openRawResource(R.raw.questions);
            return LoadQuestionData.loadData(is);
        } catch (IOException ioe) {
            throw new XMLFileLoadingException(ApplicationConstants.XML_FILE_LOADING_EXCEPTION);
        } catch (XmlPullParserException xppe) {
            throw new XMLParsingException(ApplicationConstants.XML_PARSING_EXCEPTION);
        }

    }

}